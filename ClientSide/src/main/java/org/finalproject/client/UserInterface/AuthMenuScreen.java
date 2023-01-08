package org.finalproject.client.UserInterface;

import java.util.Scanner;

public class AuthMenuScreen extends UIScreen {
    public AuthMenuScreen(Scanner scanner) {
        super(scanner);
    }

    @Override
    void printGuideMessage() {
        System.out.println("""
                Main Menu: enter number to select:
                 1. Sign up
                 2. Log in
                 3. Exit""");
    }

    @Override
    void processInput() {
        String line = scanner.nextLine();
        switch (line) {
            case "1" -> new SignUpScreen(scanner).guide().process();
            case "2" -> new LoginScreen(scanner).guide().process();
            case "3" -> {
                System.out.println("good bye!");
                System.exit(0);
            }
            default -> this.restartWithError(line+" is not a meaningful command in this context.");
        }
    }
}
