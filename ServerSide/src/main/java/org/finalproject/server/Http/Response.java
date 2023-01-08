package org.finalproject.server.Http;

import org.finalproject.DataObject.DataObject;
import org.finalproject.server.ServerConfiguration;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.List;

public class Response {
    final int statusCode;
    String contentType;
    final Object response;
    byte[] responseBytes;

    public Response(int statusCode, Object response) {
        this.statusCode = statusCode;
        this.response = response;
        if (response instanceof String) {
            contentType = "text/plain";
            Charset charset = ServerConfiguration.getInstance().getCharset();
            responseBytes = ((String) response).getBytes(charset);
        }
        if (response instanceof DataObject) {
            contentType = "object/java";
            responseBytes = ((DataObject) response).toByteArray();
        }
        if (response instanceof List<?> list) {
            contentType = "list/java";
            responseBytes = DataObject.toByteArray((Serializable) list);
        }
    }

    public byte[] getResponseBytes() {
        return responseBytes;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getContentType() {
        return contentType;
    }

    public Object getResponse() {
        return response;
    }
}
