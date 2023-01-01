package org.finalproject.server.Http;

import org.finalproject.DataObject.DataObject;
import org.finalproject.server.ServerConfiguration;

import java.nio.charset.Charset;
import java.util.List;

public class Response {
    int statusCode;
    String contentType;
    Object response;
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
            //todo convert object to bytes.
            responseBytes = new byte[]{};
        }
        if (response instanceof List) {
            List<?> list = (List<?>) response;
            contentType = "list/java";
            //todo convert list to bytes.
            responseBytes = new byte[]{};
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
