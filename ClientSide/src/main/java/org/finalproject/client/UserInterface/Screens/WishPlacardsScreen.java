package org.finalproject.client.UserInterface.Screens;

import org.finalproject.DataObject.SalePlacard;
import org.finalproject.client.ClientConfiguration;
import org.finalproject.client.Http.Request;
import org.finalproject.client.Http.RequestException;
import org.finalproject.client.Http.Response;
import org.finalproject.client.UserInterface.Navigation;
import org.finalproject.client.UserInterface.UIScreen;
import org.finalproject.client.UserInterface.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class WishPlacardsScreen extends UIScreen {

    List<SalePlacard> fetchPlacards() {
        List<SalePlacard> placards;
        try {
            Response response = ClientConfiguration.getInstance().getRequestManager()
                    .sendRequest(new Request("GET", "placard/wishlist"));
            placards = response.getResponseBody();
            return placards;
        } catch (RequestException e) {
            e.printDetails();
            return new ArrayList<>();
        }
    }

    @Override
    public void startScreen() {
        UIUtils.header("My Wish Placards Page");
        final List<SalePlacard> placards = fetchPlacards();
        int i = 1;
        for (SalePlacard placard : placards) {
            UIUtils.placardTemplate(i++, placard, true);
        }

        UIUtils.secondary("Select a placard to view/remove. (type 'back' to go back to the home menu)");

        String input = requiredPrompt(scanner.nextLine());
        if (input.equals("back")) {
            Navigation.popBackStack();
            return;
        }
        try {
            int index = Integer.parseInt(input)-1;
            Navigation.navigateTo(new PlacardDetailsScreen(placards.get(index)));
        } catch (Exception ex) {
            System.out.println(input+" is not a meaningful command in this context.");
        }
    }
}
