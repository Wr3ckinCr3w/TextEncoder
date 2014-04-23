package com.csab.TextEncoder;

import java.util.Arrays;
import android.content.Context;
import org.apache.commons.codec.binary.StringUtils;

public class AsciiMessage extends Message {

    public AsciiMessage(byte[] inputArray) {
        super(inputArray);
    }

    public AsciiMessage(String inputString, Context context) {
        setByteValuesArray(inputString.getBytes());
    }

    public AsciiMessage(long[] inputArray) throws MessageConstructException {
        long[] sortedArray = Arrays.copyOf(inputArray, inputArray.length);
        Arrays.sort(sortedArray);
        if (sortedArray[sortedArray.length - 1] > Byte.MAX_VALUE) {
            // TODO: Get a context to access this message from strings.xml
            throw new MessageConstructException("Value outside ASCII code range (127)");
        } else {
            byte[] byteArray = new byte[inputArray.length];
            for (int i = 0; i < inputArray.length; i++) {
                byteArray[i] = (byte) inputArray[i];
            }
            setByteValuesArray(byteArray);
        }
    }

    public <T> T convert(Class<T> clazz) throws Exception {
        return clazz.getConstructor(byte[].class).newInstance(getByteValuesArray());
    }

    @Override
    public String toString() {
        return StringUtils.newStringUsAscii(getByteValuesArray());
    }
}