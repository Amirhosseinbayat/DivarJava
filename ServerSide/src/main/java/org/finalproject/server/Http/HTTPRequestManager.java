package org.finalproject.server.Http;

import com.sun.net.httpserver.HttpExchange;
import org.finalproject.DataObject.User;
import org.finalproject.server.Database.QueryConstraints;
import org.finalproject.server.Http.RequestHandlers.RequestHandler;
import org.finalproject.server.ServerConfiguration;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Objects;

/**
 * Parses a http request and passes it to the related handler.
 */
public class HTTPRequestManager implements IHttpRequestManager {


    HashMap<String, RequestHandler> requestHandlerHashMap = new HashMap<>();

    public HTTPRequestManager() {

    }

    public void assignHandler(RequestHandler handler) {
        requestHandlerHashMap.put(handler.getHandlerCode(), handler);
    }

    @Override
    public void handle(HttpExchange t) throws IOException {
        System.out.println("handle request...");
        try {
            InputStream stream = t.getRequestBody();
            byte[] bytes = new byte[stream.available()];
            int bodyLength = stream.read(bytes);
            stream.close();
            Request request = new Request(t);
            Object attr = t.getRequestHeaders().getFirst("Content-Type");
            if(t.getRequestMethod().equals("POST")){
                if (attr == null) {
                    System.out.println("content type was null for POST request!");
                    sendBackResponse(new Response(403,
                            "content type header is required for POST request."), t);
                    return;
                }else {
                    String contentType = attr.toString();
                    request.interpretRequestBytesAs(contentType, bytes);
                }
            }
            int authResult = setRequestUser(request);
            if (authResult==HttpURLConnection.HTTP_UNAUTHORIZED){
                sendBackResponse(new Response(HttpURLConnection.HTTP_UNAUTHORIZED,
                        "auth failed. invalid credentials."),t);
                return;
            }
            String requestLog = "received a "+
                    t.getRequestMethod()+
                    " request from "+
                    t.getLocalAddress().toString()+
                    " to the path: "+
                    t.getRequestURI().getPath();
            if (bodyLength == -1) {
                requestLog += "\nrequest had no body.";
            } else {
                requestLog += " request body was "+bodyLength+" bytes long: \n"+request.getRequestBody();
            }

            RequestHandler handler = requestHandlerHashMap.get(request.getRequestCode());
            System.out.println(requestLog+" \n"+request.getRequestCode());
            if (handler == null) {
                Response response = new Response(404, "not found.");
                sendBackResponse(response, t);
                return;
            }
            Response response = handler.handle(request);
            sendBackResponse(response, t);
        } catch (Exception e) {
            e.printStackTrace();
            sendBackResponse(new Response(HttpURLConnection.HTTP_INTERNAL_ERROR,
                    "internal server error "+e.getMessage()), t);
        }

    }

    private void sendBackResponse(Response response, HttpExchange httpExchange) throws IOException {
        byte[] responseBytes = response.getResponseBytes();
        httpExchange.getResponseHeaders().set("content-type", response.getContentType());
        httpExchange.sendResponseHeaders(response.getStatusCode(), responseBytes.length);
        OutputStream os = new DataOutputStream(httpExchange.getResponseBody());
        os.write(responseBytes);
        os.flush();
        os.close();
    }


    private int setRequestUser(Request request) throws IOException {
        System.out.println(request.getHeaders().keySet());
        String objectIdHeader = request.getHeaders().get("X-auth-id");
        String passwordHeader = request.getHeaders().get("X-auth-pass");
        System.out.println("auth info: " + objectIdHeader + " : " + passwordHeader);
        if (objectIdHeader==null && passwordHeader==null)return 0; //if both are null, it is ok.
        if (objectIdHeader==null || passwordHeader==null)return HttpURLConnection.HTTP_UNAUTHORIZED;
        //if either id or password is null, it is not ok.

        long id = Long.parseLong(objectIdHeader);
        User user = ServerConfiguration.getInstance().getDataBase().findOne(new QueryConstraints<User>() {
            @Override
            public boolean test(User object) {
                return object.getObjectId()==id;
            }

            @Override
            public int compare(User o1, User o2) {
                return 0;
            }
        });
        if (!Objects.equals(user.getPassword(), passwordHeader)){
            return HttpURLConnection.HTTP_UNAUTHORIZED;
        }
        request.setUser(user);
        System.out.println("request authorized as " + user.getUsername());
        return 200;
    }

}
