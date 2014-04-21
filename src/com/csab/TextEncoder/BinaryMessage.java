package com.csab.TextEncoder;

public class BinaryMessage extends Message {

    private long[] longBinaryArray;
    private static final int NUMBER_OF_BITS = 8;

    public BinaryMessage(byte[] inputArray) {
        super(inputArray);
        longBinaryArray = new long[inputArray.length];
        for (int i = 0; i < longBinaryArray.length; i++) {
            longBinaryArray[i] = (long) inputArray[i];
        }
    }

    public BinaryMessage(String inputString) throws MessageConstructException, NumberFormatException {
        String[] stringArray = inputString.split("\\s+");
        checkValid(stringArray);
        longBinaryArray = new long[stringArray.length];
        for (int i = 0; i < longBinaryArray.length; i++) {
            longBinaryArray[i] = Long.parseLong(stringArray[i], 2);
        }
    }

    public BinaryMessage(long[] inputArray) {
        longBinaryArray = new long[inputArray.length];
        for (int i = 0; i < longBinaryArray.length; i++) {
            longBinaryArray[i] = Long.parseLong(Long.toBinaryString(inputArray[i]), 2);
        }
    }

    public AsciiMessage toAsciiMessage() throws MessageConstructException {
        return new AsciiMessage(longBinaryArray);
    }

    public DecimalMessage toDecimalMessage() {
        return new DecimalMessage(longBinaryArray);
    }

    public Base64Message toBase64Message() {
        return new Base64Message(longBinaryArray);
    }

    public HexMessage toHexMessage() {
        return new HexMessage(longBinaryArray);
    }

    public OctalMessage toOctalMessage() {
        return new OctalMessage(longBinaryArray);
    }

    @Override
    public String toString() {
        String[] binaryStringArray = new String[longBinaryArray.length];
        String result = "";
        for (int i = 0; i < longBinaryArray.length; i++) {
            binaryStringArray[i] = Long.toBinaryString(longBinaryArray[i]);
            while (binaryStringArray[i].length() < NUMBER_OF_BITS) {
                binaryStringArray[i] = "0" + binaryStringArray[i];
            }
            if (i == longBinaryArray.length - 1) {
                result += binaryStringArray[i];
            } else {
                result += binaryStringArray[i] + " ";
            }
        }
        return result;
    }

    private boolean checkValid(String[] array) throws MessageConstructException {
        for (String s : array) {
            if (!s.matches("^[01]+$")) {
                throw new MessageConstructException(MessageConstructException.INVALID_INPUT_MESSAGE);
            }
        }
        return true;
    }

}
