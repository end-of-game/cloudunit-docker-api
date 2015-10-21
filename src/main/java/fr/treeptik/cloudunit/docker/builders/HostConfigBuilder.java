package fr.treeptik.cloudunit.docker.builders;

import fr.treeptik.cloudunit.docker.model.HostConfig;

import java.util.List;

/**
 * Created by guillaume on 21/10/15.
 */
public class HostConfigBuilder {
    private List<String> volumesFrom;

    private HostConfigBuilder() {
    }

    public static HostConfigBuilder aHostConfig() {
        return new HostConfigBuilder();
    }

    public HostConfigBuilder withVolumesFrom(List<String> volumesFrom) {
        this.volumesFrom = volumesFrom;
        return this;
    }

    public HostConfigBuilder but() {
        return aHostConfig().withVolumesFrom(volumesFrom);
    }

    public HostConfig build() {
        HostConfig hostConfig = new HostConfig();
        hostConfig.setVolumesFrom(volumesFrom);
        return hostConfig;
    }
}
