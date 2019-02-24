package net.minecord.messagenotifier.entity;

public class WelcomeTitle {

    private String title;
    private String subTitle;
    private int stay;
    private int fadeIn;
    private int fadeOut;

    public WelcomeTitle(String title, String subTitle, int stay, int fadeIn, int fadeOut) {
        this.title = title;
        this.subTitle = subTitle;
        this.stay = stay;
        this.fadeIn = fadeIn;
        this.fadeOut = fadeOut;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public int getStay() {
        return stay;
    }

    public void setStay(int stay) {
        this.stay = stay;
    }

    public int getFadeIn() {
        return fadeIn;
    }

    public void setFadeIn(int fadeIn) {
        this.fadeIn = fadeIn;
    }

    public int getFadeOut() {
        return fadeOut;
    }

    public void setFadeOut(int fadeOut) {
        this.fadeOut = fadeOut;
    }
}
