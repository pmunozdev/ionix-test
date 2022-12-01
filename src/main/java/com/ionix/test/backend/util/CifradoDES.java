package com.ionix.test.backend.util;

import lombok.extern.log4j.Log4j2;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@Log4j2
public class CifradoDES {

    public static String encode(String parametro,String key) throws
            InvalidKeySpecException,
            UnsupportedEncodingException,
            NoSuchAlgorithmException,
            InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {

        DESKeySpec keySpec = new DESKeySpec(key.getBytes("UTF8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        byte[] cleartext = parametro.getBytes("UTF8");
        Cipher cipher = Cipher.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(keySpec);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(cleartext));

    }
}
