package org.finalproject.server.Logic;

public class SimpleFieldValidator {


    public static String validateSimpleField(String str,String name){
        return validateSimpleField(str,name,4);
    }

    public static String validateSimpleField(String str,String name,int minLength){
        if (str == null)return null;
        if (str.isBlank()){
            return name +" can not be empty.";
        }
        if (str.length()<minLength){
            return name + " can not be less than " + minLength+" characters.";
        }
        return null;
    }


}
