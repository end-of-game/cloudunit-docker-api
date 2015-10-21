package fr.treeptik.cloudunit.docker.core;

import fr.treeptik.cloudunit.docker.model.Container;
import fr.treeptik.cloudunit.dto.DockerResponse;
import fr.treeptik.cloudunit.exception.FatalDockerJSONException;

/**
 * Created by guillaume on 21/10/15.
 */
public interface DockerDriver {

    DockerResponse find(Container container, String hostIp) throws FatalDockerJSONException;

    DockerResponse findAll(String hostIp) throws FatalDockerJSONException;

    DockerResponse create(Container container, String hostIp) throws FatalDockerJSONException;

    DockerResponse start(Container container, String hostIp) throws FatalDockerJSONException;

    DockerResponse stop(Container container, String hostIp) throws FatalDockerJSONException;

    DockerResponse remove(Container container, String hostIp) throws FatalDockerJSONException;

}
