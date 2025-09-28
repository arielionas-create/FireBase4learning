package com.example.firebase_4learning;

public class Men {
    private int age;
    private String name;

    public Men() {
        // חובה לפיירבייס
    }

    public Men(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }
}