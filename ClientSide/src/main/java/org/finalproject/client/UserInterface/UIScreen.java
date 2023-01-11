package org.finalproject.client.UserInterface;


import java.util.Scanner;

@SuppressWarnings("unused")
public abstract class UIScreen {
    final Scanner scanner;
    int numberOfInvalidInputs = 0;

    public UIScreen(Scanner scanner) {
        this.scanner = scanner;
        clearScreen();
    }

    public UIScreen guide() {
        printGuideMessage();
        return this;
    }

    public void clearScreen() {
        UIUtils.clearScreen();
    }

    abstract void printGuideMessage();

    public void process() {
        processInput();
    }

    abstract void processInput();

    void restartWithError(String description) {
        UIUtils.danger("===================\n"+description+"\n===================");
        numberOfInvalidInputs++;
        processInput();
    }

    String getInputBy(String guide) {
        UIUtils.secondary(guide);
        return scanner.nextLine();
    }


}
