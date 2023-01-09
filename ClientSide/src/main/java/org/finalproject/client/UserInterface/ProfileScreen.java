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
        }
    }

    String getInputBy(String guide) {
        UIUtils.secondary(guide);
        return scanner.nextLine();
    }

    void trySaveUserObject() {
        trySaveUserObject("Update successful!");
    }

    void trySaveUserObject(String message) {
        IHttpRequestManager manager = ClientConfiguration.getInstance().getRequestManager();
        try {
            Response response =
                    manager.sendRequest(new Request("POST", "user/update").setBody(user));
            user = response.getResponseBody();
            ClientConfiguration.getInstance().setUser(user);
        } catch (RequestException e) {
            UIUtils.danger("Could not update profile: "+e.getMessage());
            return;
        }
        UIUtils.successful(message);
        guide();
        processInput();
    }
    void processUsernameChange() {
        String newUsername = getInputBy("OK! enter the username which you want to have.");
        user.setUsername(newUsername);
        trySaveUserObject("Update successful.\n"+
                "Remember to login as '"+newUsername+"' next time.");
    }

    void processFirstNameChange(){
        String input = getInputBy("OK! enter the name you want to set as your firstName");
        user.setFirstName(input);
        trySaveUserObject();
    }

    void processLastNameChange(){
        String input = getInputBy("OK! enter the name you want to set as your lastName");
        user.setLastName(input);
        trySaveUserObject();
    }

    void processEmailChange(){
        String input = getInputBy("OK! enter your email address carefully.");
        user.setEmailAddress(input);
        trySaveUserObject();
    }

    void processPhoneChange(){
        String input = getInputBy("OK! enter your phone number for it to be updated.");
        user.setPhoneNumber(input);
        trySaveUserObject();
    }

    void processCityChange(){
        String input = getInputBy("OK! enter the name of the city where you live." +
                " this will be used to show local placards to you.");
        user.setCity(input);
        trySaveUserObject();
    }

    void processProfilePicChange(){
        String input = getInputBy("OK! enter a valid url to a jpg or png image for it to be set as your profile picture.");
        user.setProfilePictureUrl(input);
        trySaveUserObject();
    }

}
