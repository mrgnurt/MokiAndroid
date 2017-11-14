package com.coho.moki.ui.start_tutorial;

public class Frame {
    public int height;
    public int width;
    public int f170x;
    public int f171y;

    public Frame(int x, int y, int with, int height) {
        this.f170x = x;
        this.f171y = y;
        this.width = with;
        this.height = height;
    }

    public static Frame FrameMake(int x, int y, int width, int height) {
        return new Frame(x, y, width, height);
    }
}