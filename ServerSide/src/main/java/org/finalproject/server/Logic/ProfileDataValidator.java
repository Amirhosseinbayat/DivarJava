package org.finalproject.server.Logic;

import org.finalproject.DataObject.User;
import org.finalproject.server.Database.QueryConstraints;
import org.finalproject.server.ServerConfiguration;

import java.io.IOException;
import java.util.Objects;

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
        if (emailAddress != null && !emailAddress.isBlank()) {
            //the regex used here is a simple one, which does not match some rarely used but valid email addresses.
            if (!emailAddress.matches(
                    "^[a-zA-Z0-9.]"+ //first part, letters, numbers and dot. ^ represents start of the string.
                            "+@[a-zA-Z]"+ // @Gmail etc. part. can contain letters only.
                            "+\\.[a-zA-Z]+$")) { // .com etc part. $ represents end of the string.
                return "Your email address seems incorrect. sample email: byt.amir3@gmail.com";
            }
        } else return "Email address can not be blank.";

        User userWithEmail = null;
        try {
            userWithEmail = ServerConfiguration.getInstance().getDataBase().findOne(new QueryConstraints<User>() {
                @Override
                public boolean test(User object) {
                    return Objects.equals(object.getEmailAddress(), user.getEmailAddress());
                }

                @Override
                public int compare(User o1, User o2) {
                    return 0;
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (userWithEmail != null) return "This email address is already in use.";
        return null;
    }

    public static String validateUserProfile(User user) {

        String profileResult = checkProfileImage(user);
        if (profileResult != null) return profileResult;

        String emailResult = checkEmailAddress(user);
        if (emailResult != null) return emailResult;

        String first = SimpleFieldValidator.validateSimpleField(user.getFirstName(),"firstName",2);
        String last = SimpleFieldValidator.validateSimpleField(user.getLastName(),"lastName",3);

        if (first!=null)return first;
        if (last!=null)return last;
        return checkPhoneNumber(user);
    }




}
