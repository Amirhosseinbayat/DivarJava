package org.finalproject.client.UserInterface;

import java.util.Scanner;

public class MainMenuProcessor extends InputProcessor {
    public MainMenuProcessor(Scanner scanner) {
        super(scanner);
    }

    @Override
    void printGuideMessage() {
        System.out.println("Main Menu: enter number to select:"+
                "\n 1. Sign up"+
                "\n 2. Log in"+
                "\n 3. Exit");
    }

    @Override
    void processInput() {
        String line = scanner.nextLine();
        switch (line) {
            case "1":
                new SignUpProcessor(scanner).guide().process();
                break;
            case "2":
                new LoginProcessor(scanner).guide().process();
                break;
            case "3":
                System.out.println("good bye!");
                System.exit(0);
                break;
            default:
                this.restartWithError(line+" is not a meaningful command in this context.");
        }
    }
}
