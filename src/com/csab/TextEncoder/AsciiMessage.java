package com.csab.TextEncoder;

import org.apache.commons.codec.binary.StringUtils;
import java.util.Arrays;

public class AsciiMessage extends Message {

    public AsciiMessage(byte[] inputArray) {
        super(inputArray);
    }

    public AsciiMessage(String inputString) {
        setByteValuesArray(inputString.getBytes());
    }

    public AsciiMessage(long[] inputArray) throws MessageConstructException {
        long[] sortedArray = Arrays.copyOf(inputArray, inputArray.length);
        Arrays.sort(sortedArray);
        if (sortedArray[sortedArray.length - 1] > Byte.MAX_VALUE) {
            throw new MessageConstructException("Value outside ASCII code range (127)");
        } else {
            byte[] byteArray = new byte[inputArray.length];
            for (int i = 0; i < inputArray.length; i++) {
                byteArray[i] = (byte) inputArray[i];
            }
            setByteValuesArray(byteArray);
        }
    }

    public Base64Message toBase64Message()  {
        return new Base64Message(getByteValuesArray());
    }

    public BinaryMessage toBinaryMessage() throws MessageConstructException {
        return new BinaryMessage(getByteValuesArray());
    }

    public DecimalMessage toDecimalMessage() throws MessageConstructException {
        return new DecimalMessage(getByteValuesArray());
    }

    public HexMessage toHexMessage() {
        return new HexMessage(getByteValuesArray());
    }

    public OctalMessage toOctalMessage() {
        return new OctalMessage(getByteValuesArray());
    }

    public <T> T convert(Class<T> clazz) throws Exception {
        return clazz.getConstructor(byte[].class).newInstance(getByteValuesArray());
    }

    @Override
    public String toString() {
        return StringUtils.newStringUsAscii(getByteValuesArray());
    }
}