package org.finalproject.client.UserInterface.Screens;

import org.finalproject.DataObject.SalePlacard;
import org.finalproject.client.UserInterface.Navigation;
import org.finalproject.client.UserInterface.UIScreen;
import org.finalproject.client.UserInterface.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class WishPlacardsScreen extends UIScreen {
    private List<SalePlacard> placards;

    void fetchPlacards() {
        //TODO related http request to fetch wish list of the user
        placards = new ArrayList<>();
    }

    @Override
    public void startScreen() {
        UIUtils.header("My Wish Placards Page");
        fetchPlacards();
        int i = 1;
        for (SalePlacard placard : placards) {
            UIUtils.placardTemplate(i++, placard, true);
        }

        UIUtils.secondary("You can select a placard for more details. (type 'back' to back to the home menu)");

        String input = requiredPrompt(scanner.nextLine());
        if (input.equals("back")) {
            Navigation.popBackStack();
            return;
        }
        try {
            int index = Integer.parseInt(input)-1;
            Navigation.navigateTo(new PlacardDetailsScreen(placards.get(index)));
            return;
        } catch (Exception ex) {
            System.out.println(input+" is not a meaningful command in this context.");
        }
    }
}
