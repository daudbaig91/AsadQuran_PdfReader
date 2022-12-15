package com.example.asadquran;

import java.io.Serializable;

public class surahclass implements Comparable<surahclass> , Serializable {
    Integer number;
    Integer page;

    public surahclass(Integer number,Integer page){
        this.number = number;
        this.page = page;
    }


    public int compareTo(surahclass compare) {
        int compareage=((surahclass)compare).number;
        /* For Ascending order*/
        return this.number-compareage;
    }


}
