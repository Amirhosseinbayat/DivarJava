package org.finalproject.server.Http;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.finalproject.DataObject.DataObject;
import org.finalproject.DataObject.User;
import org.finalproject.server.ServerConfiguration;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class Request {

    final String httpMethod, path, clientIpAddress;
    final String x_id_header, x_pass_header;
    Map<String, String> headers;
    User user;
    Object bodyObject;
    HttpExchange httpExchange;
    byte[] bodyBytes;

    public Request(HttpExchange httpExchange) throws IOException, ClassNotFoundException {
        this.httpMethod = httpExchange.getRequestMethod();
        this.path = httpExchange.getRequestURI().getPath();
        this.httpExchange = httpExchange;
        this.clientIpAddress = httpExchange.getRemoteAddress().toString();
        this.setHeaders(new HashMap<>());
        Headers headers1 = httpExchange.getRequestHeaders();
        for (String header : headers1.keySet()) {
            String value = headers1.getFirst(header);
            this.getHeaders().put(header, value);
        }

        x_id_header = getHeader("X-auth-id");
        x_pass_header = getHeader("X-auth-pass");
    }

    public String getAuthIdHeader() {
        return x_id_header;
    }

    public String getAuthPasswordHeader() {
        return x_pass_header;
    }

    public int handleBody() throws IOException, ClassNotFoundException {
        InputStream stream = httpExchange.getRequestBody();
        bodyBytes = stream.readAllBytes();
        Object attr = httpExchange.getRequestHeaders().getFirst("Content-Type");
        if (httpExchange.getRequestMethod().equals("POST")) {
            if (attr == null) {
                return HttpURLConnection.HTTP_BAD_REQUEST;
            } else {
                String contentType = attr.toString();
                interpretRequestBytesAs(contentType, bodyBytes);
            }
        }
        return HttpURLConnection.HTTP_OK;
    }

    public int getBodyLength() {
        return bodyBytes.length;
    }


    public String getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }

    public void interpretRequestBytesAs(String contentType, byte[] bytes) throws IOException, ClassNotFoundException {
        if ("text/plain".equals(contentType)) {
            bodyObject = new String(bytes, ServerConfiguration.getInstance().getCharset());
        }
        if ("object/java".equals(contentType)) {
            bodyObject = DataObject.createFromByteArray(bytes);
        }
        if (("list/java".equals(contentType))) {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInput in = new ObjectInputStream(bis);
            @SuppressWarnings("UnnecessaryLocalVariable") //it is necessary to do the cast here.
            List<? extends DataObject> list = (List<? extends DataObject>) in.readObject();
            bodyObject = list;
        }
    }

    public <V> V getBodyObject() {
        return (V) bodyObject;
    }

    public String getClientIpAddress() {
        return clientIpAddress;
    }


    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getHeader(String key) {
        return getHeaders().get(key);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRequestCode() {
        return getHttpMethod()+":"+getPath();
    }


    @Override
    public String toString() {
        String identity = user != null ? "authorized user '"+user.getUsername()+"'"
                : "unknown client with ip: '"+clientIpAddress+"'";
        return "a "+httpMethod+" request from "+identity+" to: "+path;
    }
}
