package com.mhw.audiobucket.util;

import com.google.common.io.CharStreams;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Created by michaelwomack on 1/9/17.
 */
public class Util {

    public static String readResouce(String resource) throws IOException {
        InputStream in = getResourceAsStream(resource);
        return CharStreams.toString(new InputStreamReader(in));
    }

    public static InputStream getResourceAsStream(String resource) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream in = classLoader.getResourceAsStream(resource);
        return in;
    }

    public static void main(String[] args) {
        System.out.println(org.mindrot.jbcrypt.BCrypt.checkpw("password", "$2a$10$vAD8RNWZo3yknizR24pET.Fwyd51tkHjP8i/A1ECvvwVe10802lkq"));
    }
}

