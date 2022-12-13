package com.example.boringLibrary;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.boringLibrary.model.Users;
import com.example.boringLibrary.repositories.BookRepo;
import com.example.boringLibrary.repositories.UserRepo;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private BookRepo bookRepo;

    public void run(ApplicationArguments args) throws Exception {


        //seeds one user with a book
        Users user = new Users("fred from scooby doo", "1234567");
        userRepo.save(user);

        URL url = new URL ("http://localhost:8080/users/1");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        String jsonInputString = "{\"id\": \"1\", \"title\": \"book1\", \"author\": \"author1\"}";
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);			
        }

        try(BufferedReader br = new BufferedReader(
        new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        }
        System.out.println("Seeded data");

    }
}
