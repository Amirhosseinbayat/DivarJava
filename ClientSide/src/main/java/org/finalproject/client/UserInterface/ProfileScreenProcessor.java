package org.finalproject.client.UserInterface;

import org.finalproject.DataObject.User;
import org.finalproject.client.ClientConfiguration;
import org.finalproject.client.Http.IHttpRequestManager;
import org.finalproject.client.Http.Request;
import org.finalproject.client.Http.RequestException;
import org.finalproject.client.Http.Response;

import java.util.Scanner;

public class ProfileScreenProcessor extends InputProcessor{

    User user;
    public ProfileScreenProcessor(Scanner scanner) {
        super(scanner);
        user = ClientConfiguration.getInstance().getUser();
    }

    @Override
    void printGuideMessage() {
        System.out.println("Your profile info is listed below. send the number of any item to edit it.");
        assert user!=null;
        System.out.println(ANSI_BLUE + "1. username: " + ANSI_RESET + user.getUsername());
        System.out.println(ANSI_BLUE + "2. first name: " + ANSI_RESET + user.getFirstName());
        System.out.println(ANSI_BLUE + "3. last name: " + ANSI_RESET + user.getLastName());
        System.out.println(ANSI_BLUE + "4. email: " + ANSI_RESET + user.getEmailAddress());
        System.out.println(ANSI_BLUE + "5. phone: " + ANSI_RESET + user.getPhoneNumber());
        System.out.println(ANSI_BLUE + "6. city: " + ANSI_RESET + user.getCity());
        System.out.println(ANSI_BLUE + "7. profile picture: " + ANSI_RESET + user.getProfilePictureUrl());
    }

    @Override
    void processInput() {
        String number = scanner.nextLine();
        switch (number){
            case "1":processUsernameChange();break;
            case "2":processFirstNameChange();break;
            case "3":processLastNameChange();break;
            case "4":processEmailChange();break;
            case "5":processPhoneChange();break;
            case "6":processCityChange();break;
            case "7":processProfilePicChange();break;

        }
    }

    String getInputBy(String guide){
        System.out.println(guide);
        return scanner.nextLine();
    }

    boolean trySaveUserObject() {
        IHttpRequestManager manager = ClientConfiguration.getInstance().getRequestManager();
        try {
            Response response =
                    manager.sendRequest(new Request("POST","user/update").setBody(user));
            user = response.getResponseBody();
            ClientConfiguration.getInstance().setUser(user);
            System.out.println(ANSI_GREEN + "Update successful!"+ANSI_RESET);
            guide();
            processInput();
            return true;
        } catch (RequestException e) {
            System.out.println(ANSI_RED +"Could not update profile: " + e.getMessage()
                    + ANSI_RESET);
            return false;
        }
    }
    void processUsernameChange(){
        String newUsername = getInputBy("OK! enter the username which you want to have.");
        user.setUsername(newUsername);
        trySaveUserObject();
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
