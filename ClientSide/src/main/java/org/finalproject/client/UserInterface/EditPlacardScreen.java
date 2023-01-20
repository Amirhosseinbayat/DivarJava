package org.finalproject.client.UserInterface;

import org.finalproject.DataObject.SalePlacard;
import org.finalproject.client.ClientConfiguration;
import org.finalproject.client.Http.Request;
import org.finalproject.client.Http.RequestException;
import org.finalproject.client.ImprovedUserInterface.BackSupportedInputHandler;

public class EditPlacardScreen extends PlacardScreen {
    public EditPlacardScreen(SalePlacard placard) {
        super(placard);
    }

    @Override
    public void startScreen() {
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

        String input = scanner.nextLine();
        promptInput(new BackSupportedInputHandler("8") {
            @Override
            public boolean handleValidInput(String input) {
                switch (input) {
                    case "1" -> processTitleChange();
                    case "2" -> processImagesChange();
                    case "3" -> processDescriptionChange();
                    case "4" -> processCityChange();
                    case "5" -> processPriceChange();
                    case "6" -> processPhoneNumberChange();
                    case "7" -> processDeletion();
                    default -> {
                        return false;
                    }
                }
                return true;
            }
        });
    }

    private void processTitleChange() {
        String prevTitle = placard.getTitle();
        promptInput("Enter new title (press Enter to go back): ", new BackSupportedInputHandler() {
            @Override
            public boolean handleValidInput(String input) {
                placard.setTitle(input);
                try {
                    trySavePlacardObject("Title changed successfully.");
                    return true;
                } catch (RequestException e) {
                    placard.setTitle(prevTitle);
                    UIUtils.danger("failed to update title: "+e.getMessage());
                    System.out.println("Try again, Enter a valid title. \npress enter to go back.");
                    return true;
                }
            }
        });

    }


    private void processImagesChange(){
        //TODO this method has to be implemented again with the new ui structure.
    }

    private void processDescriptionChange() {
        String prevDescription = placard.getDescription();
        promptInput("Type new description for your placard (press Enter to go back): "
                , new BackSupportedInputHandler() {
                    @Override
                    public boolean handleValidInput(String input) {
                        placard.setDescription(input);
                        try {
                            trySavePlacardObject("Description updated successfully.");
                            return true;
                        } catch (RequestException e) {
                            placard.setDescription(prevDescription);
                            UIUtils.danger("failed to update description: "+e.getMessage());
                            System.out.println("Try again, Enter a valid description. \npress enter to go back.");
                            return true;
                        }
                    }
                });
    }

    private void processCityChange() {
        String prevCity = placard.getCity();
        promptInput("Enter new City for your placard (press Enter to go back): "
                , new BackSupportedInputHandler() {
                    @Override
                    public boolean handleValidInput(String input) {
                        placard.setCity(input);
                        try {
                            trySavePlacardObject("Related city updated successfully.");
                            return true;
                        } catch (RequestException e) {
                            placard.setCity(prevCity);
                            UIUtils.danger("failed to update related city: "+e.getMessage());
                            System.out.println("Try again, Enter a valid city. \npress enter to go back.");
                            return false;
                        }
                    }
                });
    }

    private void processPriceChange() {
        long prevPrice = placard.getPriceInRials();
        promptInput("Type new price in rials for your placard (press Enter to go back): ",
                new BackSupportedInputHandler() {
                    @Override
                    public boolean handleValidInput(String input) {
                        try {
                            long price = Long.parseLong(input);
                            placard.setPriceInRials(price);
                            trySavePlacardObject("Price of placard updated successfully.");
                            return true;
                        } catch (NumberFormatException ex) {
                            UIUtils.warning("Price must be a numeric value. try again");
                            return false;
                        } catch (RequestException e) {
                            placard.setPriceInRials(prevPrice);
                            UIUtils.danger("failed to update price: "+e.getMessage());
                            System.out.println("Try again, Enter a valid price in rials. \npress enter to go back.");
                            return false;
                        }
                    }
                });
    }

    private void processPhoneNumberChange() {
        String prevPhoneNumber = placard.getPhoneNumber();
        promptInput("Enter new phone number (press Enter to go back): ", new BackSupportedInputHandler() {
            @Override
            public boolean handleValidInput(String input) {
                placard.setPhoneNumber(input);
                try {
                    trySavePlacardObject("Phone number updated successfully.");
                    return true;
                } catch (RequestException e) {
                    placard.setPhoneNumber(prevPhoneNumber);
                    UIUtils.danger("failed to update phone number: "+e.getMessage());
                    System.out.println("Try again, Enter a valid phone number. \npress enter to go back.");
                    return false;
                }
            }
        });
    }

    private void processDeletion(){
        //TODO related http request to delete the placard
    }

    void trySavePlacardObject(String message) throws RequestException {
        ClientConfiguration.getInstance().getRequestManager().sendRequest(new Request(
                "POST", "placard/update"
        ).setBody(placard));
        UIUtils.successful(message);
    }
}
