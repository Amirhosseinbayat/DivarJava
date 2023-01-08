package org.finalproject.server;

import org.finalproject.server.Database.DataBase;
import org.finalproject.server.Database.IDataBase;
import org.finalproject.server.Database.SimpleRAMDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ServerConfiguration {
    private static final ServerConfiguration SERVER_CONFIGURATION = new ServerConfiguration();
    int portNumber = 12435;
    Charset charset = StandardCharsets.UTF_8;
    IDataBase dataBase;

    private ServerConfiguration() {
        createProductionDataBase();
    }

    private void createProductionDataBase() {
        try {
            String projectPath = System.getProperty("user.dir");
            File databaseFolder = new File(projectPath, "database");
            if (!databaseFolder.exists()) {
                System.out.println("creating database files in "+databaseFolder.getAbsolutePath());
                boolean createdDirs = databaseFolder.mkdirs();
                if (!createdDirs) throw new RuntimeException("unable to create database folder at "
                        +databaseFolder.getAbsolutePath());
            }
            File file = new File(databaseFolder, "PrimaryDataBase"+".divar");
            setDataBase(new DataBase(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static ServerConfiguration getInstance() {
        return SERVER_CONFIGURATION;
    }

    public void createTestDatabase() {
        setDataBase(new SimpleRAMDatabase());
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
