package org.finalproject.client.UserInterface;

import org.finalproject.DataObject.SalePlacard;
import org.finalproject.client.ClientConfiguration;
import org.finalproject.client.Http.Request;
import org.finalproject.client.Http.RequestException;
import org.finalproject.client.Http.Response;

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
        UIUtils.primary("Enter a short title for your placard:");
        String title = scanner.nextLine();
        UIUtils.primary("Write some description about what you are selling: ");
        String description = scanner.nextLine();
        long price;
        while(true){
            try{
                UIUtils.primary("Enter it's price in rials: ");
                price = scanner.nextLong();
                break;
            }catch(InputMismatchException ex){
                UIUtils.danger("Price must be a decimal number.");
            }finally {
                scanner.nextLine();
            }
        }
        UIUtils.primary("City related to placard: ");
        String city = scanner.nextLine();
        UIUtils.primary("Address related to placard: ");
        String address = scanner.nextLine();
        UIUtils.primary("Owner's phone number: ");
        String phoneNumber = scanner.nextLine();
        UIUtils.primary("Enter image urls splitted by comma. (ex: img1.jpg, img2.jpg): ");
        String imagesUrlRaw = scanner.nextLine();

        placard = new SalePlacard(title);
        placard.setDescription(description);
        placard.setPriceInRials(price);
        placard.setCity(city);
        placard.setAddress(address);
        placard.setPhoneNumber(phoneNumber);
        // TODO placard.setUserId
        for(String imageUrl : imagesUrlRaw.split(","))
            placard.addImageUrl(imageUrl.trim());

        trySavePlacard();
    }

    void trySavePlacard(){
        try {
            Response response = ClientConfiguration.getInstance().getRequestManager().sendRequest(
                    new Request("POST", "placard/new")
                            .setBody(placard));
            UIUtils.successful("successfully published your placard!(press Enter to continue)");
            scanner.nextLine();
            new HomeMenuScreen(scanner).guide().process();
        }catch(RequestException ex){
            restartWithError(ex.getMessage());
        }
    }
}
