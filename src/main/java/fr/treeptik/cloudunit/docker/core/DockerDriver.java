package fr.treeptik.cloudunit.docker.core;

import fr.treeptik.cloudunit.docker.model.Container;
import fr.treeptik.cloudunit.docker.model.ExecBody;
import fr.treeptik.cloudunit.docker.model.ExecStartBody;
import fr.treeptik.cloudunit.docker.model.Image;
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

    DockerResponse findAnImage(Image image, String host) throws FatalDockerJSONException;

    DockerResponse commit(Container container, String host, String tag, String repository)
            throws FatalDockerJSONException;

    DockerResponse push(String host, String tag, String repository)
            throws FatalDockerJSONException;

    DockerResponse pull(String host, String tag, String repository)
            throws FatalDockerJSONException;

    DockerResponse removeImage(Image image, String host)
            throws FatalDockerJSONException;

    DockerResponse removeImageIntoRepository(Image image, String host, String registryHost)
            throws FatalDockerJSONException;

    /*
    Advanced methods
     */

    DockerResponse execCreate(Container container, ExecBody execBody, String host)
            throws FatalDockerJSONException;

    DockerResponse execStart(String execId, ExecStartBody execStartBody, String host)
            throws FatalDockerJSONException;

}
