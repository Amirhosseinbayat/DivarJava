package org.finalproject.client;

import org.finalproject.client.Http.HttpRequestManager;
import org.finalproject.client.Http.Request;
import org.finalproject.client.Http.RequestException;
import org.finalproject.client.Http.Response;
import org.finalproject.client.UserInterface.AuthMenuScreen;

import java.nio.charset.Charset;
import java.util.Scanner;

public class ClientMain {

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
        System.out.println("welcome to the cli client!");
        if (args.length != 0 && args[0].equals("--expert")) expertMode();
        else new AuthMenuScreen(new Scanner(System.in)).guide().process();
    }

    static void expertMode() {
        HttpRequestManager manager = new HttpRequestManager();
        Scanner inputScanner = new Scanner(System.in);
        //noinspection InfiniteLoopStatement
        while (true) { //simple ui not meant to be in production. just to test api and network
            System.out.println("enter the request method");
            String method = inputScanner.nextLine();
            System.out.println("enter request path, without the first /");
            String path = inputScanner.nextLine();
            Request request = new Request(method, path);
            if (method.equals("POST")) {
                System.out.println("enter request body.");
                String body = inputScanner.nextLine();
                request.setBody(body);
            }
            try {
                Response response = manager.sendRequest(request);
                if (response == null) System.out.println("server did not respond.");
                else System.out.println(response.getResponseBody().toString()
                        +"  "+response.getResponseBody().getClass().getName());
            } catch (RequestException e) {
                e.printDetails();
            }
        }
    }

    public static boolean processCLIArgs(int index, String key, String value) {
        switch (key) {
            case "-port":
                ClientConfiguration.getInstance().setServerPort(Integer.parseInt(value));
                return true;
            case "--help":
            case "-h":
                if (index == 0) {
                    System.out.println("todo: print help message.");
                    return false;
                }
                break;
            case "-charset": {
                if (value == null) {
                    System.out.println("please specify the charset. ");
                    return false;
                }
                Charset charset = Charset.forName(value);
                ClientConfiguration.getInstance().setCharset(charset);
            }
            break;
            case "-serverUrl":
                if (value == null) {
                    System.out.println("please specify the server url. ");
                    return false;
                }
                ClientConfiguration.getInstance().setServerAddress(value);
        }
        System.out.println("unknown argument "+key);
        return false;
    }


}
