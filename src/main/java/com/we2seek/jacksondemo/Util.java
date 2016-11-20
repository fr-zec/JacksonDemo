package com.we2seek.jacksondemo;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.we2seek.jacksondemo.items.Item;
import com.we2seek.jacksondemo.items.Property;
import com.we2seek.jacksondemo.work.Category;
import com.we2seek.jacksondemo.work.Event;
import com.we2seek.jacksondemo.work.Response;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Util {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void saveToFile(final String filename, Object stuffToSave) {
        try {
            File file = new File("./src/main/resources/" + filename);

            MAPPER.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
            MAPPER.writeValue(file, stuffToSave);

        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static List<Item> generateItems() {
        String[][] source = {
            {"100500", "Item 100500", "01510:prop00:val00 01510:prop01:val01 01510:prop02:val02 01511:prop00:val00 01511:prop01:val01"},
            {"100501", "Item 100501", "01610:prop00:val00 01610:prop01:val01 01610:prop02:val02 01511:prop00:val00 01611:prop01:val01"},
            {"100502", "Item 100502", "01510:prop00:val00 01710:prop01:val01 01510:prop02:val02 01511:prop00:val00 01511:prop01:val01"},};

        return Arrays.stream(source).map((String[] arrayItem) -> {
            Map<String, Property> properties = new HashMap<>();
            String[] split = arrayItem[2].split(" ");

            for (String s : split) {
                String[] split1 = s.split(":");
                properties.put(split1[0], new Property(split1[1], split1[2]));
            }

            return new Item(Util.generateUUID(), arrayItem[1], properties);
        }).collect(Collectors.toList());
    }

    public static String generateUUID() {
        String result = null;
        try {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            byte[] randomBytes = new byte[64];
            secureRandom.nextBytes(randomBytes);

            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] resultSha = sha.digest(randomBytes);

            return hexEncode(resultSha);

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    /**
     * The byte[] returned by MessageDigest does not have a nice textual
     * representation, so some form of encoding is usually performed.
     *
     * This implementation follows the example of David Flanagan's book "Java In
     * A Nutshell", and converts a byte array into a String of hex characters.
     *
     * Another popular alternative is to use a "Base64" encoding.
     *
     * @param aInput bytes to be coded
     * @return encoded result as String
     */
    public static String hexEncode(byte[] aInput) {
        StringBuilder result = new StringBuilder();
        char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        for (int idx = 0; idx < aInput.length; ++idx) {
            byte b = aInput[idx];
            result.append(digits[(b & 0xf0) >> 4]);
            result.append(digits[b & 0x0f]);
        }

        return result.toString();
    }

    public static Response generateResponse() {
        String[][] source = {
            {"0100500", "10 2016-11-20T12:00:00"},
            {"0100501", "11 2016-10-20T12:00:10"},
            {"0100502", "12 2016-09-20T12:00:20"},
            {"0100503", "13 2016-08-20T12:00:30"},
            {"0100504", "14 2016-07-20T12:00:40"}
        };

        Map<String, Event> events = Arrays.stream(source)
                .collect(Collectors.toMap(
                        arr -> arr[0],
                        arr -> {
                            String[] split = arr[1].split(" ");
                            Category category = new Category(Long.parseLong(split[0]), split[1]);

                            return new Event(category);
                        })
                );

        final Response result = new Response(events);
        Logger.getLogger(Util.class.getName()).log(Level.INFO, result.toString());

        return result;
    }

    public static Response readValue(final String filename) {
        Response fromFile = null;

        try {
            fromFile = MAPPER.readValue(new File("./src/main/resources/" + filename), Response.class);
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return fromFile;
    }
}
