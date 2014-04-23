package com.csab.TextEncoder;

import java.util.Map;
import java.util.HashMap;
import android.content.Context;
import org.apache.commons.codec.DecoderException;

public class HexMessage extends Message {

    public static Map<Long, String> mapLongString = new HashMap<>();
    public static Map<String, Long> mapStringLong = new HashMap<>();

    public HexMessage(byte[] inputArray) {
        super(inputArray);
    }

    public HexMessage(String inputString, Context context) throws DecoderException,
            MessageConstructException {
        super(inputString.toUpperCase(), context);
        checkValid(getStringValuesArray());
        long[] tempArray = calcDecimalArray(getStringValuesArray());
        setLongValuesArray(tempArray);
    }

    public HexMessage(long[] inputArray) {
        super(inputArray);
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
        for (int i = 0; i < getLongValuesArray().length; i++) {
            result += calcHexValue(getLongValuesArray()[i]);
            if (i != getLongValuesArray().length - 1)
                result += " ";
        }
        return result;
    }

    public boolean checkValid(String[] array) throws MessageConstructException {
        for (String s: array) {
            if(!s.matches("^[A-F0-9]+$"))
                throw new MessageConstructException(
                        getContext().getResources().getString(R.string.invalid_input_message));
        }
        return true;
    }
}
