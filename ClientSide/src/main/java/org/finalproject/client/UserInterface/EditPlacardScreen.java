package org.finalproject.client.UserInterface;

import org.finalproject.DataObject.SalePlacard;
import org.finalproject.client.Http.RequestException;

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
        String input = getInputBy("Enter new title (press Enter to go back): ");
        while (true) {
            if (input.isEmpty() || input.equals("\n")) {
                guide();
                process();
                return;
            }
            placard.setTitle(input);
            try {
                trySavePlacardObject("Title changed successfully.");
                break;
            } catch (RequestException e) {
                UIUtils.danger("failed to update title: "+e.getMessage());
                input = getInputBy("Try again, Enter a valid title. \npress enter to go back.");
            }
        }

    }

    private void processImagesChange(){
        String[] images = (String[]) placard.getImagesUrl().toArray();
        UIUtils.primary("Enter index of image to delete or enter new image url. (press Enter to go back)");
        for(int i = 0; i < images.length; i++)
            UIUtils.secondary((i+1) + ". " + images[i]);

        String input = scanner.nextLine();
        while(true){
            if(input.equals("")){
                guide().process();
                return;
            }
            try {
                int imageIndex = Integer.parseInt(input) - 1;
                if(imageIndex >= 0 && imageIndex < images.length){
                   placard.removeImageUrl(images[imageIndex]);
                }else{
                    placard.addImageUrl(input);
                }
            }catch(NumberFormatException ex){}
        }
    }
    void trySavePlacardObject() throws RequestException {
        trySavePlacardObject("Update successful!");
    }

    void trySavePlacardObject(String message) throws RequestException {
        //TODO related http request to update placard
        UIUtils.successful(message);
        guide();
        processInput();
    }
}
