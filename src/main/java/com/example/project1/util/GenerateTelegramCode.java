package com.example.project1.util;

public class GenerateTelegramCode {
    public String generateCode(){
        StringBuilder randomNumber = new StringBuilder(Integer.toString((int) (Math.random() * 1000000)));
        while (randomNumber.length() != 6){
            randomNumber.append("0");
        }
     return randomNumber.toString();
    }
}
