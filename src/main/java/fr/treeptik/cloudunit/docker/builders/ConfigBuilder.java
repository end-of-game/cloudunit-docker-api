package fr.treeptik.cloudunit.docker.builders;

import fr.treeptik.cloudunit.docker.model.Config;
import fr.treeptik.cloudunit.docker.model.HostConfig;

import java.util.List;
import java.util.Map;

/**
 * Created by guillaume on 21/10/15.
 */
public class ConfigBuilder {
    private Boolean attachStdin;
    private Boolean attachStdout;
    private Boolean attachStderr;
    private Long memory;
    private Long memorySwap;
    private String image;
    private List<String> cmd;
    private Map<String, String> exposedPorts;
    private HostConfig hostConfig;

    private ConfigBuilder() {
    }

    public static ConfigBuilder aConfig() {
        return new ConfigBuilder();
    }

    public ConfigBuilder withAttachStdin(Boolean attachStdin) {
        this.attachStdin = attachStdin;
        return this;
    }

    public ConfigBuilder withAttachStdout(Boolean attachStdout) {
        this.attachStdout = attachStdout;
        return this;
    }

    public ConfigBuilder withAttachStderr(Boolean attachStderr) {
        this.attachStderr = attachStderr;
        return this;
    }

    public ConfigBuilder withMemory(Long memory) {
        this.memory = memory;
        return this;
    }

    public ConfigBuilder withMemorySwap(Long memorySwap) {
        this.memorySwap = memorySwap;
        return this;
    }

    public ConfigBuilder withImage(String image) {
        this.image = image;
        return this;
    }

    public ConfigBuilder withCmd(List<String> cmd) {
        this.cmd = cmd;
        return this;
    }

    public ConfigBuilder withExposedPorts(Map<String, String> exposedPorts) {
        this.exposedPorts = exposedPorts;
        return this;
    }

    public ConfigBuilder withHostConfig(HostConfig hostConfig) {
        this.hostConfig = hostConfig;
        return this;
    }

    public ConfigBuilder but() {
        return aConfig().withAttachStdin(attachStdin).withAttachStdout(attachStdout).withAttachStderr(attachStderr).withMemory(memory).withMemorySwap(memorySwap).withImage(image).withCmd(cmd).withExposedPorts(exposedPorts).withHostConfig(hostConfig);
    }

    public Config build() {
        Config config = new Config();
        config.setAttachStdin(attachStdin);
        config.setAttachStdout(attachStdout);
        config.setAttachStderr(attachStderr);
        config.setMemory(memory);
        config.setMemorySwap(memorySwap);
        config.setImage(image);
        config.setCmd(cmd);
        config.setExposedPorts(exposedPorts);
        config.setHostConfig(hostConfig);
        return config;
    }
}
