package fr.treeptik.cloudunit.docker.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.treeptik.cloudunit.docker.model.Container;
import fr.treeptik.cloudunit.dto.DockerResponse;
import fr.treeptik.cloudunit.exception.DockerJSONException;
import fr.treeptik.cloudunit.exception.ErrorDockerJSONException;
import fr.treeptik.cloudunit.exception.FatalDockerJSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by guillaume on 21/10/15.
 */
public class DockerClient {

    private Logger logger = LoggerFactory.getLogger(DockerClient.class);

    private DockerDriver driver = new SimpleDockerDriver();

    private ObjectMapper objectMapper = new ObjectMapper();

    public Container findContainer(Container container, String hostIp) throws DockerJSONException {
        try {
            DockerResponse dockerResponse = driver.find(container, hostIp);
            logger.debug(dockerResponse.getBody());
            handleDockerAPIError(dockerResponse);
            container = objectMapper.readValue(dockerResponse.getBody(), Container.class);
        } catch (FatalDockerJSONException | IOException e) {
            throw new DockerJSONException(e.getMessage(), e);
        }
        return container;
    }


    public void createContainer(Container container, String hostIp) throws DockerJSONException {
        try {
            DockerResponse dockerResponse = driver.create(container, hostIp);
            handleDockerAPIError(dockerResponse);
        } catch (FatalDockerJSONException e) {
            throw new DockerJSONException(e.getMessage(), e);
        }
    }

    public void startContainer(Container container, String hostIp) throws DockerJSONException {
        try {
            DockerResponse dockerResponse = driver.start(container, hostIp);
            handleDockerAPIError(dockerResponse);
        } catch (FatalDockerJSONException e) {
            throw new DockerJSONException(e.getMessage(), e);
        }
    }

    public void stopContainer(Container container, String hostIp) throws DockerJSONException {
        try {
            DockerResponse dockerResponse = driver.stop(container, hostIp);
            handleDockerAPIError(dockerResponse);
        } catch (FatalDockerJSONException e) {
            throw new DockerJSONException(e.getMessage(), e);
        }
    }

    private void handleDockerAPIError(DockerResponse dockerResponse) throws DockerJSONException {
        switch (dockerResponse.getStatus()) {
            case 101:
                logger.info("No error : hints proxy about hijacking");
                break;
            case 200:
                logger.info("Status OK");
                break;
            case 201:
                logger.info("Status OK");
                break;
            case 304:
                logger.error("Docker API answers with a 304 error code : " + dockerResponse.getBody());
                throw new ErrorDockerJSONException("Docker API answers with a 304 error code : " +
                        dockerResponse.getBody());
            case 400:
                logger.error("Docker API answers with a 400 error code : " + dockerResponse.getBody());
                throw new ErrorDockerJSONException("Docker API answers with a 400 error code : " +
                        dockerResponse.getBody());
            case 404:
                logger.error("Docker API answers with a 404 error code : " + dockerResponse.getBody());
                throw new ErrorDockerJSONException("Docker API answers with a 404 error code : " +
                        dockerResponse.getBody());
            case 406:
                logger.error("Docker API answers with a 406 error code : " + dockerResponse.getBody());
                throw new ErrorDockerJSONException("Docker API answers with a 406 error code : " +
                        dockerResponse.getBody());
            case 409:
                logger.error("Docker API answers with a 409 error code : " + dockerResponse.getBody());
                throw new ErrorDockerJSONException("Docker API answers with a 409 error code : " +
                        dockerResponse.getBody());
            case 500:
                logger.error("Docker API answers with a 500 error code : " + dockerResponse.getBody());
                throw new ErrorDockerJSONException("Docker API answers with a 500 error code : " +
                        dockerResponse.getBody());
        }
    }
}
