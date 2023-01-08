package org.finalproject.client.UserInterface;

import org.finalproject.DataObject.SalePlacard;
import org.finalproject.client.ClientConfiguration;
import org.finalproject.client.Http.Request;
import org.finalproject.client.Http.RequestException;
import org.finalproject.client.Http.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlacardsScreen extends UIScreen {
    public PlacardsScreen(Scanner scanner) {
        super(scanner);
    }

    @Override
    void printGuideMessage() {
        System.out.println("getting the list of placards...");
        List<SalePlacard> placardList = getTestPlacards();
        for (SalePlacard placard : placardList) {
            String str = "-------------------------------\n";
            str += "title:  "+ANSI_BLUE+placard.getTitle()+ANSI_RESET+
                    "\ndescription:  "+
                    //ANSI_BLUE +
                    placard.getDescription()
                            .substring(0, Math.min(placard.getDescription().length(), 60))+"..."+
                    //ANSI_RESET +
                    "\ncity: "+
                    ANSI_GREEN+
                    placard.getCity()+
                    ANSI_RESET+
                    " price:"+
                    ANSI_GREEN+
                    placard.getPriceInRials()+"Rials"+
                    ANSI_RESET;
            System.out.println(str);
        }
    }

    List<SalePlacard> getTestPlacards() {
        List<SalePlacard> placardList = new ArrayList<>();
        for (int x = 0; x != 10; x++) {
            SalePlacard placard = new SalePlacard("dummy placard number "+x);
            placard.setPriceInRials(410000*(x+1));
            placard.setCity("Tehran");
            placard.setDescription("some kind of description is here. purchase this item!!!");
            placardList.add(placard);
        }
        return placardList;
    }

    List<SalePlacard> getPlacards() {
        try {
            Response response = ClientConfiguration.getInstance().getRequestManager()
                    .sendRequest(new Request("GET", "placards/list"));
            List<SalePlacard> placardList = response.getResponseBody();
            System.out.println("retrieved "+placardList.size()+" placards:");
            return placardList;
        } catch (RequestException e) {
            e.printDetails();
        }
        return new ArrayList<>();
    }

    @Override
    void processInput() {
        String input = scanner.nextLine();
    }
}
