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

    @Override
    void processInput(){
        String input = scanner.nextLine();
        switch (input){
            case "1" -> processTitleChange();
//            case "2" -> processImagesChange();
//            case "3" -> processDescriptionChange();
//            case "4" -> processCityChange();
//            case "5" -> processPriceChange();
//            case "6" -> processPhoneNumberChange();
//            case "7" -> processDeletion();
            case "8" -> {
                previousScreen.guide().process();
            }
        }
    }

    private void processTitleChange(){

    }

}
