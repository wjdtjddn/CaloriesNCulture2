package com.example.caloriesnculture;

public class FoodDTO {

    private String title;
    private String content;


    public FoodDTO(String title, String content){


        this.title=title;
        this.content=content;
    }


    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

}