package fr.treeptik.cloudunit.docker.core;

import fr.treeptik.cloudunit.docker.model.Container;
import fr.treeptik.cloudunit.dto.DockerResponse;
import fr.treeptik.cloudunit.exception.FatalDockerJSONException;

/**
 * Created by guillaume on 21/10/15.
 */
public interface Driver {

    DockerResponse create(Container container, String hostIp) throws FatalDockerJSONException;
}
