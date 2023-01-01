package org.finalproject.server.Http;

import com.sun.net.httpserver.HttpExchange;
import org.finalproject.DataObject.DataObject;
import org.finalproject.DataObject.User;
import org.finalproject.server.ServerConfiguration;

import java.util.ArrayList;
import java.util.Map;

public class Request {

    String httpMethod;
    String path;
    String clientIpAddress;
    Map<String, Object> headers;
    User user;
    Object requestBody;

    public Request(String httpMethod, String path) {
        this.httpMethod = httpMethod;
        this.path = path;
    }

    public Request(HttpExchange httpExchange) {
        this.httpMethod = httpExchange.getRequestMethod();
        this.path = httpExchange.getRequestURI().getPath();
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }

    public void interpretRequestBytesAs(String contentType, byte[] bytes) {
        if ("text/plain".equals(contentType)) {
            requestBody = new String(bytes, ServerConfiguration.getInstance().getCharset());
        }
        if ("object/java".equals(contentType)) {
            requestBody = DataObject.createFromByteArray(bytes);
            //todo convert bytes to object.
        }
        if (("list/java".equals(contentType))) {
            requestBody = new ArrayList<DataObject>();
            //todo convert bytes to list of objects.
        }
    }

    public <V> V getRequestBody() {
        return (V) requestBody;
    }

    public String getClientIpAddress() {
        return clientIpAddress;
    }

    public void setClientIpAddress(String clientIpAddress) {
        this.clientIpAddress = clientIpAddress;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Object> headers) {
        this.headers = headers;
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

}
