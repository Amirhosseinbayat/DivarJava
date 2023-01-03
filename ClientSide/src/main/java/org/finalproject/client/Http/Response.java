package org.finalproject.client.Http;

import org.finalproject.DataObject.DataObject;
import org.finalproject.client.ClientConfiguration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.net.HttpURLConnection;

public class Response {


    public static final int ERR_INVALID_PASSWORD = 601;
    public static final int ERR_USERNAME_TAKEN = HttpURLConnection.HTTP_CONFLICT;
    public static final int ERR_SERVER_EXCEPTION = HttpURLConnection.HTTP_INTERNAL_ERROR;
    int resultCode;

    byte[] bytes;
    Object responseBody;

    public Response(byte[] bytes) {
        this.bytes = bytes;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void interpretResultBytesAs(String contentType) throws IOException, ClassNotFoundException {
        if ("text/plain".equals(contentType)) {
            responseBody = new String(bytes, ClientConfiguration.getInstance().getCharset());
        }
        if ("object/java".equals(contentType)) {
            responseBody = DataObject.createFromByteArray(bytes);
        }
        if (("list/java".equals(contentType))) {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInput in = new ObjectInputStream(bis);
            responseBody = in.readObject();
        }
    }

    public Object getResponseBody() {
        return responseBody;
    }
}
