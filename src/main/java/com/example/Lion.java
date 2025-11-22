package com.example;

import java.util.List;

public class Lion {

    boolean hasMane;
    Feline feline;  // Зависимость, которая будет инъектирована

    // Конструктор принимает и пол, и зависимость Feline
    public Lion(String sex, Feline feline) throws Exception {
        this.feline = feline;  // Сохраняем переданную зависимость

        if ("Самец".equals(sex)) {
            hasMane = true;
        } else if ("Самка".equals(sex)) {
            hasMane = false;
        } else {
            throw new Exception("Используйте допустимые значения пола животного - самец или самка");
        }
    }

    public int getKittens() {
        return feline.getKittens();
    }

    public boolean doesHaveMane() {
        return hasMane;
    }

    public List<String> getFood() throws Exception {
        return feline.eatMeat();
    }
}