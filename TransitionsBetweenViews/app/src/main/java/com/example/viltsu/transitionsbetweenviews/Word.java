package com.example.viltsu.transitionsbetweenviews;

/**
 * Created by ville-pekkapalmgren on 22/02/18.
 */

public class Word {

    private String miwok;
    private String english;
    private int picture;
    private int audio;

    public Word(){
        miwok = "";
        english = "";
        picture = -1;
        audio = -1;
    }

    public Word(String mi, String engl, int audio){
        this.miwok = mi;
        this.english = engl;
        this.picture = -1;
        this.audio = audio;
    }

    public Word(String mi, String engl, int pic, int audio){
        this.miwok = mi;
        this.english = engl;
        this.picture = pic;
        this.audio = audio;
    }

    public int getAudio() {
        return audio;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public String getMiwok() {
        return miwok;
    }

    public void setMiwok(String miwok) {
        this.miwok = miwok;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getDefaultTranslation(){
        return english;
    }
    public String getMiwokWord(){
        return miwok;
    }
}
