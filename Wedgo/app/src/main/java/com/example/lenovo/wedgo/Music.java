package com.example.lenovo.wedgo;
// the interference of music data

public class Music {

    public int music_band_id;
    public String singer_name,music_band_img,music_band_description;



    public Music(int music_band_id, String singer_name, String music_band_img,
                 String music_band_description) {

        this.music_band_id = music_band_id;
        this.singer_name = singer_name;
        this.music_band_img = music_band_img;
        this.music_band_description = music_band_description;




    }

    public int getMusic_band_id() {
        return music_band_id;
    }

    public void setMusic_band_id(int music_band_id) {
        this.music_band_id = music_band_id;
    }

    public String getSinger_name() {
        return singer_name;
    }

    public void setSinger_name(String singer_name) {
        this.singer_name = singer_name;
    }

    public String getMusic_band_img() {
        return music_band_img;
    }

    public void setMusic_band_img(String music_band_img) {
        this.music_band_img = music_band_img;
    }
    public String getMusic_band_description() {
        return music_band_description;
    }

    public void setMusic_band_description(String music_band_description) {
        this.music_band_description = music_band_description;
    }





}
