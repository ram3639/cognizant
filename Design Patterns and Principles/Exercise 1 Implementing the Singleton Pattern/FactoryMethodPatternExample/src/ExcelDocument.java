package com.pattern.factory;

public class ExcelDocument implements Document {
    @Override
    public void open() {
        System.out.println("Opening MS Excel Spreadsheet (.xlsx)... Cells and formulas loaded.");
    }
}
