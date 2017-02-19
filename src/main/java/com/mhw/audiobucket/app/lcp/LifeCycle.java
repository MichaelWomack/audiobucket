package com.mhw.audiobucket.app.lcp;

/**
 * Created by michaelwomack on 2/18/17.
 */
public enum LifeCycle {

    DEV("dev"), QA("qa"), PR("prod");

    private String propertiesPath;

    LifeCycle(String propertiesPath) {
        this.propertiesPath = propertiesPath;
    }
}
