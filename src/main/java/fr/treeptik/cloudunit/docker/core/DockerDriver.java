package fr.treeptik.cloudunit.docker.core;

import fr.treeptik.cloudunit.docker.model.Container;
import fr.treeptik.cloudunit.dto.DockerResponse;
import fr.treeptik.cloudunit.exception.FatalDockerJSONException;

/**
 * Created by guillaume on 21/10/15.
 */
public interface DockerDriver {
    /*
    Usual methods to manipulate containers
     */

    DockerResponse find(Container container, String host) throws FatalDockerJSONException;

    DockerResponse findAll(String host) throws FatalDockerJSONException;

    DockerResponse create(Container container, String host) throws FatalDockerJSONException;

    DockerResponse start(Container container, String host) throws FatalDockerJSONException;

    DockerResponse stop(Container container, String host) throws FatalDockerJSONException;

    DockerResponse kill(Container container, String host) throws FatalDockerJSONException;

    DockerResponse remove(Container container, String host) throws FatalDockerJSONException;

    /*
    Usual methods to manipulate images and registry
     */

    DockerResponse commit(Container container, String host, String tag, String repository)
            throws FatalDockerJSONException;

    DockerResponse push(String host, String tag, String repository)
            throws FatalDockerJSONException;

    DockerResponse pull(String host, String tag, String repository)
            throws FatalDockerJSONException;

    DockerResponse removeImage(String host, String tag, String repository)
            throws FatalDockerJSONException;

    DockerResponse removeImageIntoRepository(String host, String tag, String repository)
            throws FatalDockerJSONException;

}
