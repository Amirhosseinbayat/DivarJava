package org.finalproject.client.UserInterface.Screens;

import org.finalproject.DataObject.SalePlacard;
import org.finalproject.DataObject.User;
import org.finalproject.client.ClientConfiguration;
import org.finalproject.client.Http.Request;
import org.finalproject.client.Http.RequestException;
import org.finalproject.client.Http.Response;
import org.finalproject.client.UserInterface.*;

public class PlacardCreateScreen extends UIScreen {
    protected User user;
    protected SalePlacard originalPlacard = new SalePlacard("blank");
    protected SalePlacard editedPlacard;
    protected InputHandler titleHandler = new BackSupportedInputHandler() {
        @Override
        public boolean handleValidInput(String title) {
            getNewCopy().setTitle(title);
            return trySavePlacard();
        }
    };
    protected InputHandler descriptionHandler = new BackSupportedInputHandler() {
        @Override
        public boolean handleValidInput(String description) {
            getNewCopy().setDescription(description);
            return trySavePlacard();

        }
    };
    protected InputHandler priceHandler = new BackSupportedInputHandler() {
        @Override
        public boolean handleValidInput(String input) {
            try {
                long price = Long.parseLong(input);
                if (price<1) {
                    UIUtils.danger("price can not be less than 1 rial.");
                    return false;
                }
                getNewCopy().setPriceInRials(price);
                return trySavePlacard();
            } catch (Exception e) { //numberFormat or too long input.
                UIUtils.danger("Price must be an integer number. try again!");
                return false;
            }
        }
    };
    protected InputHandler cityHandler = new BackSupportedInputHandler() {
        @Override
        public boolean handleValidInput(String city) {
            if (city.isBlank()) {
                if (user.getCity() != null && !user.getCity().isBlank()) {
                    city = user.getCity();
                } else {
                    UIUtils.danger("city can not be blank. try again");
                    return false;
                }
            }
            getNewCopy().setCity(city);
            return trySavePlacard();
        }
    };
    protected InputHandler addressHandler = new BackSupportedInputHandler() {
        @Override
        public boolean handleValidInput(String address) {
            getNewCopy().setAddress(address);
            return trySavePlacard();
        }
    };
    protected InputHandler phoneHandler = new BackSupportedInputHandler() {
        @Override
        public boolean handleValidInput(String phoneNumber) {
            if (phoneNumber.isBlank()) {
                if (user.getPhoneNumber() != null && !user.getPhoneNumber().isBlank()) {
                    phoneNumber = user.getPhoneNumber();
                } else {
                    UIUtils.danger("phone number can not be blank. try again");
                    return false;
                }
            }
            getNewCopy().setPhoneNumber(phoneNumber);
            return trySavePlacard();
        }
    };
    protected InputHandler imageAddHandler = new BackSupportedInputHandler() {
        @Override
        public boolean handleValidInput(String input) {
            if (input.equalsIgnoreCase("done")) return true;
            System.out.println("image url can not be empty.");
            if (input.isBlank()) return false;
            getNewCopy().addImageUrl(input);
            trySavePlacard();
            UIUtils.secondary("add another image url, or send 'done' to finish adding images.");
            return false;//continue getting input from user.
        }
    };

    @Override
    public void trimMemory() {
        addressHandler = null;
        cityHandler = null;
        phoneHandler = null;
        imageAddHandler = null;
        priceHandler = null;
        descriptionHandler = null;
        titleHandler = null;
        user = null;
        editedPlacard = null;
        originalPlacard = null;
    }

    public PlacardCreateScreen(User user) {
        this.user = user;
    }

    protected SalePlacard getNewCopy() {
        editedPlacard = originalPlacard.clone();
        return editedPlacard;
    }

    @Override
    public void startScreen() {
        UIUtils.header("Create Placard");
        processTitle();
        processDescription();
        processAddingImageUrls();
        processPrice();
        processCity();
        processAddress();
        processPhoneNumber();
        UIUtils.successful("placard saved successfully! press enter to continue");
        scanner.nextLine();
        Navigation.popBackStack();
    }

    protected boolean trySavePlacard() {
        try {
            Response response = ClientConfiguration.getInstance().getRequestManager().sendRequest(
                    new Request("POST", "placard/")
                            .setBody(editedPlacard));
            SalePlacard saved = response.getResponseBody();
            originalPlacard.copyData(saved);
            UIUtils.successful("successfully saved.");
            return true;
        } catch (RequestException e) {
            UIUtils.danger("unable to save: "+e.getMessage());
            return false;
        }
    }

    protected void processTitle() {
        promptInput("Enter a short title for your placard:", titleHandler);
    }

    protected void processDescription() {
        promptInput("Write some description about what you are selling: "
                , descriptionHandler);
    }

    protected void processPrice() {
        promptInput("Enter its price in rials: ", priceHandler);
    }

    protected void processCity() {
        UIUtils.primary("City related to placard: ");
        if (user.getCity() != null && !user.getCity().isBlank()) {
            UIUtils.secondary(user.getCity()+" (press Enter to continue with your current city or type desired city: )");
        } else
            UIUtils.warning("did you know? if you had specified your city in your profile, we would suggest it here.");
        promptInput(cityHandler);

    }

    protected void processAddress() {
        UIUtils.primary("Address related to placard: ");
        promptInput(addressHandler);
    }

    protected void processPhoneNumber() {
        UIUtils.primary("Owner's phone number: ");
        if (user.getPhoneNumber() != null && !user.getPhoneNumber().isBlank()) {
            UIUtils.secondary(user.getPhoneNumber()+" (press Enter to continue with your phone number or type desired phone number: )");
        } else
            UIUtils.warning("did you know? if you had specified your phone in your profile, we would suggest it here.");

        promptInput(phoneHandler);
    }

    protected void processAddingImageUrls() {
        System.out.println("You can attach some images to your placard.");
        UIUtils.primary("Enter image urls one by one, send 'done' when you are done.");
        promptInput(imageAddHandler);

    }
}
