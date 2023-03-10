package org.finalproject.client.UserInterface;

public abstract class BackSupportedInputHandler implements InputHandler {

    protected final String backCommand;

    public BackSupportedInputHandler(String backCommand) {
        this.backCommand = backCommand;
    }

    public BackSupportedInputHandler() {
        this.backCommand = "back";
    }

    @Override
    public boolean handle(String input) {
        if (input.equalsIgnoreCase(backCommand)) {
            Navigation.popBackStack();
            return true;
        }
        return handleValidInput(input);
    }

    public abstract boolean handleValidInput(String input);
}
