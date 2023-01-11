package org.finalproject.client.UserInterface;

import org.finalproject.DataObject.SalePlacard;

import java.util.List;
import java.util.Scanner;

public class MyPlacardsScreen extends PlacardsScreen{

    public MyPlacardsScreen(Scanner scanner) {
        super(scanner);
    }

    @Override
    void printGuideMessage() {
        UIUtils.header("My Placards Page");
        List<SalePlacard> placardList = getTestPlacards();
        int i = 1;
        for (SalePlacard placard : placardList) {
            UIUtils.placardTemplate(i++, placard, true);
        }
    }
}
