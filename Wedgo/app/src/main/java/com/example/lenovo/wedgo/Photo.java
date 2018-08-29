package com.example.lenovo.wedgo;
//the interference of photo data
public class Photo {

    public int photographer_id;
    public String photographer_name,photographer_img,photographer_description;



    public Photo(int photographer_id, String photographer_name, String photographer_img,
                 String photographer_description) {

        this.photographer_id = photographer_id;
        this.photographer_name = photographer_name;
        this.photographer_img = photographer_img;
        this.photographer_description = photographer_description;




    }

    public int getPhotographer_id() {
        return photographer_id;
    }

    public void setPhotographer_id(int photographer_id) {
        this.photographer_id = photographer_id;
    }

    public String getPhotographer_name() {
        return photographer_name;
    }

    public void setPhotographer_name(String photographer_name) {
        this.photographer_name = photographer_name;
    }

    public String getPhotographer_img() {
        return photographer_img;
    }

    public void setPhotographer_img(String photographer_img) {
        this.photographer_img = photographer_img;
    }
    public String getPhotographer_description() {
        return photographer_description;
    }

    public void setPhotographer_description(String photographer_description) {
        this.photographer_description = photographer_description;
    }





}
