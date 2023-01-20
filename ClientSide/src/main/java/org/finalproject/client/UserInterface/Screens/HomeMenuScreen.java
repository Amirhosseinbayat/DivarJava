package org.finalproject.client.UserInterface.Screens;

import org.finalproject.DataObject.User;
import org.finalproject.client.ClientConfiguration;
import org.finalproject.client.UserInterface.*;

public class HomeMenuScreen extends UIScreen {

    User user;
    InputHandler menuHandler = new BackSupportedInputHandler() {
        @Override
        public boolean handleValidInput(String input) {
            switch (input) {
                case "1" -> Navigation.navigateTo(new ProfileScreen());
                case "2" -> Navigation.navigateTo(new PlacardListScreen());
                case "3" -> Navigation.navigateTo(new WishPlacardsScreen());
                case "4" -> Navigation.navigateTo(new PlacardCreateScreen(user));
                case "5" -> Navigation.navigateTo(new MyPlacardListScreen());
                case "6" -> {
                    ClientConfiguration.getInstance().logOutUser();
                    Navigation.navigateTo(new AuthMenuScreen());
                }
                case "7" -> {
                    UIUtils.primary("Good bye!");
                    System.exit(0);
                }
                default -> {
                    System.out.println(input+" is not a meaningful command in this context.");
                    return false;
                }
            }
            return true;
        }
    };

    public HomeMenuScreen(User user) {
        this.user = user;
        clearScreen();
    }

    @Override
    public void startScreen() {
        UIUtils.header("Home Menu");
        UIUtils.primary("Hi "+user.getUsername()+"! Welcome to your home screen!");
        UIUtils.options("My profile",
                "Explore placards",
                "My wish list",
                "Create a placard",
                "See/edit my placards",
                "Log out",
                "Close program");
        promptInput(menuHandler);
    }
}
