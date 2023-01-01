package org.finalproject.client.Http;

import org.finalproject.client.ClientConfiguration;
import org.finalproject.DataObject.DataObject;

import java.util.ArrayList;

public class Response {


    int resultCode;

    byte[] bytes;
    Object responseBody;

    public Response(byte[] bytes) {
        this.bytes = bytes;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public void interpretResultBytesAs(String contentType){
        if ("text/plain".equals(contentType)){
            responseBody = new String(bytes, ClientConfiguration.getInstance().getCharset());
        }
        if ("object/java".equals(contentType)){
            responseBody = new Object();
            //todo convert bytes to object.
        }
        if (("list/java".equals(contentType))){
            responseBody = new ArrayList<DataObject>();
            //todo convert bytes to list of objects.
        }
    }

    public Object getResponseBody() {
        return responseBody;
    }
}
