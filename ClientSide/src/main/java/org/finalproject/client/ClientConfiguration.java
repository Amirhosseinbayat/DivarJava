package org.finalproject.client;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ClientConfiguration {
    String serverAddress = "http://localhost";
    int serverPort = 12435;
    Charset charset = StandardCharsets.UTF_8;

    private static final ClientConfiguration CLIENT_CONFIGURATION = new ClientConfiguration();
    private ClientConfiguration(){

    }

    public String getServerConnectionString(){
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
