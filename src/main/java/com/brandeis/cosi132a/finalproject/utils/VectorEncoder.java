package com.brandeis.cosi132a.finalproject.utils;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.util.Base64;

public class VectorEncoder {
    /**
     * converte a base64 encoded string back to its vector
     * @param base64Str base64 encoding string
     * @return the vector whose base64 encoding is base64str
     */
    public static double[] convertBase64ToArray(String base64Str) {
        final byte[] decode = Base64.getDecoder().decode(base64Str.getBytes());
        final DoubleBuffer doubleBuffer = ByteBuffer.wrap(decode).asDoubleBuffer();
        final double[] dims = new double[doubleBuffer.capacity()];
        doubleBuffer.get(dims);
        return dims;
    }

    /**
     * Convert a double array into base64 encoding
     * @param array input array
     * @return base64 encoding of that array
     */
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

    /**
     * Convert a base64 encoding url into its base64 url encoding
     * @param url input url
     * @return base64-url encoding os input url
     */
    public static String urlBase64Encode(String url) {
        return Base64.getUrlEncoder()
                .encodeToString(url.getBytes());
    }

    /**
     * Convert the base64-url encoding of input back into its base64 encoding
     * @param url input base64-rl encoded url
     * @return its base64 encoding
     */
    public static String urlBase64Decode(String url) {
        byte[] decodedURLBytes = Base64.getUrlDecoder().decode(url);
        return new String(decodedURLBytes);
    }

}
