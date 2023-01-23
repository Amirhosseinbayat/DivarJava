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
                getNewCopy().setPriceInRials(price);
                return trySavePlacard();
            } catch (NumberFormatException ex) {
                UIUtils.danger("Price must be a decimal number. try again!");
                return false;
            }
        }
    };
    protected InputHandler cityHandler = new BackSupportedInputHandler() {
        @Override
        public boolean handleValidInput(String city) {
            city = city.isBlank() ? requiredPrompt(user.getCity()) : city;
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
            phoneNumber = phoneNumber.equals("") ? requiredPrompt(user.getPhoneNumber()) : phoneNumber;
            getNewCopy().setPhoneNumber(phoneNumber);
            return trySavePlacard();
        }
    };
    protected InputHandler imageAddHandler = new BackSupportedInputHandler() {
        @Override
        public boolean handleValidInput(String input) {
            if (input.equalsIgnoreCase("done")) return true;
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
            originalPlacard = response.getResponseBody();
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
        promptInput("Enter it's price in rials: ", priceHandler);
    }

    protected void processCity() {
        UIUtils.primary("City related to placard: ");
        if (!user.getCity().isBlank()) {
            UIUtils.secondary(user.getCity()+" (press Enter to continue with your current city or type desired city: )");
        }
        promptInput(cityHandler);

    }

    protected void processAddress() {
        UIUtils.primary("Address related to placard: ");
        promptInput(addressHandler);
    }

    protected void processPhoneNumber() {
        UIUtils.primary("Owner's phone number: ");
        if (!user.getPhoneNumber().isBlank()) {
            UIUtils.secondary(user.getPhoneNumber()+" (press Enter to continue with your phone number or type desired phone number: )");
        }
        promptInput(phoneHandler);
    }

    protected void processAddingImageUrls() {
        System.out.println("You can attach some images to your placard.");
        UIUtils.primary("Enter image urls one by one, send 'done' when you are done.");
        promptInput(imageAddHandler);

    }
}
