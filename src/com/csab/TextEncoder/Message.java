package com.csab.TextEncoder;

import android.content.Context;

public class Message implements Converter<Message, Class> {

    private byte[] byteValuesArray;
    private long[] longValuesArray;
    private String[] stringValuesArray;
    private Context messageContext;

    public Message() { }

    public Message(byte[] inputArray) {
        byteValuesArray = inputArray;
        longValuesArray = new long[inputArray.length];
        for (int i = 0; i < longValuesArray.length; i++) {
            longValuesArray[i] = (long)inputArray[i];
        }
    }

    public Message(String inputString, Context context) {
        stringValuesArray = inputString.split("\\s+");
        messageContext = context;
    }

    public Message(long[] inputArray) {
        longValuesArray = inputArray;
    }

    public byte[] getByteValuesArray() {
        return byteValuesArray;
    }

    public void setByteValuesArray(byte[] updatedArray) {
        byteValuesArray = updatedArray;
    }

    public long[] getLongValuesArray() {
        return longValuesArray;
    }

    public void setLongValuesArray(long[] updatedArray) {
        longValuesArray = updatedArray;
    }

    public String[] getStringValuesArray() {
        return stringValuesArray;
    }

    public void setStringValuesArray(String[] updatedArray) {
        stringValuesArray = updatedArray;
    }

    public Context getContext() { return messageContext; }

    public <T> T convert(Class<T> clazz) throws Exception {
        return clazz.getConstructor(long[].class).newInstance(getLongValuesArray());
    }

    @Override
    public String toString() {
        return new String(byteValuesArray);
    }
}
