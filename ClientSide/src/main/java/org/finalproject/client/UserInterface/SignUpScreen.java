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
        System.out.println("OK! enter a username of your choice for me to check it.");
    }

    @Override
    void processInput() {
        userName = scanner.nextLine();
        System.out.println("checking if "+userName+" is not already taken...");
        try {
            ClientConfiguration.getInstance().getRequestManager()
                    .sendRequest(new Request("POST", "username/check")
                            .setBody(userName));
            System.out.println(userName+" looks ok. \n"+ANSI_BLUE+"Now lets create a strong password. "+
                    "enter a password with at least 8 characters."+ANSI_RESET);
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
            System.out.println("sign up successful! now you can log in to your account.");
            new LoginScreen(scanner).guide().process();
        } catch (RequestException e) {
            if (e.getCode() == Response.ERR_WEAK_PASSWORD) {
                System.out.println("you have entered an invalid password: "+e.getMessage());
                System.out.println("please try a better password...");
                processPassword();
            }
            if (e.getCode() == Response.ERR_USERNAME_TAKEN) {
                System.out.println("while you were deciding about your password,"+
                        " someone else has occupied the username you had selected! you will have to start over."
                        +e.getMessage());
                processInput();
            }
        }

    }
}
