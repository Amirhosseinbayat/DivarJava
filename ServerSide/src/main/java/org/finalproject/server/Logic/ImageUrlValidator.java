package org.finalproject.server.Logic;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageUrlValidator {
    public static String validateImageUrl(String profileImage) {
        if (profileImage != null && !profileImage.isEmpty()) {
            URL url;
            try {
                url = new URL(profileImage);
            } catch (MalformedURLException e) {
                System.out.println(e.getMessage());
                return "not a valid url. please send a full url to an image.";
            }
            try {
                ImageIO.read(url);
            } catch (IOException e) {
                return "We could not download the image file from this url. are you sure it is pointing to an image?";
            }

        }
        return null;
    }
}
