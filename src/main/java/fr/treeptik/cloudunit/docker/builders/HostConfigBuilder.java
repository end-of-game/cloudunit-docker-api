package fr.treeptik.cloudunit.docker.builders;

import fr.treeptik.cloudunit.docker.model.HostConfig;

import java.util.List;
import java.util.Map;

/**
 * Created by guillaume on 21/10/15.
 */
public class HostConfigBuilder {
    private Boolean privileged;
    private Boolean publishAllPorts;
    private List<String> links;
    private List<String> binds;
    private Map<String, String> portBindings;
    private List<String> volumesFrom;

    private HostConfigBuilder() {
    }

    public static HostConfigBuilder aHostConfig() {
        return new HostConfigBuilder();
    }

    public HostConfigBuilder withPrivileged(Boolean privileged) {
        this.privileged = privileged;
        return this;
    }

    public HostConfigBuilder withPublishAllPorts(Boolean publishAllPorts) {
        this.publishAllPorts = publishAllPorts;
        return this;
    }

    public HostConfigBuilder withLinks(List<String> links) {
        this.links = links;
        return this;
    }

    public HostConfigBuilder withBinds(List<String> binds) {
        this.binds = binds;
        return this;
    }

    public HostConfigBuilder withPortBindings(Map<String, String> portBindings) {
        this.portBindings = portBindings;
        return this;
    }

    public HostConfigBuilder withVolumesFrom(List<String> volumesFrom) {
        this.volumesFrom = volumesFrom;
        return this;
    }

    public HostConfigBuilder but() {
        return aHostConfig().withPrivileged(privileged).withPublishAllPorts(publishAllPorts).withLinks(links).withBinds(binds).withPortBindings(portBindings).withVolumesFrom(volumesFrom);
    }

    public HostConfig build() {
        HostConfig hostConfig = new HostConfig();
        hostConfig.setPrivileged(privileged);
        hostConfig.setPublishAllPorts(publishAllPorts);
        hostConfig.setLinks(links);
        hostConfig.setBinds(binds);
        hostConfig.setPortBindings(portBindings);
        hostConfig.setVolumesFrom(volumesFrom);
        return hostConfig;
    }
}
