package org.finalproject.server.Logic;

public class PasswordValidator implements BusinessLogic {
    public static boolean isTheNumberAPowerOfTwo(int number) {
        /*
         * a binary representation of a power of two will be like 10000... , and if we subtract 1 from it, it will
         * become 01111... , if the AND operator is applied on these two, we will get 00000... :
         *   01111
         * & 10000
         * ----------
         *   00000
         * Quick reminder: AND operator returns 0 for 1,0, and 1 for 0,0 or 1,1.
         * We use & to perform an AND operation on two numbers in java.
         * */
        int resultOfANDOperator = number & (number-1);
        return (resultOfANDOperator == 0);
    }

    public static String sequence = "0123456789";

    public static boolean areDigitsASequence(String number) {
        char[] chars = number.toCharArray();
        int previousIndex = -1;
        for (char ch : chars) {
            int currentIndex = sequence.indexOf(ch);
            if (previousIndex != -1 && currentIndex != previousIndex+1) {
                return false;
            }
            previousIndex = currentIndex;
        }
        return true;
    }

    public String validatePassword(String password) {
        if (password == null || password.isEmpty()) return "Password can not be empty.";
        if (password.length()<8) return "Password should contain at least 8 characters.";

        if (!password.matches("^[a-z0-9]+$")) {
            return "password can only contain lowercase english letters and numbers.";
        }
        if (password.matches("^[a-z]+$")) return "password has only letters. must contain numbers also.";
        if (password.matches("^[0-9]+$")) return "password has only numbers. must contain letters also.";
        boolean containsTwoA = password.matches(
                ".*"//zero or more chars at the begining,
                        +"a"+ //an A
                        ".*"+ // zero or more chars in between
                        "a"+ //another a
                        ".*"); //maybe more chars at the end.

        boolean containsANumberInPowerOfTwo = false;
        String[] numbers = password.split("[a-z]+"); //for example, byt876amir3r4 becomes { , 876 , , 3 , , 4}
        // basically numbers remain as is and letters get converted to empty string ""
        for (String num : numbers) {
            if (num.isEmpty()) continue; //it was a letter stroke at original password, and has become empty here.
            int n = Integer.parseInt(num);
            if (areDigitsASequence(num)) return "You have used "+num+" in your password, whose digits are a sequence.";
            if (isTheNumberAPowerOfTwo(n)) {
                containsANumberInPowerOfTwo = true;
            }
        }
        if (!containsTwoA && !containsANumberInPowerOfTwo) {
            return "This password has neither two 'a' chars, nor a number in power of two.";
        }
        return null;
    }
}
