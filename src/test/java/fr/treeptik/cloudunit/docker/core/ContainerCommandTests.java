package fr.treeptik.cloudunit.docker.core;

import fr.treeptik.cloudunit.docker.builders.ConfigBuilder;
import fr.treeptik.cloudunit.docker.builders.ContainerBuilder;
import fr.treeptik.cloudunit.docker.builders.HostConfigBuilder;
import fr.treeptik.cloudunit.docker.model.Config;
import fr.treeptik.cloudunit.docker.model.Container;
import fr.treeptik.cloudunit.docker.model.HostConfig;
import fr.treeptik.cloudunit.exception.DockerJSONException;
import org.junit.Assert;
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
public class ContainerCommandTests {

    private static DockerClient dockerClient;
    private static final String DOCKER_HOST = "cloudunit.dev:2376";
    private static final String CONTAINER_NAME = "myContainer";
    private final int RUNNING_CONTAINERS = 7;

    @BeforeClass
    public static void setup() {
        dockerClient = new DockerClient();
        dockerClient.setDriver(new SimpleDockerDriver("/home/guillaume/CloudUnit/cu-vagrant/certificats", true));
    }


    @Test
    public void test00_createContainer() throws DockerJSONException {
        HostConfig hostConfig = HostConfigBuilder.aHostConfig()
                .withVolumesFrom(new ArrayList<>())
                .build();
        Config config = ConfigBuilder.aConfig()
                .withAttachStdin(Boolean.FALSE)
                .withAttachStdout(Boolean.TRUE)
                .withAttachStderr(Boolean.TRUE)
                .withCmd(Arrays.asList("/bin/bash", "/cloudunit/scripts/start-service.sh", "johndoe", "abc2015",
                        "192.168.2.116", "172.17.0.221", "aaaa",
                        "AezohghooNgaegh8ei2jabib2nuj9yoe", "main"))
                .withImage("cloudunit/git:dev")
                .withHostConfig(hostConfig)
                .withExposedPorts(new HashMap<>())
                .withMemory(0L)
                .withMemorySwap(0L)
                .build();
        Container container = ContainerBuilder.aContainer()
                .withName(CONTAINER_NAME)
                .withConfig(config)
                .build();
        dockerClient.createContainer(container, DOCKER_HOST);
        Assert.assertNotNull(dockerClient.findContainer(container, DOCKER_HOST).getId());
    }

    @Test
    public void test01_findContainer() throws DockerJSONException {
        Container container = ContainerBuilder.aContainer()
                .withName("myContainer")
                .build();
        dockerClient.findContainer(container, DOCKER_HOST);

    }

    @Test
    public void test02_startContainer() throws DockerJSONException {
        HostConfig hostConfig = HostConfigBuilder.aHostConfig()
                .withLinks(new ArrayList<>())
                .withBinds(new ArrayList<>())
                .withPortBindings(new HashMap<>())
                .withPrivileged(Boolean.FALSE)
                .withPublishAllPorts(Boolean.TRUE)
                .withVolumesFrom(new ArrayList<>()).build();
        Config config = ConfigBuilder.aConfig()
                .withHostConfig(hostConfig)
                .build();
        Container container = ContainerBuilder.aContainer()
                .withName(CONTAINER_NAME)
                .withConfig(config).build();
        dockerClient.startContainer(container, DOCKER_HOST);
        Assert.assertTrue(dockerClient.findContainer(container, DOCKER_HOST).getState().getRunning());
    }

    @Test
    public void test03_stopContainer() throws DockerJSONException {
        Container container = ContainerBuilder.aContainer()
                .withName(CONTAINER_NAME)
                .build();
        dockerClient.stopContainer(container, DOCKER_HOST);
        Assert.assertFalse(dockerClient.findContainer(container, DOCKER_HOST).getState().getRunning());
    }

    @Test
    public void test04_killContainer() throws DockerJSONException {
        Container container = ContainerBuilder.aContainer()
                .withName(CONTAINER_NAME)
                .build();
        container = dockerClient.findContainer(container, DOCKER_HOST);
        dockerClient.startContainer(container, DOCKER_HOST);
        Assert.assertFalse(dockerClient.findContainer(container, DOCKER_HOST).getState().getRunning());
    }

    @Test
    public void test05_removeContainer() throws DockerJSONException {
        Container container = ContainerBuilder.aContainer()
                .withName(CONTAINER_NAME)
                .build();
        Assert.assertEquals(dockerClient.removeContainer(container, DOCKER_HOST).getStatus(), 204);
    }

    @Test
    public void test06_findAllContainers() throws DockerJSONException {
        Container container = ContainerBuilder.aContainer().
                withName(CONTAINER_NAME)
                .build();
        Assert.assertEquals(RUNNING_CONTAINERS, dockerClient.findAllContainers(DOCKER_HOST).size());
    }


}
