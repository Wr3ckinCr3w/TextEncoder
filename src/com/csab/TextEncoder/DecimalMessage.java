package com.csab.TextEncoder;

import java.util.Arrays;

public class DecimalMessage extends Message {

    public DecimalMessage(byte[] inputArray) {
        super(inputArray);
    }

    public DecimalMessage(String inputString) {
        super(inputString);
    }

    public BinaryMessage toBinaryMessage() {
        return new BinaryMessage(getCharacterCodeArray());
    }

    public HexMessage toHexadecimalMessage() {
        return new HexMessage(getCharacterCodeArray());
    }

    @Override
    public String toString() {
        return (Arrays.toString(getCharacterCodeArray())).replaceAll("[^0-9\\s]", "");
    }

}
