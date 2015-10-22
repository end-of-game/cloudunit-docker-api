package fr.treeptik.cloudunit.docker.core;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 * Created by guillaume on 22/10/15.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ImageCommandTests {

    private static DockerClient dockerClient;
    private static final String DOCKER_HOST = "192.168.50.4:4243";
    private static final String CONTAINER_NAME = "myContainer";
    private static final int RUNNING_CONTAINERS = 8;

    @BeforeClass
    public static void setup() {
        dockerClient = new DockerClient();
    }




}
