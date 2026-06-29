package com.pattern.factory;

public class WordDocument implements Document {
    @Override
    public void open() {
        System.out.println("Opening MS Word Document (.docx)... Ready for editing.");
    }
}
