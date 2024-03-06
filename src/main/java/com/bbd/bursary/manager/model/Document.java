package com.bbd.bursary.manager.model;

public class Document {

    private int documentId;
    private String Transcript;
    private String IdentityDocument;
    private long BursaryApplicationID;

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
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

    @Override
    public String toString() {
        return "Document{" +
                "documentId=" + documentId +
                ", Transcript='" + Transcript + '\'' +
                ", IdentityDocument='" + IdentityDocument + '\'' +
                ", BursaryApplicationID=" + BursaryApplicationID +
                '}';
    }
}
