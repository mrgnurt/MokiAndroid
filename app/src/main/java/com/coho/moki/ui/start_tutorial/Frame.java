package com.coho.moki.ui.start_tutorial;

public class Frame {
    public int height;
    public int width;
    public int x;
    public int y;

    public Frame(int x, int y, int with, int height) {
        this.x = x;
        this.y = y;
        this.width = with;
        this.height = height;
    }

    public static Frame FrameMake(int x, int y, int width, int height) {
        return new Frame(x, y, width, height);
    }
}