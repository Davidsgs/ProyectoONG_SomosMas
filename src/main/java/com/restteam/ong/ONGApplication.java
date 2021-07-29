package com.restteam.ong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ONGApplication{

    public static void main(String[] args) {

        String firma = "------------------------------ Created By: -----------------------------\n" +
                "8888888b.                   888    888                                   \n" +
                "888   Y88b                  888    888                                   \n" +
                "888    888                  888    888                                   \n" +
                "888   d88P .d88b.  .d8888b  888888 888888 .d88b.   8888b.  88888b.d88b.  \n" +
                "8888888P\" d8P  Y8b 88K      888    888   d8P  Y8b     \"88b 888 \"888 \"88b \n" +
                "888 T88b  88888888 \"Y8888b. 888    888   88888888 .d888888 888  888  888 \n" +
                "888  T88b Y8b.          X88 Y88b.  Y88b. Y8b.     888  888 888  888  888 \n" +
                "888   T88b \"Y8888   88888P'  \"Y888  \"Y888 \"Y8888  \"Y888888 888  888  888 \n" +
                "---------------------------------~<o>~----------------------------------";

        SpringApplication.run(ONGApplication.class, args);
        System.out.println(firma);
    }



}
