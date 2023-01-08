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
        System.out.println("Hi "+user.getUsername()+"! Welcome to your home screen!");
        System.out.println("1. my profile");
        System.out.println("2. explore placards");
        System.out.println("3. my wish list");
        System.out.println("4. create a placard");
        System.out.println("5. see/edit my placards");
        System.out.println("send 'exit' to close the program.");
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
            case "exit" -> {
                System.out.println("bye!");
                System.exit(0);
            }
            default -> restartWithError(input+" is not a meaningful command in this context.");
        }
    }
}
