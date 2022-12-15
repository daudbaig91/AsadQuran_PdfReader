package com.example.asadquran;

import java.io.Serializable;

public class bookmarklist implements Serializable {

     String surah , parah, verse , page , title , description;

    public bookmarklist(String surah ,String parah,String verse ,String page ,String title ,String description){

        this.surah = surah;
        this.parah=parah;
        this.verse=verse;
        this.page=page;
        this.title=title;
        this.description=description;
    }


    //test completed
    public  String getDescription() {
        return description;
    }


    public  String getTitle() {
        return title;
    }

    public  String getPage() {
        return page;
    }

    public  String getParah() {
        return parah;
    }

    public  String getSurah() {
        return surah;
    }


    public  String getVerse() {
        return verse;
    }
}
