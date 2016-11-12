package com.example.or.makeitwhite;

/**
 * Created by Or on 30/10/2016.
 */
interface Communicator {

    public void requestRandomClick();

    public boolean getBlue();
    public boolean getGreen();
    public boolean getRed();
    public boolean getYellow();

    public void setBlueTrue();
    public void setGreenTrue();
    public void setRedTrue();
    public void setYellowTrue();
}
