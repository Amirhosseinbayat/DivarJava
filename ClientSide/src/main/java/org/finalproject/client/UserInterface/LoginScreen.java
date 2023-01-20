package org.finalproject.client.UserInterface;

import org.finalproject.DataObject.User;
import org.finalproject.client.ClientConfiguration;
import org.finalproject.client.Http.Request;
import org.finalproject.client.Http.RequestException;
import org.finalproject.client.Http.Response;
import org.finalproject.client.ImprovedUserInterface.BackSupportedInputHandler;
import org.finalproject.client.ImprovedUserInterface.InputHandler;
import org.finalproject.client.ImprovedUserInterface.Navigation;

public class LoginScreen extends UIScreen {


    String username;
    String password;


    InputHandler usernameHandler = new BackSupportedInputHandler() {
        @Override
        public boolean handleValidInput(String input) {
            if (input.equals("1")) {
                Navigation.popNavigate(new SignUpScreen());
                return true;
            }
            username = input;
            return true;
        }
    };

    InputHandler passwordHandler = new BackSupportedInputHandler() {
        @Override
        public boolean handleValidInput(String input) {
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
                Navigation.navigateTo(new HomeMenuScreen(user));
                return true;
            } catch (RequestException e) {
                if (e.getCode() == Response.ERR_INVALID_CREDENTIALS) {
                    UIUtils.danger("Invalid username/password!\n"
                            +"Enter your password for "+username+" again,"
                            +"press Enter to go back and correct your username.");
                    return false;
                }
                e.printDetails();
                return false;
            }
        }
    };

    @Override
    public void startScreen() {
        UIUtils.header("Log In");
        UIUtils.primary("Enter your username to login.");
        UIUtils.options("Create an account first", "Go back to main menu");
        promptInput(usernameHandler);
        promptInput("Now enter your password: ", passwordHandler);
    }


}
