package com.example.lenovo.wedgo;

//the interference of car data

public class Car {

    public int car_id;
    public String car_model,car_img,car_description;



    public Car(int car_id, String car_model, String car_img,String car_description) {

        this.car_id = car_id;
        this.car_model = car_model;
        this.car_img = car_img;
        this.car_description = car_description;




    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public String getCar_model() {
        return car_model;
    }

    public void setCar_model(String car_model) {
        this.car_model = car_model;
    }

    public String getCar_img() {
        return car_img;
    }

    public void setCar_img(String car_img) {
        this.car_img = car_img;
    }
    public String getCar_description() {
        return car_description;
    }

    public void setCar_description(String car_description) {
        this.car_description = car_description;
    }





}
