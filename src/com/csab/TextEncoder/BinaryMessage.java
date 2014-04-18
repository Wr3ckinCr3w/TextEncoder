package com.csab.TextEncoder;

public class BinaryMessage extends Message {

    private long[] longBinaryArray;
    private static final int NUMBER_OF_BITS = 8;

    public BinaryMessage(byte[] inputArray) throws MessageConstructException {
        super(inputArray);
        longBinaryArray = new long[inputArray.length];
        for (int i = 0; i < longBinaryArray.length; i++) {
            longBinaryArray[i] = Long.parseLong(Long.toBinaryString((long) inputArray[i]), 2);
        }
    }

    public BinaryMessage(String inputString) throws MessageConstructException {
        if (!isValid(inputString.replaceAll("\\s", ""))) {
            throw new MessageConstructException(Message.INVALID_INPUT_MESSAGE);
        }
        String[] stringArrayOfBytes = inputString.split("\\s+");
        longBinaryArray = new long[stringArrayOfBytes.length];
        for (int i = 0; i < longBinaryArray.length; i++) {
            longBinaryArray[i] = Long.parseLong(stringArrayOfBytes[i], 2);
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

    private boolean isValid(String inputString) {
        for (char ch : inputString.toCharArray()) {
            if (ch == '1'  || ch == '0') {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }
}
