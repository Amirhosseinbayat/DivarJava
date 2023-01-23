package org.finalproject.client.UserInterface;

import org.finalproject.DataObject.SalePlacard;

import java.util.Calendar;

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
    public static void usual(String text){
        System.out.println(ANSICodes.RESET+text);
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

    public static void placardTemplate(int index, SalePlacard placard, Boolean hideDetails){
        System.out.println(ANSICodes.RED_BOLD+"=====================================");
        if (index != 0) {
            String promotion = placard.isStillPromoted() ? ANSICodes.GREEN_BOLD
                    +"    PROMOTED"+ANSICodes.RESET : "";
            Calendar calendar = Calendar.getInstance();

            calendar.setTimeInMillis(placard.getCreatedAt());
            String dateCreated = calendarToString(calendar);

            calendar.setTimeInMillis(placard.getUpdatedAt());
            String dateUpdated = calendarToString(calendar);

            form("#: ", index+promotion+"      created: "+dateCreated+" updated: "+dateUpdated);
        }
        if (hideDetails) {
            form("First Image: ", placard.getFirstImageUrl());
        } else {
            form("Images: ", placard.getImageUrlSet().toString());
        }
        form("Title: ", placard.getTitle());
        if (hideDetails) {
            form("Description: ", placard.getDescription().substring(0, Math.min(placard.getDescription().length(), 60)));
        } else {
            form("Full Description: ", placard.getDescription());
        }
        form("City: ", placard.getCity());
        if (!hideDetails) {
            form("Address: ", placard.getAddress());
        }
        form("Price: ", placard.getPriceInRials()+" Rials");
        if (!hideDetails) {
            form("Contact: ", placard.getPhoneNumber());
        }
    }

    private static String calendarToString(Calendar calendar) {
        return calendar.get(Calendar.YEAR)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.DAY_OF_MONTH)
                +" "+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE);
    }
}
