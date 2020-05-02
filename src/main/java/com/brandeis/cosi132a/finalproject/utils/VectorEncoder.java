package com.brandeis.cosi132a.finalproject.utils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.Base64;

public class VectorEncoder {
    public static float[] convertBase64ToArray(String base64Str) {
        final byte[] decode = Base64.getDecoder().decode(base64Str.getBytes());
        final FloatBuffer floatBuffer = ByteBuffer.wrap(decode).asFloatBuffer();
        final float[] dims = new float[floatBuffer.capacity()];
        floatBuffer.get(dims);
        return dims;
    }

    public static String convertArrayToBase64(float[] array) {
        final int capacity = Float.BYTES * array.length;
        final ByteBuffer bb = ByteBuffer.allocate(capacity);
        for (float v : array) {
            bb.putFloat(v);
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

    public static void main(String[] args) {
        float[] decode = convertBase64ToArray("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");
        System.out.println(Arrays.toString(decode));
    }
}
