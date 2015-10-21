package fr.treeptik.cloudunit.docker.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by guillaume on 21/10/15.
 */
public class HostConfig implements Serializable {

    @JsonProperty("VolumesFrom")
    private List<String> volumesFrom;


    public List<String> getVolumesFrom() {
        return volumesFrom;
    }

    public void setVolumesFrom(List<String> volumesFrom) {
        this.volumesFrom = volumesFrom;
    }

}
