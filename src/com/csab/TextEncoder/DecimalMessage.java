package com.csab.TextEncoder;

import java.util.Arrays;

public class DecimalMessage extends Message {

    private long[] longDecimalArray;

    public DecimalMessage(byte[] inputArray) throws MessageConstructException {
        super(inputArray);
        longDecimalArray = new long[inputArray.length];
        for (int i = 0; i < longDecimalArray.length; i++) {
            longDecimalArray[i] = Long.parseLong(String.valueOf(inputArray[i]));
        }
    }

    public DecimalMessage(String inputString) throws NumberFormatException {
        String[] stringArray = inputString.split("\\s+");
        longDecimalArray = new long[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            longDecimalArray[i] = Long.parseLong(stringArray[i]);
        }
    }

    public DecimalMessage(long[] inputArray) {
        longDecimalArray = inputArray;
    }

    public AsciiMessage toAsciiMessage() throws MessageConstructException {
        if (getCharacterCodeArray() != null) {
            return new AsciiMessage(getCharacterCodeArray());
        } else {
            return new AsciiMessage(longDecimalArray);
        }
    }

    public Base64Message toBase64Message() {
        return new Base64Message(longDecimalArray);
    }

    public BinaryMessage toBinaryMessage() {
        return new BinaryMessage(longDecimalArray);
    }

    public HexMessage toHexMessage() {
        return new HexMessage(longDecimalArray);
    }

    public OctalMessage toOctalMesage() {
        return new OctalMessage(longDecimalArray);
    }

    @Override
    public String toString() {
        return Arrays.toString(longDecimalArray).replaceAll("[^0-9\\s]","");
    }

}
