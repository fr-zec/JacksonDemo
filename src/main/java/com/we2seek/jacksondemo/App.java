package com.we2seek.jacksondemo;

import com.we2seek.jacksondemo.work.Response;
import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {

        // Util.saveToFile("items.json", Util.generateItems());
        // Util.saveToFile("response.json", Util.generateResponse());
        Response response = Util.readValue("category.json");
        
        System.out.println(response.getCategoryId("022742"));
    }

}
