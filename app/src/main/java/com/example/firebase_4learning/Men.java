package com.example.firebase_4learning;

import androidx.annotation.NonNull;


public class Men {
    private int age;
    private String name;
    public Men()
    {
        age = 0;
        name = "";
    }
    public Men(int age , String name)
    {
        this.age = age;
        this.name = name;
    }
    public String getName() {return this.name;}
    public int getAge() {return this.age;}
    public void setAge(int age) {this.age = age;}
    public void setName(String name) {this.name = name;}


    @NonNull
    @Override
    public String toString() {
        return "name: " + this.name + " age: " + this.age;
    }
}
