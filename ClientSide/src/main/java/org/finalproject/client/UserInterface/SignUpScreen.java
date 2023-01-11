package org.finalproject.client.UserInterface;

import org.finalproject.DataObject.User;
import org.finalproject.client.ClientConfiguration;
import org.finalproject.client.Http.IHttpRequestManager;
import org.finalproject.client.Http.Request;
import org.finalproject.client.Http.RequestException;
import org.finalproject.client.Http.Response;

import java.util.Scanner;

public class SignUpScreen extends UIScreen {
    String userName;

    public SignUpScreen(Scanner scanner) {
        super(scanner);
    }

    @Override
    void printGuideMessage() {
        UIUtils.header("Sign Up");
        UIUtils.primary("Enter a username of your choice: ");
    }

    @Override
    void processInput() {
        userName = scanner.nextLine();
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
            UIUtils.primary("Enter a password which as all the qualities mentioned above.");
            processPassword();
        } catch (RequestException e) {
            restartWithError(e.getMessage()+ANSICodes.RESET+"\nplease try again.");
        }

    }

    void processPassword() {
        String password = scanner.nextLine();
        User user = new User(userName, password);
        Request request = new Request("POST", "signup");
        request.setBody(user);
        try {
            Response response = ClientConfiguration.getInstance().getRequestManager().sendRequest(request);
            ClientConfiguration.getInstance().setUser(response.getResponseBody());
            UIUtils.successful("Very well! ");
            processEmailChange();
        } catch (RequestException e) {
            if (e.getCode() == Response.ERR_WEAK_PASSWORD) {
                UIUtils.danger(e.getMessage());
                UIUtils.warning("Please try a better password...");
                processPassword();
            }
            if (e.getCode() == Response.ERR_USERNAME_TAKEN) {
                UIUtils.danger("While you were deciding about your password,"+
                        " someone else has occupied the username you had selected! you will have to start over."
                        +e.getMessage());
                processInput();
            }
        }

    }


    void processEmailChange() {
        User user = ClientConfiguration.getInstance().getUser();
        String input = getInputBy("now Enter your email address carefully.");
        String previous = user.getEmailAddress();
        while (true) {
            if (input.isEmpty() || input.equals("\n")) {
                guide();
                process();
                return;
            }
            user.setEmailAddress(input);
            try {
                IHttpRequestManager manager = ClientConfiguration.getInstance().getRequestManager();
                Response response =
                        manager.sendRequest(new Request("POST", "user/update").setBody(user));
                ClientConfiguration.getInstance().setUser(response.getResponseBody());
                UIUtils.successful("Sign up done! press Enter to continue");
                scanner.nextLine();
                new HomeMenuScreen(scanner).guide().process();
                break;
            } catch (RequestException e) {
                UIUtils.danger("failed to update Email: "+e.getMessage());
                user.setEmailAddress(previous);
                input = getInputBy("Try again with a different one. \npress enter to go back.");
            }
        }
    }
}
