package org.finalproject.server;

import org.finalproject.server.Database.IDataBase;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ServerConfiguration {
    private static final ServerConfiguration SERVER_CONFIGURATION = new ServerConfiguration();
    int portNumber = 12435;
    Charset charset = StandardCharsets.UTF_8;
    IDataBase dataBase;

    private ServerConfiguration() {

    }

    public static ServerConfiguration getInstance() {
        return SERVER_CONFIGURATION;
    }

    public IDataBase getDataBase() {
        return dataBase;
    }

    public void setDataBase(IDataBase dataBase) {
        this.dataBase = dataBase;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }
}
