package org.finalproject.client.UserInterface;

import org.finalproject.DataObject.User;
import org.finalproject.client.ClientConfiguration;

import java.util.Scanner;

public class HomeMenuScreen extends UIScreen {

    public HomeMenuScreen(Scanner scanner) {
        super(scanner);
    }

    @Override
    void printGuideMessage() {
        User user = ClientConfiguration.getInstance().getUser();
        UIUtils.header("Home Menu");
        UIUtils.primary("Hi "+user.getUsername()+"! Welcome to your home screen!");
        UIUtils.options("My profile", "Explore placards", "My wish list", "Create a placard", "See/edit my placards", "Log out", "Close program");
    }

    @Override
    void processInput() {
        String input = scanner.nextLine();
        switch (input) {
            case "1" -> {
                new ProfileScreen(scanner).guide().process();
                return;
            }
            case "2" -> {
                new PlacardsScreen(scanner).guide().process();
                return;
            }
            case "3", "4", "5" -> {
                System.out.println(ANSI_YELLOW+"not implemented yet..."+ANSI_RESET);
                processInput();
            }
            case "7" -> {
                UIUtils.primary("Good bye!");
                System.exit(0);
            }
            default -> restartWithError(input+" is not a meaningful command in this context.");
        }
    }
}