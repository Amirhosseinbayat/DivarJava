package org.finalproject.client.UserInterface;

import org.finalproject.DataObject.SalePlacard;

import java.util.Scanner;

public class PlacardScreen extends UIScreen{
    SalePlacard placard;

    public PlacardScreen(Scanner scanner, SalePlacard placard) {
        super(scanner);
        this.placard = placard;
    }

    @Override
    void printGuideMessage() {
        UIUtils.header("Placard Details");
        UIUtils.placardTemplate(0, placard, false);
    }

    @Override
    void processInput() {
        String line = scanner.nextLine();
    }
}
