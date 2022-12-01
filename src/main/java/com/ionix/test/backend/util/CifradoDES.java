package com.ionix.test.backend.util;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


public class CifradoDES {

    public static String encode(String parametro,String key) throws UnsupportedEncodingException,
            IllegalBlockSizeException,
            BadPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            NoSuchPaddingException {

        DESKeySpec keySpec = new DESKeySpec(key.getBytes("UTF8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        byte[] cleartext = parametro.getBytes("UTF8");
        Cipher cipher = Cipher.getInstance("DES");
        return Base64.getEncoder().encodeToString(cipher.doFinal(cleartext));

    }
}
