package com.csab.TextEncoder;

import java.util.Arrays;
import android.content.Context;

public class DecimalMessage extends Message {

    public DecimalMessage(byte[] inputArray) throws MessageConstructException {
        super(inputArray);
    }

    public DecimalMessage(String inputString, Context context) throws MessageConstructException,
            NumberFormatException {
        super(inputString, context);
        checkValid(getStringValuesArray());
        long[] tempArray = new long[getStringValuesArray().length];
        for (int i = 0; i < getStringValuesArray().length; i++) {
            tempArray[i] = Long.parseLong(getStringValuesArray()[i]);
        }
        setLongValuesArray(tempArray);
    }

    public DecimalMessage(long[] inputArray) {
        super(inputArray);
    }

    @Override
    public String toString() {
        return Arrays.toString(getLongValuesArray()).replaceAll("[^0-9\\s]","");
    }

    private boolean checkValid(String[] array) throws MessageConstructException {
        for (String s : array) {
            if (!s.matches("^[0-9]+$")) {
                throw new MessageConstructException(
                        getContext().getResources().getString(R.string.invalid_input_message));
            }
        }
        return true;
    }
}
