package org.finalproject.client.UserInterface;

import org.finalproject.DataObject.User;
import org.finalproject.client.ClientConfiguration;
import org.finalproject.client.Http.Request;
import org.finalproject.client.Http.RequestException;
import org.finalproject.client.Http.Response;

import java.util.Scanner;

public class LoginScreen extends UIScreen {


    String username;
    String password;

    public LoginScreen(Scanner scanner) {
        super(scanner);
    }

    @Override
    void printGuideMessage() {
        UIUtils.header("Log In");
        UIUtils.primary("Enter your username to login.");
        UIUtils.options("Create an account first", "Go back to main menu");
    }

    @Override
    void processInput() {
        String line = scanner.nextLine();
        if (line.equals("1")) {
            new SignUpScreen(scanner).guide().process();
            return;
        }
        if (line.equals("2")) {
            new AuthMenuScreen(scanner).guide().process();
            return;
        }
        username = line;
        UIUtils.secondary("Now enter your password: ");
        processPassword();
    }

    private void processPassword() {
        password = scanner.nextLine();
        if (password.isEmpty() || password.equals("\n")) {
            guide();
            process();
            return;
        }
        Request request = new Request("POST", "login");
        request.setBody(new User(username, password));
        try {
            Response response = ClientConfiguration.getInstance().getRequestManager()
                    .sendRequest(request);
            UIUtils.successful("Log in successful!(press Enter to continue)");
            scanner.nextLine();
            ClientConfiguration.getInstance().setUser(response.getResponseBody());
            new HomeMenuScreen(scanner).guide().process();
        } catch (RequestException e) {
            if (e.getCode() == Response.ERR_INVALID_CREDENTIALS) {
                UIUtils.danger("Invalid username/password!\n"
                        +"Enter your password for "+username+" again,"
                        +"press Enter to go back and correct your username.");
                processPassword();
            }
        }
    }


}
