package org.finalproject.client.UserInterface;

import org.finalproject.DataObject.SalePlacard;

import java.util.Scanner;

public class EditPlacardScreen extends PlacardScreen{
    public EditPlacardScreen(Scanner scanner, SalePlacard placard, UIScreen previousScreen) {
        super(scanner, placard, previousScreen);
    }

    @Override
    void printGuideMessage() {
        UIUtils.header("Placard Edition Page");
        UIUtils.form("1. Title: ", placard.getTitle());
        UIUtils.form("2. Images: ", placard.getImagesUrl().toString());
        UIUtils.form("3. Description: ", placard.getDescription());
        UIUtils.form("4. City: ", placard.getCity());
        UIUtils.form("5. Price: ", Long.toString(placard.getPriceInRials()));
        UIUtils.form("6. Owner's phone number: ", placard.getPhoneNumber());
        UIUtils.primary("Enter the number of any item to edit it");
        UIUtils.danger("7. Delete the placard");
        UIUtils.primary("8. Back to previous page");
        UIUtils.hr();
    }
    //TODO other stuff
}
