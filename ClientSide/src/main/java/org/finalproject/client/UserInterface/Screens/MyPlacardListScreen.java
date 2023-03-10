package org.finalproject.client.UserInterface.Screens;

import org.finalproject.DataObject.PlacardQuery;
import org.finalproject.DataObject.SalePlacard;
import org.finalproject.client.ClientConfiguration;
import org.finalproject.client.Http.Request;
import org.finalproject.client.Http.RequestException;
import org.finalproject.client.Http.Response;
import org.finalproject.client.UserInterface.BackSupportedInputHandler;
import org.finalproject.client.UserInterface.Navigation;
import org.finalproject.client.UserInterface.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class MyPlacardListScreen extends PlacardListScreen {

    public MyPlacardListScreen() {

    }

    @Override
    public void startScreen() {
        UIUtils.header("My Placards Page");
        printPlacards();
        if (placardList.isEmpty()) {
            UIUtils.warning("you haven't created any placards yet.");
            UIUtils.primary("send 'back' to go back.");
        } else {
            UIUtils.secondary("Select placard to see more details. type 'back' to go back.");
        }
        promptInput(new BackSupportedInputHandler() {
            @Override
            public boolean handleValidInput(String input) {
                try {
                    int index = Integer.parseInt(input)-1;
                    Navigation.navigateTo(new PlacardDetailsScreen(placardList.get(index)));
                    return true;
                } catch (Exception ex) {
                    System.out.println(input+" is not a meaningful command in this context.");
                    return false;
                }
            }
        });
    }

    @Override
    List<SalePlacard> getPlacards(PlacardQuery query) {
        try {
            Response response = ClientConfiguration.getInstance().getRequestManager()
                    .sendRequest(new Request("GET", "placard/mine"));
            placardList = response.getResponseBody();
            return placardList;
        } catch (RequestException e) {
            e.printDetails();
        }
        return new ArrayList<>();
    }

    @Override
    public void trimMemory() {
        super.trimMemory(); //implemented at PlacardListScreen.
    }
}
