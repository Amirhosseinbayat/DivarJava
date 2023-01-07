package org.finalproject.server.Http;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.finalproject.DataObject.DataObject;
import org.finalproject.DataObject.User;
import org.finalproject.server.ServerConfiguration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class Request {

    String httpMethod;
    String path;
    String clientIpAddress;
    Map<String, String> headers;
    User user;
    Object requestBody;

    public Request(String httpMethod, String path) {
        this.httpMethod = httpMethod;
        this.path = path;
    }

    public Request(HttpExchange httpExchange) {
        this.httpMethod = httpExchange.getRequestMethod();
        this.path = httpExchange.getRequestURI().getPath();
        this.setHeaders(new HashMap<>());
        Headers headers1 = httpExchange.getRequestHeaders();
        for (String header : headers1.keySet()){
            String value = headers1.getFirst(header);
            this.getHeaders().put(header,value);
        }
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }

    public void interpretRequestBytesAs(String contentType, byte[] bytes) throws IOException, ClassNotFoundException {
        if ("text/plain".equals(contentType)) {
            requestBody = new String(bytes, ServerConfiguration.getInstance().getCharset());
        }
        if ("object/java".equals(contentType)) {
            requestBody = DataObject.createFromByteArray(bytes);
        }
        if (("list/java".equals(contentType))) {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInput in = new ObjectInputStream(bis);
            @SuppressWarnings("UnnecessaryLocalVariable") //it is necessary to do the cast here.
            List<? extends DataObject> list = (List<? extends DataObject>) in.readObject();
            requestBody = list;
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

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
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
