package Model;

import java.util.ArrayList;

public class Person {
    private int id;
    private String name;
    private String address;
    private int age;

    public Person(int id, String name, String address, int age){
        this.id = id;
        this.name = name;
        this.address = address;
        this.age = age;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public int getId(){
        return this.id;
    }

    public int getAge(){
        return this.age;
    }

    public String getName(){
        return this.name;
    }

    public String getAddress(){
        return this.address;
    }
}
