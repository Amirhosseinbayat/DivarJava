package org.finalproject.client.UserInterface;

import org.finalproject.client.ImprovedUserInterface.BackSupportedInputHandler;
import org.finalproject.client.ImprovedUserInterface.InputHandler;
import org.finalproject.client.ImprovedUserInterface.Navigation;

public class AuthMenuScreen extends UIScreen {
    InputHandler inputHandler = new BackSupportedInputHandler("3") {
        @Override
        public boolean handleValidInput(String input) {
            switch (input) {
                case "1" -> Navigation.navigateTo(new SignUpScreen());
                case "2" -> Navigation.navigateTo(new LoginScreen());
                default -> {
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
