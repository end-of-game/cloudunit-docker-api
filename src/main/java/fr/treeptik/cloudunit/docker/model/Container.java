package fr.treeptik.cloudunit.docker.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by guillaume on 21/10/15.
 */
public class Container implements Serializable {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Config")
    private Config config;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

}
