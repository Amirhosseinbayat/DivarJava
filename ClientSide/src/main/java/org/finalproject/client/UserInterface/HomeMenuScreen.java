package org.finalproject.client.UserInterface;

import org.finalproject.DataObject.User;
import org.finalproject.client.ClientConfiguration;
import org.finalproject.client.ImprovedUserInterface.BackSupportedInputHandler;
import org.finalproject.client.ImprovedUserInterface.InputHandler;
import org.finalproject.client.ImprovedUserInterface.Navigation;

public class HomeMenuScreen extends UIScreen {

    User user;
    InputHandler menuHandler = new BackSupportedInputHandler() {
        @Override
        public boolean handleValidInput(String input) {
            switch (input) {
                case "1" -> Navigation.navigateTo(new ProfileScreen());
                case "2" -> Navigation.navigateTo(new PlacardsScreen());
                case "3" -> Navigation.navigateTo(new WishPlacardsScreen());
                case "4" -> Navigation.navigateTo(new CreatePlacardScreen(user));
                case "5" -> Navigation.navigateTo(new MyPlacardsScreen());
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
