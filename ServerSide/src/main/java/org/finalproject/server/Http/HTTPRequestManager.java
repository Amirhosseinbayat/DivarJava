package org.finalproject.server.Http;

import com.sun.net.httpserver.HttpExchange;
import org.finalproject.server.Http.RequestHandlers.RequestHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;

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
            Object attr = t.getRequestHeaders().getFirst("Content-Type");
            if (attr == null) {
                System.out.println("content type null.");
                sendBackResponse(new Response(403,
                        "content type header is required for POST request."), t);
                return;
            }
            String contentType = attr.toString();
            Request request = new Request(t);
            request.interpretRequestBytesAs(contentType, bytes);
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
                Response response = new Response(200, "not found.");
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

}
