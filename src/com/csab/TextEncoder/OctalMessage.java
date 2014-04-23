package com.csab.TextEncoder;

import android.content.Context;

public class OctalMessage extends Message {

    public OctalMessage(byte[] inputArray) {
        super(inputArray);
    }

    public OctalMessage(String inputString, Context context) throws NumberFormatException {
        super(inputString, context);
        checkValid(getStringValuesArray());
        long[] tempArray = new long[getStringValuesArray().length];
        for (int i = 0; i < tempArray.length; i++) {
            tempArray[i] = Long.parseLong(getStringValuesArray()[i]);
        }
        tempArray = calcDecimalArray(tempArray);
        setLongValuesArray(tempArray);
    }

    public OctalMessage(long[] inputArray) throws NumberFormatException {
        super(inputArray);
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
        for (int i = 0; i < getLongValuesArray().length; i++) {
            result += calcOctalValue(getLongValuesArray()[i]);
            if (i != getLongValuesArray().length - 1)
                result += " ";
        }
        return result;
    }

    private boolean checkValid(String[] array) throws NumberFormatException {
        for (String s: array) {
            if (!s.matches("^[0-7]+$"))
                throw new NumberFormatException(
                        getContext().getResources().getString(R.string.invalid_input_message));
        }
        return true;
    }
}
