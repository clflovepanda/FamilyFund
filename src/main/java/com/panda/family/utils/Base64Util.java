package com.panda.family.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

public class Base64Util {

    public static String encode(String str) {
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String result = base64Encoder.encode(str.getBytes());
        return result;
    }

    public static String decode(String str) throws IOException {
        BASE64Decoder base64Decoder = new BASE64Decoder();
        byte[] result = base64Decoder.decodeBuffer(str);
        return new String(result);
    }
}
