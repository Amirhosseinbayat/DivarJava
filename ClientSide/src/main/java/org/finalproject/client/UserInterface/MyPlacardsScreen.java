package org.finalproject.client.UserInterface;

import org.finalproject.DataObject.SalePlacard;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MyPlacardsScreen extends PlacardsScreen{

    public MyPlacardsScreen(Scanner scanner) {
        super(scanner);
        placardList = fetchMyPlacards();
    }

    @Override
    void printGuideMessage() {
        UIUtils.header("My Placards Page");
        //TODO remove this assignment
        placardList = getTestPlacards();
        printPlacards();
        UIUtils.secondary("Select placard to see more details. type 'back' to go back.");
    }

    void processInput(){
        String input = notEmptyInput(scanner.nextLine()) ;
        if(input.equals("back")){
            new HomeMenuScreen(scanner).guide().process();
            return;
        }
        try{
            int index = Integer.parseInt(input) - 1;
            new PlacardScreen(scanner, placardList.get(index), this).guide().process();
            return;
        }catch(Exception ex){
            restartWithError(input+" is not a meaningful command in this context.");
        }
    }

    List<SalePlacard> fetchMyPlacards(){
        //TODO fetch the user placards
        return new ArrayList<>();
    }
}
