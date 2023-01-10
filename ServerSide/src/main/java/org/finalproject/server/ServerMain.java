package org.finalproject.server;

import com.sun.net.httpserver.HttpServer;
import org.finalproject.server.Database.IDataBase;
import org.finalproject.server.Http.HTTPRequestManager;
import org.finalproject.server.Http.IHttpRequestManager;
import org.finalproject.server.Http.RequestHandlers.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class ServerMain {
    static final ServerConfiguration serverConfiguration = ServerConfiguration.getInstance();
    static HttpServer server;
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
            IDataBase dataBase = serverConfiguration.getDataBase();
            manager.assignHandler(new PingHandler());
            manager.assignHandler(new GetAllRecordsHandler(dataBase));
            manager.assignHandler(new SignUpHandler(dataBase));
            manager.assignHandler(new UserNameHandler(dataBase));
            manager.assignHandler(new LoginHandler(dataBase));
            manager.assignHandler(new UserUpdateHandler(dataBase));
            manager.assignHandler(new PlacardCreationHandler(dataBase));
            server = HttpServer.create(new InetSocketAddress(serverConfiguration.getPortNumber()), 0);
            server.createContext("/", manager);
            server.setExecutor(Executors.newFixedThreadPool(10));
            server.start();
            System.out.println("server is listening on "+server.getAddress().toString());
        } catch (IOException e) {
            System.out.println("failed to start the server: "+e.getMessage());
            return;
        }

        //called on ctrl+c or exit command. gracefully closes the database avoiding unfinished writes and corrupt data.
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                server.stop(0);
                System.out.println("received shut down signal. gracefully terminating database process...");
                serverConfiguration.getDataBase().close();
                System.out.println("process terminated successfully. server is offline.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, "Shutdown-thread"));

        Scanner scanner = new Scanner(System.in);
        while (!scanner.nextLine().equals("exit")) {
            System.out.println("unrecognized command. type 'exit' to close the server process...");
        }
        System.exit(0);
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
                """
                        Server process for Divar software.
                          usage: [options]\s
                        --help , -h               shows this message. interpreted only if it is the first arg
                         -port=number             specify port number to listen on [default: 12435]"""
        );
    }
}