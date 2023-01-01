package org.finalproject.client.Http;

import com.sun.net.httpserver.HttpExchange;
import org.finalproject.client.ClientConfiguration;
import org.finalproject.DataObject.DataObject;
import org.finalproject.DataObject.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {

    String httpMethod;
    String path;
    Map<String,Object> headers = new HashMap<>();
    User user;

    Object body;

    public byte[] getBody() {
        if (body instanceof String){
            return ((String) body).getBytes(ClientConfiguration.getInstance().getCharset());
        }
        if (body instanceof DataObject){
            return ((DataObject) body).toByteArray();
        }
        if (body instanceof List){
            //todo
        }
        throw new RuntimeException("");
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public String getContentType(){
        if (body instanceof String){
            return "text/plain";
        }
        if (body instanceof DataObject){
            return "object/java";
        }
        if (body instanceof List){
            return "list/java";
        }
        throw new RuntimeException(body.getClass().toString());
    }

    public Request(String httpMethod, String path) {
        this.httpMethod = httpMethod;
        this.path = path;
    }

    public Request(HttpExchange httpExchange){
        this.httpMethod = httpExchange.getRequestMethod();
        this.path = httpExchange.getRequestURI().getPath();

    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public boolean isGET(){
        return "GET".equals(getHttpMethod());
    }

    public boolean isPOST(){
        return "POST".equals(getHttpMethod());
    }

    public String getPath() {
        return path;
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
        return getHttpMethod() +":"+ getPath();
    }

}
