package org.finalproject.client.UserInterface;

import org.finalproject.DataObject.User;
import org.finalproject.client.ClientConfiguration;
import org.finalproject.client.Http.IHttpRequestManager;
import org.finalproject.client.Http.Request;
import org.finalproject.client.Http.RequestException;
import org.finalproject.client.Http.Response;

import java.util.Scanner;

public class ProfileScreen extends UIScreen {

    User user;

    public ProfileScreen(Scanner scanner) {
        super(scanner);
        user = ClientConfiguration.getInstance().getUser();
    }

    @Override
    void printGuideMessage() {
        UIUtils.header("Profile Page");
        assert user!=null;
        UIUtils.form("1. Username: ", user.getUsername());
        UIUtils.form("2. First name: ", user.getFirstName());
        UIUtils.form("3. Last name: ", user.getLastName());
        UIUtils.form("4. Email: ", user.getEmailAddress());
        UIUtils.form("5. Phone: ", user.getPhoneNumber());
        UIUtils.form("6. City: ", user.getCity());
        UIUtils.form("7. Profile picture: ", user.getProfilePictureUrl());
        UIUtils.secondary("Enter the number of any item to edit it, or enter 'back' to go back!");
    }

    @Override
    void processInput() {
        String input = scanner.nextLine();
        switch (input) {
            case "1" -> processUsernameChange();
            case "2" -> processFirstNameChange();
            case "3" -> processLastNameChange();
            case "4" -> processEmailChange();
            case "5" -> processPhoneChange();
            case "6" -> processCityChange();
            case "7" -> processProfilePicChange();
            case "back" -> {
                new HomeMenuScreen(scanner).guide().process();
            }
            default -> this.restartWithError(input+" is not a meaningful command in this context.");
        }
    }

    void trySaveUserObject() throws RequestException {
        trySaveUserObject("Update successful!");
    }

    void trySaveUserObject(String message) throws RequestException {
        IHttpRequestManager manager = ClientConfiguration.getInstance().getRequestManager();

        Response response =
                manager.sendRequest(new Request("POST", "user/update").setBody(user));
        user = response.getResponseBody();
        ClientConfiguration.getInstance().setUser(user);
        UIUtils.successful(message);
        guide();
        processInput();
    }
    void processUsernameChange() {
        String input = getInputBy("OK! enter the username which you want to have.\npress enter to go back.");
        String previous = user.getUsername();
        while (true) {
            if (input.isEmpty() || input.equals("\n")) {
                guide();
                process();
                return;
            }
            user.setUsername(input);
            try {
                trySaveUserObject("Update successful.\n"+
                        "Remember to login as '"+input+"' next time.");
                break;
            } catch (RequestException e) {
                UIUtils.danger("failed to update username: "+e.getMessage());
                user.setUsername(previous);
                input = getInputBy("Try again, Enter a valid email address. \npress enter to go back.");
            }
        }
    }

    void processFirstNameChange() {
        String input = getInputBy("OK! enter the name you want to set as your firstName");
        String previous = user.getFirstName();
        while (true) {
            if (input.isEmpty() || input.equals("\n")) {
                guide();
                process();
                return;
            }
            user.setFirstName(input);
            try {
                trySaveUserObject();
                break;
            } catch (RequestException e) {
                UIUtils.danger("failed to update firstName: "+e.getMessage());
                user.setFirstName(previous);
                input = getInputBy("Try again with a different one. \npress enter to go back.");
            }
        }
    }

    void processLastNameChange() {
        String input = getInputBy("OK! enter the name you want to set as your lastName");
        String previous = user.getLastName();
        while (true) {
            if (input.isEmpty() || input.equals("\n")) {
                guide();
                process();
                return;
            }
            user.setLastName(input);
            try {
                trySaveUserObject();
                break;
            } catch (RequestException e) {
                UIUtils.danger("failed to update lastName: "+e.getMessage());
                user.setLastName(previous);
                input = getInputBy("Try again with a different one. \npress enter to go back.");
            }
        }
    }

    void processEmailChange() {
        String input = getInputBy("OK! enter your email address carefully.");
        String previous = user.getEmailAddress();
        while (true) {
            if (input.isEmpty() || input.equals("\n")) {
                guide();
                process();
                return;
            }
            user.setEmailAddress(input);
            try {
                trySaveUserObject();
                break;
            } catch (RequestException e) {
                UIUtils.danger("failed to update Email: "+e.getMessage());
                user.setEmailAddress(previous);
                input = getInputBy("Try again with a different one. \npress enter to go back.");
            }
        }
    }

    void processPhoneChange() {
        String input = getInputBy("OK! enter your phone number for it to be updated.");
        String previous = user.getPhoneNumber();
        while (true) {
            if (input.isEmpty() || input.equals("\n")) {
                guide();
                process();
                return;
            }
            user.setPhoneNumber(input);
            try {
                trySaveUserObject();
                break;
            } catch (RequestException e) {
                UIUtils.danger("failed to update phone: "+e.getMessage());
                user.setPhoneNumber(previous);
                input = getInputBy("Try again with a different one. \npress enter to go back.");

            }
        }
    }

    void processCityChange() {
        String input = getInputBy("OK! enter the name of the city where you live."+
                " this will be used to show local placards to you.");
        String previous = user.getCity();
        while (true) {
            if (input.isEmpty() || input.equals("\n")) {
                guide();
                process();
                return;
            }
            user.setCity(input);
            try {
                trySaveUserObject();
                break;
            } catch (RequestException e) {
                UIUtils.danger("failed to update city: "+e.getMessage());
                user.setCity(previous);
                input = getInputBy("Try again with a different one. \npress enter to go back.");
            }
        }
    }

    void processProfilePicChange() {
        String input = getInputBy("OK! enter a valid url to a jpg or png image for it to be set as your profile picture.");
        String previous = user.getProfilePictureUrl();
        while (true) {
            if (input.isEmpty() || input.equals("\n")) {
                guide();
                process();
                return;
            }
            user.setProfilePictureUrl(input);
            try {
                trySaveUserObject();
            } catch (RequestException e) {
                UIUtils.danger("failed to update profile picture: "+e.getMessage());
                user.setProfilePictureUrl(previous);
                input = getInputBy("Try again with a different one. \npress enter to go back.");
            }
        }
    }

}
