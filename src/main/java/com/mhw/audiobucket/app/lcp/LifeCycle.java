package com.mhw.audiobucket.app.lcp;

/**
 * Created by michaelwomack on 2/18/17.
 */
public enum LifeCycle {

    DEV("dev"), QA("qa"), PR("prod");

    private String configName;

    LifeCycle(String propertiesPath) {
        this.configName = propertiesPath;
    }


    public String getConfigName() {
        return configName;
    }
}
