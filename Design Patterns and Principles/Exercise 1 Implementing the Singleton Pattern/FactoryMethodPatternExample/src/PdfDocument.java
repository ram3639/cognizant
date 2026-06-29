package com.pattern.factory;

public class PdfDocument implements Document {
    @Override
    public void open() {
        System.out.println("Opening PDF Document (.pdf) in Read-Only Mode... Layouts loaded.");
    }
}
