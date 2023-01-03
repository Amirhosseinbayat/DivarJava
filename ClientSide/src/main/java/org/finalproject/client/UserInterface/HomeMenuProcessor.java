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
        System.out.println("Hi "+user.getName()+"! Welcome to your home screen!");
        System.out.println(ANSI_YELLOW+"TODO: implement this menu!");
    }

    @Override
    void processInput() {
        String input = scanner.nextLine();
    }
}
