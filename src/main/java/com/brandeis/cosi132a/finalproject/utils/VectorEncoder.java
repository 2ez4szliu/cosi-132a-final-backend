package com.brandeis.cosi132a.finalproject.utils;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.util.Base64;

public class VectorEncoder {
    public static double[] convertBase64ToArray(String base64Str) {
        final byte[] decode = Base64.getDecoder().decode(base64Str.getBytes());
        final DoubleBuffer doubleBuffer = ByteBuffer.wrap(decode).asDoubleBuffer();
        final double[] dims = new double[doubleBuffer.capacity()];
        doubleBuffer.get(dims);
        return dims;
    }

    public static String convertArrayToBase64(double[] array) {
        final int capacity = Double.BYTES * array.length;
        final ByteBuffer bb = ByteBuffer.allocate(capacity);
        for (double v : array) {
            bb.putDouble(v);
        }
        bb.rewind();
        final ByteBuffer encodedBB = Base64.getEncoder().encode(bb);

        return new String(encodedBB.array());
    }

    public static String urlBase64Encode(String url) {
        return Base64.getUrlEncoder()
                .encodeToString(url.getBytes());
    }

    public static String urlBase64Decode(String url) {
        byte[] decodedURLBytes = Base64.getUrlDecoder().decode(url);
        return new String(decodedURLBytes);
    }

}
