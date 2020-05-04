package com.brandeis.cosi132a.finalproject.utils;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.util.Arrays;
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

    public static void main(String[] args) {
        double[] arr = new double[]{
                0.13175000250339508,
                -0.25516998767852783,
                -0.06791500002145767,
                0.261929988861084,
                -0.26155000925064087,
                0.23568999767303467,
                0.1307699978351593,
                -0.011800999753177166,
                1.7659000158309937,
                0.20780999958515167,
                0.26197999715805054,
                -0.1642799973487854,
                -0.8464199900627136,
                0.020093999803066254,
                0.07017599791288376,
                0.39778000116348267,
                0.15277999639511108,
                -0.2021300047636032,
                -1.618399977684021,
                -0.5432699918746948,
                -0.17856000363826752,
                0.5389400124549866,
                0.4986799955368042,
                -0.1017099991440773,
                0.6626499891281128,
                -1.7051000595092773,
                0.05719299986958504,
                -0.32405000925064087,
                -0.6683499813079834,
                0.266539990901947,
                2.8420000076293945,
                0.26844000816345215,
                -0.5953699946403503,
                -0.5004000067710876,
                1.5198999643325806,
                0.03964100033044815,
                1.6658999919891357,
                0.9975799918174744,
                -0.5597000122070312,
                -0.7049300074577332,
                -0.030899999663233757,
                -0.2830199897289276,
                -0.1356399953365326,
                0.6428999900817871,
                0.4149099886417389,
                1.236199975013733,
                0.7658699750900269,
                0.9779800176620483,
                0.585070013999939,
                -0.3017599880695343
        };
        System.out.println(convertArrayToBase64(arr));
        System.out.println(Arrays.toString(convertBase64ToArray(urlBase64Decode(urlBase64Encode(convertArrayToBase64(arr))))));
    }
}
