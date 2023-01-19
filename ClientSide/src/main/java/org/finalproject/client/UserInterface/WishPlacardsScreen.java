package org.finalproject.client.UserInterface;

import org.finalproject.DataObject.SalePlacard;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WishPlacardsScreen extends UIScreen{
    private List<SalePlacard> placards;
    public WishPlacardsScreen(Scanner scanner) {
        super(scanner);
    }

    @Override
    void printGuideMessage() {
        UIUtils.header("My Wish Placards Page");
        fetchPlacards();
        int i = 1;
        for (SalePlacard placard : placards) {
            UIUtils.placardTemplate(i++, placard, true);
        }

        UIUtils.secondary("You can select a placard for more details. (type 'back' to back to the home menu)");
    }

    void fetchPlacards(){
        //TODO related http request to fetch wish list of the user
        placards = new ArrayList<>();
    }

    @Override
    void processInput(){
        String input = notEmptyInput(scanner.nextLine()) ;
        if(input.equals("back")){
            new HomeMenuScreen(scanner).guide().process();
            return;
        }
        try{
            int index = Integer.parseInt(input) - 1;
            new PlacardScreen(scanner, placards.get(index), this).guide().process();
            return;
        }catch(Exception ex){
            restartWithError(input+" is not a meaningful command in this context.");
        }
    }

}
