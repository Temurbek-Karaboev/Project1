package com.example.project1.util;


public class GenerateTelegramCode {
    public String generateCode(){
        String randomNumber =Integer.toString( (int)(Math.random() * 1000000));
        while (randomNumber.length() != 6){
            randomNumber +="0";
        }
     return randomNumber;
    }
}
