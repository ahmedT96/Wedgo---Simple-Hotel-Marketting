package com.example.lenovo.wedgo;


public class Data {


    public int img_id;
    public String rate,price;
    public String normal_img_url,single_img_url,multi_img_url;
    public String img_name,description;


    public Data(int img_id, String img_name, String normal_img_url,String single_img_url, String multi_img_url, String description, String rate, String price) {

        this.img_id = img_id;
        this.img_name = img_name;
        this.normal_img_url = normal_img_url;
        this.single_img_url = single_img_url;
        this.multi_img_url=multi_img_url;
        this.description = description;
        this.rate=rate;
        this.price=price;



    }

    public int getImg_id() {
        return img_id;
    }

    public void setImg_id(int img_id) {
        this.img_id = img_id;
    }

    public String getImg_name() {
        return img_name;
    }

    public void setImg_name(String img_name) {
        this.img_name = img_name;
    }

    public String getNormal_img_url() {
        return normal_img_url;
    }

    public void setNormal_img_url(String normal_img_url) {
        this.normal_img_url = normal_img_url;
    }
    public String getSingle_img_url() {
        return single_img_url;
    }

    public void setSingle_img_url(String single_img_url) {
        this.single_img_url = single_img_url;
    }

    public String getMulti_img_url() {
        return multi_img_url;
    }

    public void setMulti_img_url(String multi_img_url) {
        this.multi_img_url = multi_img_url;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }





}
