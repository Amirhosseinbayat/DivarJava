package org.finalproject.server.Logic;

import java.io.IOException;

public class PasswordValidator implements BusinessLogic {
    public String validatePassword(String password) {
        if (password == null) return "پسورد نمیتواند خالی باشد.";
        if (password.length()<8) return "پسورد کمتر از 8 کاراکتر است.";

        return null;
    }
}
