package com.bbd.bursary.manager.model;

public class Document {

    int DocumentID;
    String Transcript;
    String IdentityDocument;
    int BursaryApplicationID;

    public int getDocumentID() {
        return DocumentID;
    }

    public void setDocumentID(int documentID) {
        DocumentID = documentID;
    }

    public String getTranscript() {
        return Transcript;
    }

    public void setTranscript(String transcript) {
        Transcript = transcript;
    }

    public String getIdentityDocument() {
        return IdentityDocument;
    }

    public void setIdentityDocument(String identityDocument) {
        IdentityDocument = identityDocument;
    }

    public int getBursaryApplicationID() {
        return BursaryApplicationID;
    }

    public void setBursaryApplicationID(int bursaryApplicationID) {
        BursaryApplicationID = bursaryApplicationID;
    }
}
