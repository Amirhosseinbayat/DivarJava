package org.finalproject.client.Http;

import org.finalproject.DataObject.User;
import org.finalproject.client.ClientConfiguration;
import org.finalproject.client.UserInterface.UIScreen;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpRequestManager implements IHttpRequestManager {
    @Override
    public Response sendRequest(Request request) throws RequestException {
        String urlString = ClientConfiguration.getInstance().getServerConnectionString()+request.getPath();
        User user = ClientConfiguration.getInstance().getUser();
        if (user != null) {
            request.addHeader("X-auth-id", String.valueOf(user.getObjectId()));
            request.addHeader("X-auth-pass", user.getPassword());
        }
        if (request.isGET()) return GET(urlString, request.getHeaders());
        if (request.isPOST()) {
            request.addHeader("Content-Type", request.getContentType());
            return POST(urlString, request.getBodyBytes(), request.getHeaders());
        }
        throw new RuntimeException("unsupported http method");
    }


    private Response GET(String urlString, Map<String, Object> headers) throws RequestException {
        System.out.println("sending a GET request to "+urlString);
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(urlString).openConnection();
            if (headers != null) {
                for (String key : headers.keySet()) {
                    connection.setRequestProperty(key, headers.get(key).toString());
                }
            }
            connection.connect();
            return getResponse(connection);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("request failed "+e.getMessage());
            throw new RuntimeException(e);
        }

    }

    private Response POST(String urlString, byte[] body, Map<String, Object> headers) throws RequestException {
        System.out.print("sending a POST request to "+urlString);
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(urlString).openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            if (headers != null) {
                for (String key : headers.keySet()) {
                    String value = headers.get(key).toString();
                    connection.setRequestProperty(key, value);
                }
            }
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(body);
            outputStream.flush();
            outputStream.close();
            connection.connect();
            return getResponse(connection);
        } catch (IOException | ClassNotFoundException e) {
            System.out.print(UIScreen.ANSI_RED+" --- request failed!"+UIScreen.ANSI_RESET+"\n");
            throw new RequestException(999, "error: "+e.getMessage());
        }
    }

    private Response getResponse(HttpURLConnection connection)
            throws IOException, ClassNotFoundException, RequestException {
        boolean success = connection.getResponseCode() == HttpURLConnection.HTTP_OK;
        InputStream stream = success ? connection.getInputStream() : connection.getErrorStream();
        ByteArrayOutputStream bodyStream = new ByteArrayOutputStream();
        byte[] chunk = new byte[1024];
        int read;
        while ((read = stream.read(chunk)) != -1) {
            bodyStream.write(chunk, 0, read);
        }
        byte[] byteArray = bodyStream.toByteArray();
        Response response = new Response(byteArray);
        response.setResultCode(connection.getResponseCode());
        String contentType = connection.getHeaderField("content-type");
        response.interpretResultBytesAs(contentType);
        System.out.print(" got responseCode:"+connection.getResponseCode()+"\n");
        connection.disconnect();
        if (response.getResultCode() != HttpURLConnection.HTTP_OK) {
            throw new RequestException(response.getResultCode()
                    , response.getResponseBody().toString());
        }
        return response;
    }
}
