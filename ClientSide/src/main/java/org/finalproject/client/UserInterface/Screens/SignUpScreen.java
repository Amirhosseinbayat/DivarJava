package org.finalproject.client.UserInterface.Screens;

import org.finalproject.DataObject.User;
import org.finalproject.client.ClientConfiguration;
import org.finalproject.client.Http.IHttpRequestManager;
import org.finalproject.client.Http.Request;
import org.finalproject.client.Http.RequestException;
import org.finalproject.client.Http.Response;
import org.finalproject.client.UserInterface.*;

public class SignUpScreen extends UIScreen {
    String userName;


    private final InputHandler usernameHandler = new BackSupportedInputHandler() {
        @Override
        public boolean handleValidInput(String input) {
            if (input.isBlank()) {
                System.out.println("username can not be empty.");
                return false;
            }
            userName = input;
            UIUtils.secondary("Checking if "+userName+" can be set as your username...");
            try {
                ClientConfiguration.getInstance().getRequestManager()
                        .sendRequest(new Request("POST", "username/check")
                                .setBody(userName));
                UIUtils.primary("\n\nNow lets create a strong password. This is a little tricky!");
                UIUtils.usual("1. The password should be at least 8 characters long.");
                UIUtils.usual("2. It should contain ONLY lowercase letters and numbers. spaces are not allowed.");
                UIUtils.usual("3. It should contain BOTH  letters and numbers.");
                UIUtils.usual("4. You should either use 'a' two times, like in 'alpha', or include a number in power of two.");
                UIUtils.usual("5. None of the numeric parts should be a sequence. like 5678, or 123");
                UIUtils.primary("Enter a password which has all the qualities mentioned above.");
                return true;
            } catch (RequestException e) {
                UIUtils.danger(e.getMessage()+ANSICodes.RESET+"\nplease try again.");
                return false;
            }
        }
    };

    User user;
    private final InputHandler passwordHandler = new BackSupportedInputHandler() {
        @Override
        public boolean handleValidInput(String password) {
            user = new User(userName, password);
            Request request = new Request("POST", "signup");
            request.setBody(user);
            try {
                Response response = ClientConfiguration.getInstance().getRequestManager().sendRequest(request);
                user = response.getResponseBody();
                ClientConfiguration.getInstance().setUser(user);
                UIUtils.successful("Very well! ");
                return true;
            } catch (RequestException e) {
                if (e.getCode() == Response.ERR_WEAK_PASSWORD) {
                    UIUtils.danger(e.getMessage());
                    UIUtils.warning("Please try a better password...");
                    return false;
                }
                if (e.getCode() == Response.ERR_USERNAME_TAKEN) {
                    UIUtils.danger("While you were deciding about your password,"+
                            " someone else has occupied the username you had selected! you will have to start over."
                            +e.getMessage());
                    return false;
                }
                e.printDetails();
                return false;
            }
        }
    };
    private final InputHandler emailHandler = new BackSupportedInputHandler() {
        @Override
        public boolean handleValidInput(String input) {
            String previous = user.getEmailAddress();
            user.setEmailAddress(input);
            try {
                IHttpRequestManager manager = ClientConfiguration.getInstance().getRequestManager();
                Response response =
                        manager.sendRequest(new Request("POST", "user/update").setBody(user));
                user = response.getResponseBody();
                UIUtils.successful("Sign up done! press Enter to continue");
                scanner.nextLine();
                ClientConfiguration.getInstance().setUser(user);
                Navigation.clearRootNavigate(new HomeMenuScreen(user));
                return true;
            } catch (RequestException e) {
                UIUtils.danger("failed to update Email: "+e.getMessage());
                user.setEmailAddress(previous);
                return false;
            }
        }

    };

    @Override
    public void startScreen() {
        UIUtils.header("Sign Up");
        UIUtils.primary("Enter a username of your choice: ");
        UIUtils.secondary("send 'back' to go back");
        promptInput(usernameHandler);
        promptInput(passwordHandler);
        promptInput("now enter your email address", emailHandler);
    }


}
