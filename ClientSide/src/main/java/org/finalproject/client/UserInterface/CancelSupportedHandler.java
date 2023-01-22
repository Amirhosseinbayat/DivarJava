package org.finalproject.client.UserInterface;

public abstract class CancelSupportedHandler extends BackSupportedInputHandler {

    public CancelSupportedHandler(String backCommand) {
        super(backCommand);
    }

    public CancelSupportedHandler() {
    }

    @Override
    public boolean handle(String input) {
        if (input.equals(backCommand)) {
            onCancel();
            return true;
        }
        return handleValidInput(input);
    }

    protected abstract void onCancel();
}
