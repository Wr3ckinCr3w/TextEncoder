package com.csab.TextEncoder;

import org.apache.commons.codec.DecoderException;
import java.util.Map;
import java.util.HashMap;

public class HexMessage extends Message {

    private long[] longHexArray;
    public static Map<Long, String> mapLongString= new HashMap<>();
    public static Map<String, Long> mapStringLong= new HashMap<>();

    public HexMessage(byte[] inputArray) {
        super(inputArray);
    }

    public HexMessage(String inputString) throws DecoderException, MessageConstructException {
        String[] stringArray = inputString.toUpperCase().split("\\s+");
        checkValid(stringArray);
        longHexArray = calcDecimalArray(stringArray);
    }

    public HexMessage(long[] inputArray) {
        longHexArray = inputArray;
    }

    public AsciiMessage toAsciiMessage() throws MessageConstructException {
        return new AsciiMessage(longHexArray);
    }

    public Base64Message toBase64Message() throws MessageConstructException {
        return new Base64Message(longHexArray);
    }

    public BinaryMessage toBinaryMessage() throws MessageConstructException {
        return new BinaryMessage(longHexArray);
    }

    public DecimalMessage toDecimalMessage() throws MessageConstructException {
        return new DecimalMessage(longHexArray);
    }

    public OctalMessage toOctalMessage() throws MessageConstructException {
        return new OctalMessage(longHexArray);
    }

    private String calcHexValue(long value) {

        String result = "";
        while (value > 0) {
            long rem = value % 16;
            value /= 16;
            if (HexMessage.mapLongString.get(rem) != null) {
                result = HexMessage.mapLongString.get(rem) + result;
            } else {
                result = rem + result;
            }
        }
        return result;
    }

    private long[] calcDecimalArray(String[] inputArray) {
        long[] resultArray = new long[inputArray.length];
        for (int i = 0; i < inputArray.length; i++) {
            String[] digits = inputArray[i].split("(?!^)");
            long sum = 0;
            for (int j = 0; j < digits.length; j++) {
                if (HexMessage.mapStringLong.get(digits[j]) != null) {
                    sum += HexMessage.mapStringLong.get(digits[j]) * Math.pow(16, digits.length-j-1);
                } else {
                    sum += Long.parseLong(digits[j]) * Math.pow(16, digits.length-j-1);
                }
            }
            resultArray[i] = sum;
        }
        return resultArray;
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < longHexArray.length; i++) {
            result += calcHexValue(longHexArray[i]);
            if (i != longHexArray.length - 1)
                result += " ";
        }
        return result;
    }

    public boolean checkValid(String[] array) throws MessageConstructException {
        for (String s: array) {
            if(!s.matches("^[A-F0-9]+$"))
                throw new MessageConstructException(MessageConstructException.INVALID_INPUT_MESSAGE);
        }
        return true;
    }
}
