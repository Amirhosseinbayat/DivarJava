package org.finalproject.client.UserInterface;

public class UIUtils {
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void successful(String text){
        System.out.println(Colors.GREEN_BOLD + text + Colors.RESET);
    }

    public static void header(String text){
        System.out.println(Colors.PURPLE_BOLD + "--- " + text + " ---" + Colors.RESET);
    }
    public static void options(String ...options){
        for(int i = 0; i < options.length; i++){
            System.out.println(Colors.PURPLE + (i + 1) + ". " + options[i] + Colors.RESET);
        }
    }

    public static void secondary(String text){
        System.out.println(Colors.PURPLE + text + Colors.RESET);
    }

    public static void primary(String text){
        System.out.println(Colors.PURPLE_BOLD + text + Colors.RESET);
    }

    public static void warning(String text){
        System.out.println(Colors.YELLOW_BOLD + text + Colors.RESET);
    }

    public static void danger(String text){
        System.out.println(Colors.RED_BOLD + text + Colors.RESET);
    }

    public static void form(String key, String value){
        System.out.println(Colors.BLUE_BOLD + key + Colors.YELLOW + value + Colors.RESET);
    }

    public static void hr(){
        System.out.println(Colors.RED_BOLD + "||||||||||||||||||||||||||||||||||" + Colors.RESET);
    }

    public static void placardTemplate(int index, String imagesUrl, String title, String description, String city, long price, String phoneNumber){
        System.out.println(Colors.RED_BOLD + "=====================================");
        if(index != 0)
            form("#: ", Integer.toString(index));
        form("Images: ", imagesUrl);
        form("Title: ", title);
        form("Description: ", description);
        form("City: ", city);
        form("Price: ", price + " Rials");
        if(phoneNumber != null){
            form("Contact: ", phoneNumber);
        }
    }
}
