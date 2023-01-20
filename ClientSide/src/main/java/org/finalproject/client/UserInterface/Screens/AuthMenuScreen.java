package org.finalproject.client.UserInterface.Screens;

import org.finalproject.client.UserInterface.*;

public class AuthMenuScreen extends UIScreen {
    InputHandler inputHandler = new BackSupportedInputHandler("3") {
        @Override
        public boolean handleValidInput(String input) {
            switch (input) {
                case "1" -> Navigation.navigateTo(new SignUpScreen());
                case "2" -> Navigation.navigateTo(new LoginScreen());
                default -> {
                    System.out.println(input+" is not a meaningful command here. try again.");
                    return false;
                }
            }
            return true;
        }
    };

    public AuthMenuScreen() {

    }

    @Override
    public void startScreen() {
        UIUtils.header("Main Menu");
        UIUtils.options("Sign up", "Log in", "Exit");
        promptInput(inputHandler);
    }
}
