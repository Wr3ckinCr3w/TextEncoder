package com.csab.TextEncoder;

import java.util.Arrays;

public class OctalMessage extends Message {

    private long[] longOctalArray;

    public OctalMessage(byte[] inputArray) {
        super(inputArray);
        longOctalArray = new long[inputArray.length];
        for (int i = 0; i < longOctalArray.length; i++) {
            longOctalArray[i] = calcOctalValue((long)inputArray[i]);
        }
    }

    public OctalMessage(String inputString) throws MessageConstructException {
        if (!isValid(inputString)) {
            throw new MessageConstructException(Message.INVALID_INPUT_MESSAGE);
        }
        String[] stringArray = inputString.split("\\s+");
        longOctalArray = new long[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            longOctalArray[i] =
                    calcOctalValue(Long.parseLong(stringArray[i]));
        }
    }

    public OctalMessage(long[] inputArray) {
        longOctalArray = inputArray;
        for (int i = 0; i < longOctalArray.length; i++) {
            longOctalArray[i] = calcOctalValue(longOctalArray[i]);
        }
    }

    public AsciiMessage toAsciiMessage() throws MessageConstructException {
        if (getCharacterCodeArray() != null) {
            return new AsciiMessage(getCharacterCodeArray());
        } else {
            return new AsciiMessage(longOctalArray);
        }
    }

    public Base64Message toBase64Message() {
        return new Base64Message(calcDecimalArray(longOctalArray));
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
        return Arrays.toString(longOctalArray).replaceAll("[^0-7\\s]","");
    }

    private boolean isValid(String inputString) {
        return inputString.matches("^[0-7]+$");
    }
}
