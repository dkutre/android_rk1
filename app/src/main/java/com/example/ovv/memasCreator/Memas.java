package com.example.ovv.memasCreator;

public class Memas {
    private String topText;
    private String botText;
    private String imageName;
    public Memas(String topText, String botText, String category) {
        this.topText = topText;
        this.botText = botText;
        this.imageName = category;
    }

    public String getTopText() {
        return this.topText;
    }

    public String getBotText() {
        return this.botText;
    }

    public String getImageName() {
        return this.imageName;
    }
}
