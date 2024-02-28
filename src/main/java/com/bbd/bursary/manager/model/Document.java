package com.bbd.bursary.manager.model;

public class Document {

    private int DocumentID;
    private String Transcript;
    private String IdentityDocument;
    private long BursaryApplicationID;

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

    public long getBursaryApplicationID() {
        return BursaryApplicationID;
    }

    public void setBursaryApplicationID(long bursaryApplicationID) {
        BursaryApplicationID = bursaryApplicationID;
    }
}
