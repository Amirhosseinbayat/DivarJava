package org.finalproject.client.UserInterface;

import org.finalproject.DataObject.SalePlacard;
import org.finalproject.client.ImprovedUserInterface.BackSupportedInputHandler;
import org.finalproject.client.ImprovedUserInterface.Navigation;

import java.util.ArrayList;
import java.util.List;

public class MyPlacardsScreen extends PlacardsScreen{

    public MyPlacardsScreen() {
        placardList = fetchMyPlacards();
    }

    @Override
    public void startScreen() {
        UIUtils.header("My Placards Page");
        //TODO remove this assignment
        placardList = getTestPlacards();
        printPlacards();
        UIUtils.secondary("Select placard to see more details. type 'back' to go back.");
        promptInput(new BackSupportedInputHandler() {
            @Override
            public boolean handleValidInput(String input) {
                try {
                    int index = Integer.parseInt(input)-1;
                    Navigation.navigateTo(new PlacardScreen(placardList.get(index)));
                    return true;
                } catch (Exception ex) {
                    System.out.println(input+" is not a meaningful command in this context.");
                    return false;
                }
            }
        });
    }

    List<SalePlacard> fetchMyPlacards(){
        //TODO fetch the user placards
        return new ArrayList<>();
    }
}
