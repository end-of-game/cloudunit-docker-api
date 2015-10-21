package fr.treeptik.cloudunit.docker.core;

import fr.treeptik.cloudunit.docker.builders.ConfigBuilder;
import fr.treeptik.cloudunit.docker.builders.ContainerBuilder;
import fr.treeptik.cloudunit.docker.builders.HostConfigBuilder;
import fr.treeptik.cloudunit.docker.core.DockerClient;
import fr.treeptik.cloudunit.docker.model.Config;
import fr.treeptik.cloudunit.docker.model.Container;
import fr.treeptik.cloudunit.docker.model.HostConfig;
import fr.treeptik.cloudunit.exception.DockerJSONException;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by guillaume on 21/10/15.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreateContainerTest {

    private static DockerClient dockerClient;
    private static final String DOCKER_HOST = "192.168.50.4:4243";

    @BeforeClass
    public static void setup() {
        dockerClient = new DockerClient();

    }

    @Test
    public void test00_createASimpleContainer() throws DockerJSONException {
        HostConfig hostConfig = HostConfigBuilder.aHostConfig().withVolumesFrom(new ArrayList<>()).build();
        Config config = ConfigBuilder.aConfig()
                .withAttachStdin(Boolean.FALSE)
                .withAttachStdout(Boolean.TRUE)
                .withAttachStderr(Boolean.TRUE)
                .withCmd(Arrays.asList("/bin/sh"))
                .withImage("ubuntu")
                .withHostConfig(hostConfig)
                .withExposedPorts(new HashMap<>())
                .withMemory(512000L)
                .withMemorySwap(1L)
                .build();
        Container container = ContainerBuilder.aContainer().withName("myContainer").withConfig(config).build();
        dockerClient.createContainer(container, DOCKER_HOST);
    }
}
