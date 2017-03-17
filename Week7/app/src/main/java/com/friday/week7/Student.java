package com.friday.week7;

public class Student {

    private String name, phone, hobby, address;
    private float age;

    public String getName(){ return name; }
    public String getHobby() {return hobby;}
    public String getPhone(){ return phone; }
    public float getAge() {return age;}
    public String getAdrress(){ return address; }


    public Student(String name, String hobby, String phone, String address, float age){
        this.name = name;
        this.hobby = hobby;
        this.phone = phone;
        this.address = address;
        this.age=age;
    }
}
