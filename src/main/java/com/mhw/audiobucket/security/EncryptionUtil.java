package com.mhw.audiobucket.security;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

/**
 * Created by mxw4182 on 12/28/16.
 */
public class EncryptionUtil {

    public static String sha256(String str) throws NoSuchAlgorithmException {
        return Hashing.sha256().hashString(str, StandardCharsets.UTF_8).toString();
    }
}
