package org.finalproject.client.UserInterface;

import org.finalproject.DataObject.SalePlacard;
import org.finalproject.DataObject.User;
import org.finalproject.client.ClientConfiguration;
import org.finalproject.client.Http.Request;
import org.finalproject.client.Http.RequestException;
import org.finalproject.client.ImprovedUserInterface.Navigation;

public class CreatePlacardScreen extends UIScreen {
    private SalePlacard placard;
    private User user;

    public CreatePlacardScreen(User user) {
        this.user = user;
    }


    @Override
    public void startScreen() {
        UIUtils.header("Create Placard");
        processTitle();
        processDescription();
        processImagesUrl();
        processPrice();
        processCity();
        processAddress();
        processPhoneNumber();
        trySavePlacard();
    }

    void trySavePlacard(){
        try {
            ClientConfiguration.getInstance().getRequestManager().sendRequest(
                    new Request("POST", "placard/new")
                            .setBody(placard));

            UIUtils.successful("successfully published your placard!(press Enter to continue)");
            scanner.nextLine();

            Navigation.popBackStack();
        }catch(RequestException ex){
            ex.printDetails();
        }
    }

    private void processTitle(){
        UIUtils.primary("Enter a short title for your placard:");
        String title = requiredPrompt(scanner.nextLine());
        placard = new SalePlacard(title);
    }

    private void processDescription(){
        UIUtils.primary("Write some description about what you are selling: ");
        String description = requiredPrompt(scanner.nextLine());
        placard.setDescription(description);
    }

    private void processPrice(){
        long price;
        while(true){
            try{
                UIUtils.primary("Enter it's price in rials: ");
                String input = requiredPrompt(scanner.nextLine());
                price = Long.parseLong(input);
                break;
            }catch(NumberFormatException ex){
                UIUtils.danger("Price must be a decimal number.");
            }
        }
        placard.setPriceInRials(price);
    }

    private void processCity(){
        UIUtils.primary("City related to placard: ");
        if(!user.getCity().equals("")){
            UIUtils.secondary(user.getCity() + " (press Enter to continue with your current city or type desired city: )");

        }
        String city = scanner.nextLine();
        city = city.equals("") ? requiredPrompt(user.getCity()) : city;
        placard.setCity(city);
    }

    private void processAddress(){
        UIUtils.primary("Address related to placard: ");
        String address = requiredPrompt(scanner.nextLine());
        placard.setAddress(address);
    }

    private void processPhoneNumber(){
        UIUtils.primary("Owner's phone number: ");
        if(!user.getPhoneNumber().equals("")){
            UIUtils.secondary(user.getPhoneNumber() + " (press Enter to continue with your phone number or type desired phone number: )");
        }
        String phoneNumber = scanner.nextLine();
        phoneNumber = phoneNumber.equals("") ? requiredPrompt(user.getPhoneNumber()) : phoneNumber;
        placard.setPhoneNumber(phoneNumber);
    }

    private void processImagesUrl(){
        UIUtils.primary("Enter image urls splited by comma. (ex: img1.jpg, img2.jpg): ");
        String imagesUrlRaw = requiredPrompt(scanner.nextLine());
        for(String imageUrl : imagesUrlRaw.split(","))
            placard.addImageUrl(imageUrl.trim());
    }
}
