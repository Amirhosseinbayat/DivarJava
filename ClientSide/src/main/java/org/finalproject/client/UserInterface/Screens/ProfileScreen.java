package org.finalproject.client.UserInterface.Screens;

import org.finalproject.DataObject.User;
import org.finalproject.client.ClientConfiguration;
import org.finalproject.client.Http.IHttpRequestManager;
import org.finalproject.client.Http.Request;
import org.finalproject.client.Http.RequestException;
import org.finalproject.client.Http.Response;
import org.finalproject.client.UserInterface.*;

public class ProfileScreen extends UIScreen {

    User originalUser;
    User editedUser;
    InputHandler usernameHandler = new BackSupportedInputHandler() {
        @Override
        public boolean handleValidInput(String input) {
            if (input.isEmpty() || input.equals("\n")) {
                return false;
            }
            getNewCopy().setUsername(input);
            try {
                trySaveUserObject("Update successful.\n"+
                        "Remember to login as '"+input+"' next time.");
                return true;
            } catch (RequestException e) {
                UIUtils.danger("failed to update username: "+e.getMessage());
                System.out.println("Try again, Enter a valid username. \npress enter to go back.");
                return false;
            }
        }
    };
    InputHandler passwordHandler = new BackSupportedInputHandler() {
        @Override
        public boolean handleValidInput(String input) {
            getNewCopy().setNewPassword(input); //current password itself is used for Authentication.
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
    InputHandler emailHandler = new BackSupportedInputHandler() {

        @Override
        public boolean handleValidInput(String input) {
            getNewCopy().setEmailAddress(input);
            try {
                trySaveUserObject();
                return true;
            } catch (RequestException e) {
                UIUtils.danger("failed to update Email: "+e.getMessage());
                System.out.println("Try again with a different one. \npress enter to go back.");
                return false;
            }
        }
    };

    void trySaveUserObject() throws RequestException {
        trySaveUserObject("Update successful!");
    }

    public ProfileScreen() {
        originalUser = ClientConfiguration.getInstance().getUser();
    }

    void processUsernameChange() {
        promptInput("OK! enter the username which you want to have."+
                "\npress enter to go back.", usernameHandler);

    }

    public User getNewCopy() {
        editedUser = originalUser.clone();
        return editedUser;
    }

    void processPasswordChange() {
        promptInput("""
                        Enter the new password.
                        send 'back' to go back."""
                , passwordHandler);

    }

    @Override
    public void startScreen() {
        UIUtils.header("Profile Page");
        assert originalUser != null;
        UIUtils.form("1. Username: ", originalUser.getUsername());
        UIUtils.form("2. First name: ", originalUser.getFirstName());
        UIUtils.form("3. Last name: ", originalUser.getLastName());
        UIUtils.form("4. Email: ", originalUser.getEmailAddress());
        UIUtils.form("5. Phone: ", originalUser.getPhoneNumber());
        UIUtils.form("6. City: ", originalUser.getCity());
        UIUtils.form("7. Profile picture: ", originalUser.getProfilePictureUrl());
        UIUtils.danger("8. Change your password");
        UIUtils.secondary("Enter the number of any item to edit it, or enter 'back' to go back!");
        promptInput(menuHandler);
    }

    void trySaveUserObject(String message) throws RequestException {
        IHttpRequestManager manager = ClientConfiguration.getInstance().getRequestManager();

        Response response =
                manager.sendRequest(new Request("POST", "user/update").setBody(editedUser));
        originalUser = response.getResponseBody();
        ClientConfiguration.getInstance().setUser(originalUser);
        UIUtils.successful(message);
        startScreen();
    }

    void processFirstNameChange() {
        promptInput("OK! enter the name you want to set as your firstName", new BackSupportedInputHandler() {
            @Override
            public boolean handleValidInput(String input) {
                getNewCopy().setFirstName(input);
                try {
                    trySaveUserObject();
                    return true;
                } catch (RequestException e) {
                    UIUtils.danger("failed to update firstName: "+e.getMessage());
                    System.out.println("Try again with a different one. \npress enter to go back.");
                    return false;
                }
            }
        });

    }

    void processEmailChange() {
        promptInput("OK! enter your email address carefully.", emailHandler);
    }

    void processLastNameChange() {
        promptInput("OK! enter the name you want to set as your lastName", new BackSupportedInputHandler() {
            @Override
            public boolean handleValidInput(String input) {
                getNewCopy().setLastName(input);
                try {
                    trySaveUserObject();
                    return true;
                } catch (RequestException e) {
                    UIUtils.danger("failed to update lastName: "+e.getMessage());
                    System.out.println("Try again with a different one. \npress enter to go back.");
                    return false;
                }
            }
        });


    }

    void processPhoneChange() {

        promptInput("OK! enter your phone number for it to be updated.", new BackSupportedInputHandler() {
            @Override
            public boolean handleValidInput(String input) {
                getNewCopy().setPhoneNumber(input);
                try {
                    trySaveUserObject();
                    return true;
                } catch (RequestException e) {
                    UIUtils.danger("failed to update phone: "+e.getMessage());
                    System.out.println("Try again with a different one. \npress enter to go back.");
                    return false;
                }
            }
        });

    }

    void processCityChange() {
        promptInput("OK! enter the name of the city where you live."+
                " this will be used to show local placards to you.", new BackSupportedInputHandler() {
            @Override
            public boolean handleValidInput(String input) {
                getNewCopy().setCity(input);
                try {
                    trySaveUserObject();
                    return true;
                } catch (RequestException e) {
                    UIUtils.danger("failed to update city: "+e.getMessage());
                    System.out.println("Try again with a different one. \npress enter to go back.");
                    return false;
                }
            }
        });
    }

    void processProfilePicChange() {
        promptInput("OK! enter a valid url to a jpg or png image for it to be set as your profile picture."
                , new BackSupportedInputHandler() {
                    @Override
                    public boolean handleValidInput(String input) {
                        getNewCopy().setProfilePictureUrl(input);
                        try {
                            trySaveUserObject();
                            return true;
                        } catch (RequestException e) {
                            UIUtils.danger("failed to update profile picture: "+e.getMessage());
                            System.out.println("Try again with a different one. \npress enter to go back.");
                            return false;
                        }
                    }
                });
    }








}
