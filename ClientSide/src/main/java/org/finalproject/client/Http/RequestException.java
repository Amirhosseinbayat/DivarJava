package org.finalproject.client.Http;

public class RequestException extends Exception {
    private final int code;
    private final String description;

    public RequestException(int code, String description) {
        super(description);
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void printDetails() {
        System.out.println("request failed with code"+code+":"+description);
    }
}
