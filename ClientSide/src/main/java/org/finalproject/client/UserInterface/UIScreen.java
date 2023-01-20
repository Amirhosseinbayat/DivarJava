package org.finalproject.client.UserInterface;


import org.finalproject.client.ImprovedUserInterface.InputHandler;
import org.finalproject.client.ImprovedUserInterface.Navigation;

import java.util.Scanner;

@SuppressWarnings("unused")
public abstract class UIScreen {

    protected static final Scanner scanner = new Scanner(System.in);

    public UIScreen() {
        clearScreen();
    }

    public void clearScreen() {
        UIUtils.clearScreen();
    }

    public abstract void startScreen();

    public void promptInput(InputHandler inputHandler) {
        promptInput(null, inputHandler);
    }

    public void promptInput(String guide, InputHandler inputHandler) {
        if (guide != null) System.out.println(guide);
        String input = scanner.nextLine();
        if (input.equals("back")) {
            Navigation.popBackStack();
            return;
        }
        if (!inputHandler.handle(input)) {
            promptInput(guide, inputHandler);
        }
    }

    String prompt(String guide) {
        UIUtils.secondary(guide);
        return scanner.nextLine();
    }

    String requiredPrompt(String input) {
        if (input.isEmpty() || input.isBlank()) {
            UIUtils.warning("You can't leave this field blank.");
            requiredPrompt(scanner.nextLine());
        }
        return input;
    }

}
