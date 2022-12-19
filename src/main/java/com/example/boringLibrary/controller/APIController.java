package com.example.boringLibrary.controller;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class APIController {

    private String APIKey = "AIzaSyBVFORSWf-PrtLdl80E1i75-y6UFU_Y74c";
    private int maxBooks = 40;


    @GetMapping("API/{params}/{pageNum}")
    public String APICall(@PathVariable String params, @PathVariable String pageNum) throws Exception {

        String output = "";
        int x = 0;
        String ret = "";

        URL url = new URL("https://www.googleapis.com/books/v1/volumes?q="+params+":&printType=books&startIndex="+(maxBooks*Integer.parseInt(pageNum))+"&maxResults="+maxBooks+"&keyes&key="+APIKey);
        URLConnection yc = url.openConnection();
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                yc.getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null) 
            System.out.println(inputLine);
            output += inputLine;
            x += 1;
            if (x > 20){
                ret = inputLine;
            }
        in.close();

        return ret;
    }
}