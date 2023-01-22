package org.finalproject.server.Logic;

import org.finalproject.DataObject.User;

public class ProfileDataValidator {


    private static String checkProfileImage(User user) {
        String profileImage = user.getProfilePictureUrl();
        return ImageUrlValidator.validateImageUrl(profileImage);
    }

    private static String checkPhoneNumber(User user) {
        String phoneNumber = user.getPhoneNumber();
        if (phoneNumber != null && !phoneNumber.isEmpty()
                && !phoneNumber.matches("0[0-9]{10}")) { //contains 10 numbers after the first zero.
            return "Your phone number is not valid. sample phone number: 09123456789";
        }
        return null;
    }

    private static String checkEmailAddress(User user) {
        String emailAddress = user.getEmailAddress();
        if (emailAddress != null && !emailAddress.isEmpty()) {
            //the regex used here is a simple one, which does not match some rarely used but valid email addresses.
            if (!emailAddress.matches(
                    "^[a-zA-Z0-9.]"+ //first part, letters, numbers and dot. ^ represents start of string.
                            "+@[a-zA-Z]"+ // @Gmail etc. part. can contain letters only.
                            "+\\.[a-zA-Z]+$")) { // .com etc part. $ represents end of the string.
                return "Your email address seems incorrect. sample email: byt.amir3@gmail.com";
            }
        }
        return null;
    }

    public static String validateUserProfile(User user) {

        String profileResult = checkProfileImage(user);
        if (profileResult != null) return profileResult;

        String emailResult = checkEmailAddress(user);
        if (emailResult != null) return emailResult;

        return checkPhoneNumber(user);
    }


}
