package com.csab.TextEncoder;

public class OctalMessage extends Message {

    private long[] longOctalArray;

    public OctalMessage(byte[] inputArray) {
        super(inputArray);
        longOctalArray = new long[inputArray.length];
        for (int i = 0; i < longOctalArray.length; i++) {
            longOctalArray[i] = (long)inputArray[i];
        }
    }

    public OctalMessage(String inputString) throws NumberFormatException, MessageConstructException {
        String[] stringArray = inputString.split("\\s+");
        checkValid(stringArray);
        for (int i = 0; i < stringArray.length; i++) {
            longOctalArray[i] = Long.parseLong(stringArray[i]);
        }
        longOctalArray = calcDecimalArray(longOctalArray);
    }

    public OctalMessage(long[] inputArray) throws NumberFormatException {
        longOctalArray = inputArray;
    }

    public AsciiMessage toAsciiMessage() throws MessageConstructException {
        return new AsciiMessage(longOctalArray);
    }

    public Base64Message toBase64Message() {
        return new Base64Message(longOctalArray);
    }

    public BinaryMessage toBinaryMessage() {
        return new BinaryMessage(longOctalArray);
    }

    public DecimalMessage toDecimalMessage() {
        return new DecimalMessage(longOctalArray);
    }

    public HexMessage toHexMessage() {
        return new HexMessage(longOctalArray);
    }

    private long calcOctalValue(long value) {
        String result = "";
        while (value > 0) {
            long rem = value % 8;
            value /= 8;
            result = rem + result;
        }
        return Long.parseLong(result);
    }

    private long[] calcDecimalArray(long[] array) {
        for (int i = 0; i < array.length; i++) {
            String[] digits = String.valueOf(array[i]).split("(?!^)");
            long result = 0;
            for (int j = 0; j < digits.length; j++) {
                result +=  Long.parseLong(digits[j]) * Math.pow(8, digits.length-j-1);
            }
            array[i] = result;
        }
        return array;
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < longOctalArray.length; i++) {
            result += calcOctalValue(longOctalArray[i]);
            if (i != longOctalArray.length - 1)
                result += " ";
        }
        return result;
    }

    private boolean checkValid(String[] array) throws MessageConstructException {
        for (String s: array) {
            if (!s.matches("^[0-7]+$"))
                throw new MessageConstructException(MessageConstructException.INVALID_INPUT_MESSAGE);
        }
        return true;
    }
}
