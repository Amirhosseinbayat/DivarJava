package org.finalproject.client.Http;

import org.finalproject.DataObject.DataObject;
import org.finalproject.client.ClientConfiguration;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {

    final String httpMethod;
    final String path;
    final Map<String, Object> headers = new HashMap<>();


    Object body;

    public Request(String httpMethod, String path) {
        if (path.startsWith("/")) path = path.replaceFirst("/", "");
        this.httpMethod = httpMethod;
        this.path = path;
    }

    public Object getBody() {
        return body;
    }

    public Request setBody(Object body) {
        this.body = body;
        return this;
    }

    public String getContentType() {
        if (body instanceof String) {
            return "text/plain";
        }
        if (body instanceof DataObject) {
            return "object/java";
        }
        if (body instanceof List) {
            return "list/java";
        }
        throw new RuntimeException("could not determine content type of "+body);
    }

    public byte[] getBodyBytes() {
        if (body instanceof String) {
            return ((String) body).getBytes(ClientConfiguration.getInstance().getCharset());
        }
        if (body instanceof DataObject) {
            return ((DataObject) body).toByteArray();
        }
        if (body instanceof List) {
            return DataObject.toByteArray((Serializable) body);
        }
        throw new RuntimeException("couldn't convert body to byte array.");
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

    public void addHeader(String key, String value) {
        getHeaders().put(key.toLowerCase(), value);
    }


}
