package com.csab.TextEncoder;

import org.apache.commons.codec.binary.StringUtils;

public class Message {

    private byte[] characterCodeArray;

    public Message() { }

    public Message(byte[] inputArray) {
        characterCodeArray = inputArray;
    }

    public Message(String inputString) {
        characterCodeArray = StringUtils.getBytesUsAscii(inputString);
    }

    public byte[] getCharacterCodeArray() {
        return characterCodeArray;
    }

    public void setCharacterCodeArray(byte[] updatedArray) {
        characterCodeArray = updatedArray;
    }

    @Override
    public String toString() {
        return new String(characterCodeArray);
    }
}
