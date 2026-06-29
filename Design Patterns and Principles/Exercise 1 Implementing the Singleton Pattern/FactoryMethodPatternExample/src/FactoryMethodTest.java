package com.pattern.factory;

public class FactoryMethodTest {
    public static void main(String[] args) {
        System.out.println("=== Testing Factory Method Pattern ===");

        // Create factories
        DocumentFactory wordFactory = new WordDocumentFactory();
        DocumentFactory pdfFactory = new PdfDocumentFactory();
        DocumentFactory excelFactory = new ExcelDocumentFactory();

        System.out.println("1. Using WordDocumentFactory:");
        Document wordDoc = wordFactory.createDocument();
        System.out.print("   Direct open: ");
        wordDoc.open();
        System.out.print("   Factory helper open: ");
        wordFactory.openDocument();

        System.out.println("\n2. Using PdfDocumentFactory:");
        Document pdfDoc = pdfFactory.createDocument();
        System.out.print("   Direct open: ");
        pdfDoc.open();
        System.out.print("   Factory helper open: ");
        pdfFactory.openDocument();

        System.out.println("\n3. Using ExcelDocumentFactory:");
        Document excelDoc = excelFactory.createDocument();
        System.out.print("   Direct open: ");
        excelDoc.open();
        System.out.print("   Factory helper open: ");
        excelFactory.openDocument();

        System.out.println("\nSUCCESS: Factory Method implementation verified successfully!");
    }
}
