package com.csab.TextEncoder;

public class BinaryMessage extends Message {

    private static final int NUMBER_OF_BITS = 8;

    public BinaryMessage(byte[] inputArray) {
        super(inputArray);
    }

    public BinaryMessage(String inputString) {
        super(inputString.replace(" ",""));
    }

    public DecimalMessage toDecimalMessage() {
        return new DecimalMessage(getCharacterCodeArray());
    }

    public HexMessage toHexadecimalMessage() {
        return new HexMessage(getCharacterCodeArray());
    }

    @Override
    public String toString() {
        String[] binaryStringArray = new String[getCharacterCodeArray().length];
        String fullBinaryString = "";

        for (int i = 0; i < getCharacterCodeArray().length; i++) {
            binaryStringArray[i] = Long.toBinaryString((long) getCharacterCodeArray()[i]);
            while (binaryStringArray[i].length() < NUMBER_OF_BITS) {
                binaryStringArray[i] = "0" + binaryStringArray[i];
            }
            if (i == getCharacterCodeArray().length - 1) {
                fullBinaryString += binaryStringArray[i];
            } else {
                fullBinaryString += binaryStringArray[i] + " ";
            }
        }
        return fullBinaryString;
    }
}
