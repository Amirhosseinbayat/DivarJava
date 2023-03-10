package org.finalproject.client.UserInterface.Screens;

import org.finalproject.DataObject.User;
import org.finalproject.client.ClientConfiguration;
import org.finalproject.client.Http.Request;
import org.finalproject.client.Http.RequestException;
import org.finalproject.client.Http.Response;
import org.finalproject.client.UserInterface.*;

public class LoginScreen extends UIScreen {


    private String username;
    private String password;


    private InputHandler usernameHandler = new BackSupportedInputHandler("2") {
        @Override
        public boolean handleValidInput(String input) {
            if (input.equals("1")) {
                Navigation.popNavigate(new SignUpScreen());
                return true;
            }
            if (input.isBlank()) {
                System.out.println("username can not be empty.");
                return false;
            }
            username = input;
            return true;
        }
    };

    private InputHandler passwordHandler = new BackSupportedInputHandler() {
        @Override
        public boolean handleValidInput(String input) {
            if (input.equals("\n") || input.isBlank()) {
                UIUtils.secondary("enter your username again:");
                promptInput(usernameHandler);
                System.out.println("now enter your password. (username: '"+username+"', press enter to change username)");
                promptInput(this);
                return true;
            }
            password = input;
            Request request = new Request("POST", "login");
            request.setBody(new User(username, password));
            try {
                Response response = ClientConfiguration.getInstance().getRequestManager()
                        .sendRequest(request);
                UIUtils.successful("Log in successful!(press Enter to continue)");
                scanner.nextLine();
                User user = response.getResponseBody();
                ClientConfiguration.getInstance().setUser(user);
                Navigation.clearRootNavigate(new HomeMenuScreen(user));
                return true;
            } catch (RequestException e) {
                if (e.getCode() == Response.ERR_INVALID_CREDENTIALS) {
                    UIUtils.danger("Invalid username/password!\n"
                            +"Enter your password for "+username+" again,"
                            +"press Enter to go back and correct your username.");
                    return false;
                }
                e.printDetails();
                UIUtils.danger("try again!");
                return false;
            }
        }
    };

    @Override
    public void startScreen() {
        UIUtils.header("Log In");
        UIUtils.primary("Enter your username to login.");
        UIUtils.options("Create an account first");
        UIUtils.secondary("send 'back' to go back");
        promptInput(usernameHandler);
        promptInput("Enter your password: (username: '"+username+"', press enter to change username)"
                , passwordHandler);
    }

    @Override
    public void trimMemory() {
        usernameHandler = null;
        passwordHandler = null;
        username = null;
        password = null;
    }
}
