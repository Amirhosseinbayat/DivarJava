package org.finalproject.client.UserInterface;

import org.finalproject.DataObject.User;
import org.finalproject.client.ClientConfiguration;
import org.finalproject.client.Http.IHttpRequestManager;
import org.finalproject.client.Http.Request;
import org.finalproject.client.Http.RequestException;
import org.finalproject.client.Http.Response;
import org.finalproject.client.ImprovedUserInterface.BackSupportedInputHandler;
import org.finalproject.client.ImprovedUserInterface.InputHandler;
import org.finalproject.client.ImprovedUserInterface.Navigation;

public class ProfileScreen extends UIScreen {

    User user;

    String previousEmail;


    public ProfileScreen() {
        user = ClientConfiguration.getInstance().getUser();
    }

    InputHandler menuHandler = new BackSupportedInputHandler() {
        @Override
        public boolean handleValidInput(String input) {
            switch (input) {
                case "1" -> processUsernameChange();
                case "2" -> processFirstNameChange();
                case "3" -> processLastNameChange();
                case "4" -> processEmailChange();
                case "5" -> processPhoneChange();
                case "6" -> processCityChange();
                case "7" -> processProfilePicChange();
                case "8" -> processPasswordChange();
                case "back" -> Navigation.popBackStack();
                default -> {
                    return false;
                }
            }
            return true;
        }
    };

    @Override
    public void startScreen() {
        UIUtils.header("Profile Page");
        assert user != null;
        UIUtils.form("1. Username: ", user.getUsername());
        UIUtils.form("2. First name: ", user.getFirstName());
        UIUtils.form("3. Last name: ", user.getLastName());
        UIUtils.form("4. Email: ", user.getEmailAddress());
        UIUtils.form("5. Phone: ", user.getPhoneNumber());
        UIUtils.form("6. City: ", user.getCity());
        UIUtils.form("7. Profile picture: ", user.getProfilePictureUrl());
        UIUtils.danger("8. Change your password");
        UIUtils.secondary("Enter the number of any item to edit it, or enter 'back' to go back!");
        promptInput(menuHandler);
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
        startScreen();
    }

    void processUsernameChange() {
        promptInput("OK! enter the username which you want to have."+
                "\npress enter to go back.", usernameHandler);

    }

    InputHandler usernameHandler = new BackSupportedInputHandler() {
        @Override
        public boolean handleValidInput(String input) {
            String previous = user.getUsername();
            if (input.isEmpty() || input.equals("\n")) {
                return false;
            }
            user.setUsername(input);
            try {
                trySaveUserObject("Update successful.\n"+
                        "Remember to login as '"+input+"' next time.");
                return true;
            } catch (RequestException e) {
                UIUtils.danger("failed to update username: "+e.getMessage());
                user.setUsername(previous);
                System.out.println("Try again, Enter a valid username. \npress enter to go back.");
                return false;
            }
        }
    };

    void processPasswordChange() {
        promptInput("""
                        Enter the new password.
                        send 'back' to go back."""
                , passwordHandler);

    }

    void processFirstNameChange() {

        String previous = user.getFirstName();
        promptInput("OK! enter the name you want to set as your firstName", new BackSupportedInputHandler() {
            @Override
            public boolean handleValidInput(String input) {
                user.setFirstName(input);
                try {
                    trySaveUserObject();
                    return true;
                } catch (RequestException e) {
                    UIUtils.danger("failed to update firstName: "+e.getMessage());
                    user.setFirstName(previous);
                    System.out.println("Try again with a different one. \npress enter to go back.");
                    return false;
                }
            }
        });

    }

    InputHandler passwordHandler = new BackSupportedInputHandler() {
        @Override
        public boolean handleValidInput(String input) {
            user.setNewPassword(input); //current password itself is used for Authentication.
            // setting newPassword tells the server that we know the current password and want to change it to the new one.
            try {
                trySaveUserObject("Update successful.\n"+
                        "Remember to login with your new password next time.");
                return true;
            } catch (RequestException e) {
                UIUtils.danger(e.getMessage());
                System.out.println("Try again, Enter a valid password \npress enter to go back.");
                return false;
            }
        }
    };

    void processLastNameChange() {
        String previousLastName = user.getLastName();
        promptInput("OK! enter the name you want to set as your lastName", new BackSupportedInputHandler() {
            @Override
            public boolean handleValidInput(String input) {
                user.setLastName(input);
                try {
                    trySaveUserObject();
                    return true;
                } catch (RequestException e) {
                    UIUtils.danger("failed to update lastName: "+e.getMessage());
                    user.setLastName(previousLastName);
                    System.out.println("Try again with a different one. \npress enter to go back.");
                    return false;
                }
            }
        });


    }

    void processEmailChange() {
        previousEmail = user.getEmailAddress();
        promptInput("OK! enter your email address carefully.", emailHandler);
    }

    void processPhoneChange() {

        String previous = user.getPhoneNumber();

        promptInput("OK! enter your phone number for it to be updated.", new BackSupportedInputHandler() {
            @Override
            public boolean handleValidInput(String input) {
                user.setPhoneNumber(input);
                try {
                    trySaveUserObject();
                    return true;
                } catch (RequestException e) {
                    UIUtils.danger("failed to update phone: "+e.getMessage());
                    user.setPhoneNumber(previous);
                    System.out.println("Try again with a different one. \npress enter to go back.");
                    return false;
                }
            }
        });

    }

    void processCityChange() {
        String previous = user.getCity();
        promptInput("OK! enter the name of the city where you live."+
                " this will be used to show local placards to you.", new BackSupportedInputHandler() {
            @Override
            public boolean handleValidInput(String input) {
                user.setCity(input);
                try {
                    trySaveUserObject();
                    return true;
                } catch (RequestException e) {
                    UIUtils.danger("failed to update city: "+e.getMessage());
                    user.setCity(previous);
                    System.out.println("Try again with a different one. \npress enter to go back.");
                    return true;
                }
            }
        });
    }

    InputHandler emailHandler = new BackSupportedInputHandler() {

        @Override
        public boolean handleValidInput(String input) {
            user.setEmailAddress(input);
            try {
                trySaveUserObject();
                return true;
            } catch (RequestException e) {
                UIUtils.danger("failed to update Email: "+e.getMessage());
                user.setEmailAddress(previousEmail);
                System.out.println("Try again with a different one. \npress enter to go back.");
                return true;
            }
        }
    };

    void processProfilePicChange() {
        String previous = user.getProfilePictureUrl();
        promptInput("OK! enter a valid url to a jpg or png image for it to be set as your profile picture."
                , new BackSupportedInputHandler() {
                    @Override
                    public boolean handleValidInput(String input) {
                        user.setProfilePictureUrl(input);
                        try {
                            trySaveUserObject();
                            return true;
                        } catch (RequestException e) {
                            UIUtils.danger("failed to update profile picture: "+e.getMessage());
                            user.setProfilePictureUrl(previous);
                            System.out.println("Try again with a different one. \npress enter to go back.");
                            return false;
                        }
                    }
                });
    }








}
