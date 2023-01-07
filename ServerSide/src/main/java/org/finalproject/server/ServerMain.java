package org.finalproject.server;

import com.sun.net.httpserver.HttpServer;
import org.finalproject.server.Http.HTTPRequestManager;
import org.finalproject.server.Http.IHttpRequestManager;
import org.finalproject.server.Http.RequestHandlers.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;

public class ServerMain {
    static ServerConfiguration serverConfiguration = ServerConfiguration.getInstance();

    public static void main(String[] args) {
        for (int x = 0; x<=args.length-1; x++) {
            String key = args[x];
            String value;
            if (key.startsWith("-") && x+2<args.length) {
                value = args[x+1];
                boolean continueProcess = processCLIArgs(x, key, value);
                if (!continueProcess) return;
                x++;
            } else {
                boolean continueProcess = processCLIArgs(x, key, null);
                if (!continueProcess) return;
            }
        }

        System.out.println("Server Process is starting on port "+serverConfiguration.getPortNumber());

        try {
            IHttpRequestManager manager = new HTTPRequestManager();
            manager.assignHandler(new PingHandler());
            manager.assignHandler(new GetAllRecordsHandler());
            manager.assignHandler(new SignUpHandler());
            manager.assignHandler(new UserNameHandler());
            manager.assignHandler(new LoginHandler());
            manager.assignHandler(new UserUpdateHandler());
            HttpServer server =
                    HttpServer.create(new InetSocketAddress(serverConfiguration.getPortNumber()), 0);
            server.createContext("/", manager);
            server.setExecutor(Executors.newFixedThreadPool(10));
            server.start();
            System.out.println("server is listening on "+server.getAddress().toString());
        } catch (IOException e) {
            System.out.println("failed to start the server: "+e.getMessage());
        }
    }

    public static boolean processCLIArgs(int index, String key, String value) {
        switch (key) {
            case "-port":
                serverConfiguration.setPortNumber(Integer.parseInt(value));
                return true;
            case "--help":
            case "-h":
                if (index == 0) {
                    printHelpMessage();
                    return false;
                }
                break;
            case "-charset": {
                if (value == null) {
                    System.out.println("please specify the charset. ");
                    return false;
                }
                Charset charset = Charset.forName(value);
                serverConfiguration.setCharset(charset);
            }
        }
        System.out.println("unknown argument "+key);
        return false;
    }

    public static void printHelpMessage() {
        System.out.println(
                "Server process for Divar software."+
                        "\n  usage: [options] "+
                        "\n--help , -h               shows this message. interpreted only if it is the first arg"+
                        "\n -port=number             specify port number to listen on [default: 12435]"
        );
    }
}