package com.pattern.factory;

public abstract class DocumentFactory {
    // Factory method that concrete subclasses must implement
    public abstract Document createDocument();

    // Helper method that uses the factory method to create and open a document
    public void openDocument() {
        Document document = createDocument();
        document.open();
    }
}
