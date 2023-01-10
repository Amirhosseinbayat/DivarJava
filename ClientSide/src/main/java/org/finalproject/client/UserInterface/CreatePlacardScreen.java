package org.finalproject.client.UserInterface;

import org.finalproject.DataObject.SalePlacard;
import org.finalproject.client.Http.RequestException;


import java.util.InputMismatchException;
import java.util.Scanner;

public class CreatePlacardScreen extends UIScreen{
    private SalePlacard placard;

    public CreatePlacardScreen(Scanner scanner) {
        super(scanner);
    }


    @Override
    void printGuideMessage() {
        UIUtils.header("Create Placard");
    }

    @Override
    void processInput() {
        UIUtils.primary("Placard title: ");
        String title = scanner.nextLine();
        UIUtils.primary("Description about placard: ");
        String description = scanner.nextLine();
        long price;
        try{
            UIUtils.primary("Price in rials: ");
            price = scanner.nextLong();
        }catch(InputMismatchException ex){
            price = -1;
        }finally {
            scanner.nextLine();
        }
        UIUtils.primary("City related to placard: ");
        String city = scanner.nextLine();
        UIUtils.primary("Address related to placard: ");
        String address = scanner.nextLine();
        UIUtils.primary("Owner's phone number: ");
        String phoneNumber = scanner.nextLine();
        UIUtils.primary("Images url(ex: img1.jpg, img2.jpg): ");
        String imagesUrlRaw = scanner.nextLine();

        placard = new SalePlacard(title);
        placard.setDescription(description);
        placard.setPriceInRials(price);
        placard.setCity(city);
        placard.setAddress(address);
        placard.setPhoneNumber(phoneNumber);
        for(String imageUrl : imagesUrlRaw.split(","))
            placard.addImageUrl(imageUrl.trim());

        trySavePlacard();
    }

    void trySavePlacard(){
        try{
            //TODO send placard object to server

            UIUtils.successful("Your placard published successfully.(press Enter to continue)");


            throw new RequestException(1000, "message");
        }catch(RequestException ex){
            restartWithError(ex.getMessage());
        }
    }
}
