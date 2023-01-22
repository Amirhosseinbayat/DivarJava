package org.finalproject.server.Http;

import com.sun.net.httpserver.HttpExchange;
import org.finalproject.DataObject.User;
import org.finalproject.server.Database.IDataBase;
import org.finalproject.server.Http.RequestHandlers.RequestHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Objects;

/**
 * Parses a http request and passes it to the related handler.
 */
public class HTTPRequestManager implements IHttpRequestManager {


    final HashMap<String, RequestHandler> requestHandlerHashMap = new HashMap<>();

    final IDataBase iDataBase;

    public HTTPRequestManager(IDataBase iDataBase) {
        this.iDataBase = iDataBase;
    }

    public void assignHandler(RequestHandler handler) {
        requestHandlerHashMap.put(handler.getHandlerCode(), handler);
    }

    @Override
    public void assignHandlers(RequestHandler... handlers) {
        for (RequestHandler handler : handlers) {
            assignHandler(handler);
        }
    }

    @Override
    public void handle(HttpExchange t) throws IOException {
        try {
            Request request = new Request(t);
            int authResult = handleAuthentication(request);
            if (authResult == HttpURLConnection.HTTP_UNAUTHORIZED) {
                sendBackResponse(new Response(HttpURLConnection.HTTP_UNAUTHORIZED,
                        "auth failed. invalid credentials."), t);
                return;
            }
            int bodyResult = request.handleBody();
            if (bodyResult == HttpURLConnection.HTTP_BAD_REQUEST) {
                sendBackResponse(new Response(HttpURLConnection.HTTP_BAD_REQUEST,
                        "Content-type header is required for POST requests."), t);
                return;
            }
            System.out.println(request);
            RequestHandler handler = requestHandlerHashMap.get(request.getRequestCode());
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

    private static void sendBackResponse(Response response, HttpExchange httpExchange) throws IOException {
        byte[] responseBytes = response.getResponseBytes();
        httpExchange.getResponseHeaders().set("content-type", response.getContentType());
        httpExchange.sendResponseHeaders(response.getStatusCode(), responseBytes.length);
        OutputStream os = new DataOutputStream(httpExchange.getResponseBody());
        os.write(responseBytes);
        os.flush();
        os.close();
    }


    private int handleAuthentication(Request request) throws IOException {
        String objectIdHeader = request.getAuthIdHeader();
        String passwordHeader = request.getAuthPasswordHeader();
        if (objectIdHeader == null && passwordHeader == null) return 0; //if both are null, it is ok.
        if (objectIdHeader == null || passwordHeader == null) return HttpURLConnection.HTTP_UNAUTHORIZED;
        //if either id or password is null, it is not ok.
        long id = Long.parseLong(objectIdHeader);
        User user = iDataBase.getObjectWithId(id);
        if (!Objects.equals(user.getPassword(), passwordHeader)) {
            return HttpURLConnection.HTTP_UNAUTHORIZED;
        }
        request.setUser(user);
        return 200;
    }

}
