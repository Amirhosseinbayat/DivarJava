package org.finalproject.client;

import org.finalproject.client.Http.HttpRequestManager;
import org.finalproject.client.Http.Request;
import org.finalproject.client.Http.RequestException;
import org.finalproject.client.Http.Response;
import org.finalproject.client.UserInterface.MainMenuProcessor;

import java.util.Scanner;

public class ClientMain {

    public static void main(String[] args) {
        System.out.println("welcome to the cli client!");

    }

    static void expertMode() {
        HttpRequestManager manager = new HttpRequestManager();
        Scanner inputScanner = new Scanner(System.in);
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

}
