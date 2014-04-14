package com.csab.TextEncoder;

import org.apache.commons.codec.binary.StringUtils;
import java.util.Arrays;

public class Message {

    private byte[] characterCodeArray;

    public Message() { }

    public Message(byte[] inputArray) {
        characterCodeArray = Arrays.copyOf(inputArray, inputArray.length);
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
