package org.finalproject.client.UserInterface;

import org.finalproject.DataObject.User;
import org.finalproject.client.ClientConfiguration;

import java.util.Scanner;

public class HomeMenuProcessor extends InputProcessor {

    public HomeMenuProcessor(Scanner scanner) {
        super(scanner);
    }

    @Override
    void printGuideMessage() {
        User user = ClientConfiguration.getInstance().getUser();
        System.out.println("Hi "+user.getUsername()+"! Welcome to your home screen!");
        System.out.println("1. my profile");
    }

    @Override
    void processInput() {
        String input = scanner.nextLine();
        switch (input) {
            case "1":
                new ProfileScreenProcessor(scanner).guide().process();
                return;
            default:
                restartWithError(input+" is not a meaningful command in this context.");
        }
    }
}
