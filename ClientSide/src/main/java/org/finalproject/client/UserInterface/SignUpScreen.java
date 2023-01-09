package org.finalproject.client.UserInterface;

import org.finalproject.DataObject.User;
import org.finalproject.client.ClientConfiguration;
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
        UIUtils.secondary("Checking if "+userName+" is not already taken...");
        try {
            ClientConfiguration.getInstance().getRequestManager()
                    .sendRequest(new Request("POST", "username/check")
                            .setBody(userName));
            UIUtils.secondary("Now lets create a strong password(at least 8 characters): ");
            processPassword();
        } catch (RequestException e) {
            restartWithError(e.getMessage()+ANSI_RESET+"\nplease try again.");
        }

    }

    void processPassword() {
        String password = scanner.nextLine();
        User user = new User(userName, password);
        Request request = new Request("POST", "signup");
        request.setBody(user);
        try {
            ClientConfiguration.getInstance().getRequestManager().sendRequest(request);
            UIUtils.successful("Sign up successful! press Enter to continue");
            scanner.nextLine();
            new LoginScreen(scanner).guide().process();
        } catch (RequestException e) {
            if (e.getCode() == Response.ERR_WEAK_PASSWORD) {
                UIUtils.danger("You have entered an invalid password: "+e.getMessage());
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
}
