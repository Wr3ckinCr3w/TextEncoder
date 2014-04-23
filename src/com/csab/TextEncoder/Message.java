package com.csab.TextEncoder;

public class Message {

    private byte[] byteValuesArray;
    private long[] longValuesArray;
    private String[] stringValuesArray;

    public Message() { }

    public Message(byte[] inputArray) {
        byteValuesArray = inputArray;
        longValuesArray = new long[inputArray.length];
        for (int i = 0; i < longValuesArray.length; i++) {
            longValuesArray[i] = (long)inputArray[i];
        }
    }

    public Message(String inputString) {
        stringValuesArray = inputString.split("\\s+");
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

    @Override
    public String toString() {
        return new String(byteValuesArray);
    }
}
