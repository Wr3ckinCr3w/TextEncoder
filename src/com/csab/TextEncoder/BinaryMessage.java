package com.csab.TextEncoder;

import android.content.Context;

public class BinaryMessage extends Message {

    private static final int NUMBER_OF_BITS = 8;

    public BinaryMessage(byte[] inputArray) {
        super(inputArray);
    }

    public BinaryMessage(String inputString, Context context) throws MessageConstructException,
            NumberFormatException {
        super(inputString, context);
        checkValid(getStringValuesArray());
        long[] tempArray = new long[getStringValuesArray().length];
        for (int i = 0; i < tempArray.length; i++) {
            tempArray[i] = Long.parseLong(getStringValuesArray()[i], 2);
        }
        setLongValuesArray(tempArray);
    }

    public BinaryMessage(long[] inputArray) {
        super(inputArray);
    }

    @Override
    public String toString() {
        String[] binaryStringArray = new String[getLongValuesArray().length];
        String result = "";
        for (int i = 0; i < getLongValuesArray().length; i++) {
            binaryStringArray[i] = Long.toBinaryString(getLongValuesArray()[i]);
            while (binaryStringArray[i].length() < NUMBER_OF_BITS) {
                binaryStringArray[i] = "0" + binaryStringArray[i];
            }
            if (i == getLongValuesArray().length - 1) {
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
                throw new MessageConstructException(
                        getContext().getResources().getString(R.string.invalid_input_message));
            }
        }
        return true;
    }

}
