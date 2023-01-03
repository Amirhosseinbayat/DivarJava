package org.finalproject.client;

import org.finalproject.DataObject.User;
import org.finalproject.client.Http.HttpRequestManager;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ClientConfiguration {
    String serverAddress = "http://localhost";
    int serverPort = 12435;
    Charset charset = StandardCharsets.UTF_8;

    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    HttpRequestManager requestManager = new HttpRequestManager();

    public HttpRequestManager getRequestManager() {
        return requestManager;
    }

    private static final ClientConfiguration CLIENT_CONFIGURATION = new ClientConfiguration();

    private ClientConfiguration() {

    }

    public String getServerConnectionString() {
        return getServerAddress()+":"+getServerPort()+"/";
    }

    public static ClientConfiguration getInstance(){
        return CLIENT_CONFIGURATION;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }
}
