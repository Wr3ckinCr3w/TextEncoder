package com.csab.TextEncoder;

public class MessageConstructException extends Exception {

    public static final String INVALID_INPUT_MESSAGE = "Invalid input format";

    public MessageConstructException(String e)
    {
        super(e);
    }
    public String getMessage()
    {
        return super.getMessage();
    }

}
