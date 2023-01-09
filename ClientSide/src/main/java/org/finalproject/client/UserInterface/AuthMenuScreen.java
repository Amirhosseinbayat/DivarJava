package org.finalproject.client.UserInterface;

import java.util.Scanner;

public class AuthMenuScreen extends UIScreen {
    public AuthMenuScreen(Scanner scanner) {
        super(scanner);
    }

    @Override
    void printGuideMessage() {
        UIUtils.header("Main Menu");
        UIUtils.options("Sign up", "Log in", "Exit");
    }

    @Override
    void processInput() {
        String line = scanner.nextLine();
        switch (line) {
            case "1" -> new SignUpScreen(scanner).guide().process();
            case "2" -> new LoginScreen(scanner).guide().process();
            case "3" -> {
                UIUtils.primary("Good bye!");
                System.exit(0);
            }
            default -> this.restartWithError(line+" is not a meaningful command in this context.");
        }
    }
}
