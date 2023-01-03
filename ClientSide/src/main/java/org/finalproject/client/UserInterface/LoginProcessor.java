package org.finalproject.client.UserInterface;

import org.finalproject.DataObject.User;
import org.finalproject.client.ClientConfiguration;
import org.finalproject.client.Http.Request;
import org.finalproject.client.Http.RequestException;
import org.finalproject.client.Http.Response;

import java.util.Scanner;

public class LoginProcessor extends InputProcessor {


    String username;
    String password;

    public LoginProcessor(Scanner scanner) {
        super(scanner);
    }

    @Override
    void printGuideMessage() {
        System.out.println(ANSI_GREEN+"Login to your account \nEnter your username.\n"
                +ANSI_BLUE+"Don't have an account?"+
                " enter 5 to sign up first or send 1 to go back to the main menu."+ANSI_RESET);
    }

    @Override
    void processInput() {
        String line = scanner.nextLine();
        if (line.equals("5")) {
            new SignUpProcessor(scanner).guide().process();
            return;
        }
        if (line.equals("1")) {
            new MainMenuProcessor(scanner).guide().process();
            return;
        }
        username = line;
        System.out.println("now enter your password.");
        password = scanner.nextLine();
        Request request = new Request("POST", "login");
        request.setBody(new User(username, password));
        try {
            Response response = ClientConfiguration.getInstance().getRequestManager()
                    .sendRequest(request);
            System.out.println("\nLogin successful!\n");
            ClientConfiguration.getInstance().setUser((User) response.getResponseBody());
            new HomeMenuProcessor(scanner).guide().process();
            return;
        } catch (RequestException e) {
            if (e.getCode() == Response.ERR_INVALID_CREDENTIALS) {
                System.out.println("Invalid username/password. try again!\n"+ANSI_BLUE+"send 1 to go back to the main menu."+ANSI_RESET);
                this.process();
                return;
            }
        }
    }


}
