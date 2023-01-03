package org.finalproject.client.UserInterface;


import java.util.Scanner;

@SuppressWarnings("unused")
public abstract class InputProcessor {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    final Scanner scanner;
    int numberOfInvalidInputs = 0;

    public InputProcessor(Scanner scanner) {
        this.scanner = scanner;
    }

    public InputProcessor guide() {
        printGuideMessage();
        return this;
    }

    abstract void printGuideMessage();

    public void process() {
        processInput();
    }

    abstract void processInput();

    void restartWithError(String description) {
        System.out.println(ANSI_RED+description+ANSI_RESET);
        numberOfInvalidInputs++;
        processInput();
    }


}
