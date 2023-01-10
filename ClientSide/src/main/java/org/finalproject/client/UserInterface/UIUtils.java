package org.finalproject.client.UserInterface;

import org.finalproject.DataObject.SalePlacard;

public class UIUtils {
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void successful(String text) {
        System.out.println(ANSICodes.GREEN_BOLD+text+ANSICodes.RESET);
    }

    public static void header(String text){
        System.out.println(ANSICodes.PURPLE_BOLD+"--- "+text+" ---"+ANSICodes.RESET);
    }
    public static void options(String ...options){
        for(int i = 0; i < options.length; i++){
            System.out.println(ANSICodes.PURPLE+(i+1)+". "+options[i]+ANSICodes.RESET);
        }
    }

    public static void secondary(String text){
        System.out.println(ANSICodes.PURPLE+text+ANSICodes.RESET);
    }

    public static void primary(String text){
        System.out.println(ANSICodes.PURPLE_BOLD+text+ANSICodes.RESET);
    }

    public static void warning(String text){
        System.out.println(ANSICodes.YELLOW_BOLD+text+ANSICodes.RESET);
    }

    public static void danger(String text){
        System.out.println(ANSICodes.RED_BOLD+text+ANSICodes.RESET);
    }

    public static void form(String key, String value){
        System.out.println(ANSICodes.BLUE_BOLD+key+ANSICodes.YELLOW+value+ANSICodes.RESET);
    }

    public static void hr(){
        System.out.println(ANSICodes.RED_BOLD+"||||||||||||||||||||||||||||||||||"+ANSICodes.RESET);
    }

    public static void placardTemplate(int index, SalePlacard placard) {
        System.out.println(ANSICodes.RED_BOLD+"=====================================");
        if (index != 0)
            form("#: ", Integer.toString(index));
        form("Images: ", placard.getFirstImageUrl());
        form("Title: ", placard.getTitle());
        form("Description: ", placard.getShortenedDescription());
        form("City: ", placard.getCity());
        form("Price: ", placard.getPriceInRials()+" Rials");
        //TODO: remove contact information, move to placard details screen.
        if (placard.getPhoneNumber() != null) {
            form("Contact: ", placard.getPhoneNumber());
        }
    }
}
