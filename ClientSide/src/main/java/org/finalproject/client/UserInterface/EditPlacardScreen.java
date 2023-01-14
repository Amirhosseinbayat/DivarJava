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
            case "2" -> processImagesChange();
            case "3" -> processDescriptionChange();
            case "4" -> processCityChange();
            case "5" -> processPriceChange();
            case "6" -> processPhoneNumberChange();
            case "7" -> processDeletion();
            case "8" -> {
                previousScreen.guide().process();
            }
        }
    }

    private void processTitleChange(){
        String prevTitle = placard.getTitle();
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
                placard.setTitle(prevTitle);
                UIUtils.danger("failed to update title: "+e.getMessage());
                input = getInputBy("Try again, Enter a valid title. \npress enter to go back.");
            }
        }

    }

    private void processImagesChange(){
        String[] images = (String[]) placard.getImagesUrl().toArray();
        UIUtils.primary("Enter index of image to delete or enter new image urls splitted by comma. (press Enter to go back)");
        for(int i = 0; i < images.length; i++)
            UIUtils.secondary((i+1) + ". " + images[i]);

        String input = scanner.nextLine();
        while(true){
            if(input.equals("") || input.equals("\n")){
                guide().process();
                return;
            }
            try {
                int imageIndex = Integer.parseInt(input) - 1;
                if(imageIndex >= 0 && imageIndex < images.length)
                   placard.removeImageUrl(images[imageIndex]);
                else{
                    UIUtils.warning("Invalid image index. try again");
                    input = scanner.nextLine();
                    continue;
                }
            }catch(NumberFormatException ex){
                for(String imgUrl : input.split(","))
                    placard.addImageUrl(imgUrl.trim());
            }

            try {
                trySavePlacardObject("Images updated successfully.");
                break;
            } catch (RequestException e) {
                placard.getImagesUrl().clear();
                for (String imgUrl : images)
                    placard.addImageUrl(imgUrl);
                UIUtils.danger("failed to update images: "+e.getMessage());
                input = getInputBy("Try again, Enter a valid syntax. \npress enter to go back.");
            }
        }
    }

    private void processDescriptionChange(){
        String prevDescription = placard.getDescription();
        String input = getInputBy("Type new description for your placard (press Enter to go back): ");
        while (true) {
            if (input.isEmpty() || input.equals("\n")) {
                guide();
                process();
                return;
            }
            placard.setDescription(input);
            try {
                trySavePlacardObject("Description updated successfully.");
                break;
            } catch (RequestException e) {
                placard.setDescription(prevDescription);
                UIUtils.danger("failed to update description: "+e.getMessage());
                input = getInputBy("Try again, Enter a valid description. \npress enter to go back.");
            }
        }
    }

    private void processCityChange(){
        String prevCity = placard.getCity();
        String input = getInputBy("Enter new City for your placard (press Enter to go back): ");
        while (true) {
            if (input.isEmpty() || input.equals("\n")) {
                guide();
                process();
                return;
            }
            placard.setCity(input);
            try {
                trySavePlacardObject("Related city updated successfully.");
                break;
            } catch (RequestException e) {
                placard.setCity(prevCity);
                UIUtils.danger("failed to update related city: "+e.getMessage());
                input = getInputBy("Try again, Enter a valid city. \npress enter to go back.");
            }
        }
    }

    private void processPriceChange(){
        long prevPrice = placard.getPriceInRials();
        String input = getInputBy("Type new price in rials for your placard (press Enter to go back): ");
        while (true) {
            if (input.isEmpty() || input.equals("\n")) {
                guide();
                process();
                return;
            }
            try{
                long price = Long.parseLong(input);
                placard.setPriceInRials(price);
            }catch(NumberFormatException ex){
                UIUtils.warning("Price must be a numeric value. try again");
                input = scanner.nextLine();
                continue;
            }

            try {
                trySavePlacardObject("Price of placard updated successfully.");
                break;
            } catch (RequestException e) {
                placard.setPriceInRials(prevPrice);
                UIUtils.danger("failed to update price: "+e.getMessage());
                input = getInputBy("Try again, Enter a valid price in rials. \npress enter to go back.");
            }
        }
    }

    private void processPhoneNumberChange(){
        String prevPhoneNumber = placard.getPhoneNumber();
        String input = getInputBy("Enter new phone number (press Enter to go back): ");
        while (true) {
            if (input.isEmpty() || input.equals("\n")) {
                guide();
                process();
                return;
            }
            placard.setPhoneNumber(input);
            try {
                trySavePlacardObject("Phone number updated successfully.");
                break;
            } catch (RequestException e) {
                placard.setPhoneNumber(prevPhoneNumber);
                UIUtils.danger("failed to update phone number: "+e.getMessage());
                input = getInputBy("Try again, Enter a valid phone number. \npress enter to go back.");
            }
        }
    }

    private void processDeletion(){
        //TODO related http request to delete the placard
    }

    void trySavePlacardObject(String message) throws RequestException {
        //TODO related http request to update the placard
        UIUtils.successful(message);
        guide();
        processInput();
    }
}
