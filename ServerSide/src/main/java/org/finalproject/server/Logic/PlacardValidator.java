package org.finalproject.server.Logic;

import org.finalproject.DataObject.SalePlacard;

import java.util.Objects;

public class PlacardValidator {

    private static String checkPhoneNumber(String phoneNumber) {
        if (phoneNumber != null && !phoneNumber.isEmpty()
                && !phoneNumber.matches("0[0-9]{10}")) { //contains 10 numbers after the first zero.
            return "This phone number is not valid. sample phone number: 09123456789";
        }
        return null;
    }

    public static String validate(SalePlacard salePlacard, SalePlacard original) {
        String result;
        if (original != null) {
            result = validateUpdates(salePlacard,original);
        } else {
            result = validateFullObject(salePlacard);
        }
        if (result!=null)return result;
        return validateSimpleFields(salePlacard);
    }

    private static String validateFullObject(SalePlacard salePlacard) {
        String result;
        for (String imageUrl : salePlacard.getImageUrlSet()) {
            result = ImageUrlValidator.validateImageUrl(imageUrl);
            if (result != null) {
                return result;
            }
        }
        result = checkPhoneNumber(salePlacard.getPhoneNumber());
        if (result!=null)return result;

        return null;
    }

    private static String validateSimpleFields(SalePlacard salePlacard) {
        String titleResult = SimpleFieldValidator.validateSimpleField(salePlacard.getTitle(),"title");
        if (titleResult != null) return titleResult;

        String descriptionResult = SimpleFieldValidator
                .validateSimpleField(salePlacard.getDescription(),"description");
        if (descriptionResult != null) return descriptionResult;

        String cityResult = SimpleFieldValidator.validateSimpleField(salePlacard.getCity(),"city name");
        if (cityResult != null) return cityResult;

        String addressResult = SimpleFieldValidator.validateSimpleField(salePlacard.getAddress(),"address");
        if (addressResult != null) return addressResult;
        return null;
    }

    private static String validateUpdates(SalePlacard salePlacard, SalePlacard original) {
        String result;
        for (String imageUrl : salePlacard.getImageUrlSet()) {
            if (original.getImageUrlSet().contains(imageUrl)) continue;
            result = ImageUrlValidator.validateImageUrl(imageUrl);
            if (result != null) {
                return result;
            }
        }
        if (!Objects.equals(salePlacard.getPhoneNumber(), original.getPhoneNumber())) {
            result = checkPhoneNumber(salePlacard.getPhoneNumber());
            return result;
        }


        return null;
    }


}
