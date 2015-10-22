package fr.treeptik.cloudunit.docker.core;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.treeptik.cloudunit.docker.model.Container;
import fr.treeptik.cloudunit.docker.model.Image;
import fr.treeptik.cloudunit.dto.DockerResponse;
import fr.treeptik.cloudunit.exception.DockerJSONException;
import fr.treeptik.cloudunit.exception.ErrorDockerJSONException;
import fr.treeptik.cloudunit.exception.FatalDockerJSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by guillaume on 21/10/15.
 */
public class DockerClient {

    private Logger logger = LoggerFactory.getLogger(DockerClient.class);

    private DockerDriver driver = new SimpleDockerDriver();

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * @param container
     * @param host
     * @return
     * @throws DockerJSONException
     */
    public Container findContainer(Container container, String host) throws DockerJSONException {
        try {
            DockerResponse dockerResponse = driver.find(container, host);
            logger.debug(dockerResponse.getBody());
            handleDockerAPIError(dockerResponse);
            container = objectMapper.readValue(dockerResponse.getBody(), Container.class);
        } catch (FatalDockerJSONException | IOException e) {
            throw new DockerJSONException(e.getMessage(), e);
        }
        return container;
    }

    /**
     * @param host
     * @return
     * @throws DockerJSONException
     */
    public List<Container> findAllContainers(String host) throws DockerJSONException {
        List<Container> containers = null;
        try {
            DockerResponse dockerResponse = driver.findAll(host);
            logger.debug(dockerResponse.getBody());
            handleDockerAPIError(dockerResponse);
            containers = objectMapper.readValue(dockerResponse.getBody(),
                    new TypeReference<List<Container>>() {
                    });
        } catch (FatalDockerJSONException | IOException e) {
            throw new DockerJSONException(e.getMessage(), e);
        }
        return containers;
    }

    /**
     * @param container
     * @param host
     * @throws DockerJSONException
     */
    public void createContainer(Container container, String host) throws DockerJSONException {
        try {
            DockerResponse dockerResponse = driver.create(container, host);
            handleDockerAPIError(dockerResponse);
        } catch (FatalDockerJSONException e) {
            throw new DockerJSONException(e.getMessage(), e);
        }
    }

    /**
     * @param container
     * @param host
     * @throws DockerJSONException
     */
    public void startContainer(Container container, String host) throws DockerJSONException {
        try {
            DockerResponse dockerResponse = driver.start(container, host);
            handleDockerAPIError(dockerResponse);
        } catch (FatalDockerJSONException e) {
            throw new DockerJSONException(e.getMessage(), e);
        }
    }

    /**
     * @param container
     * @param host
     * @throws DockerJSONException
     */
    public void stopContainer(Container container, String host) throws DockerJSONException {
        try {
            DockerResponse dockerResponse = driver.stop(container, host);
            handleDockerAPIError(dockerResponse);
        } catch (FatalDockerJSONException e) {
            throw new DockerJSONException(e.getMessage(), e);
        }
    }

    public DockerResponse killContainer(Container container, String host) throws DockerJSONException {
        DockerResponse dockerResponse = null;
        try {
            dockerResponse = driver.kill(container, host);
            handleDockerAPIError(dockerResponse);
        } catch (FatalDockerJSONException e) {
            throw new DockerJSONException(e.getMessage(), e);
        }
        return dockerResponse;
    }

    /**
     * @param container
     * @param host
     * @return
     * @throws DockerJSONException
     */
    public DockerResponse removeContainer(Container container, String host) throws DockerJSONException {
        DockerResponse dockerResponse = null;
        try {
            dockerResponse = driver.remove(container, host);
            handleDockerAPIError(dockerResponse);
        } catch (FatalDockerJSONException e) {
            throw new DockerJSONException(e.getMessage(), e);
        }
        return dockerResponse;
    }

    /**
     * @param container
     * @param host
     * @param tag
     * @param repository
     * @return
     * @throws DockerJSONException
     */
    public DockerResponse commitImage(Container container, String host, String tag, String repository) throws DockerJSONException {
        DockerResponse dockerResponse = null;
        try {
            dockerResponse = driver.commit(container, host, tag, repository);
            handleDockerAPIError(dockerResponse);
        } catch (FatalDockerJSONException e) {
            throw new DockerJSONException(e.getMessage(), e);
        }
        return dockerResponse;
    }

    /**
     * @param host
     * @param tag
     * @param repository
     * @return
     * @throws DockerJSONException
     */
    public DockerResponse pushImage(String host, String tag, String repository) throws DockerJSONException {
        DockerResponse dockerResponse = null;
        try {
            dockerResponse = driver.push(host, tag, repository);
            handleDockerAPIError(dockerResponse);
        } catch (FatalDockerJSONException e) {
            throw new DockerJSONException(e.getMessage(), e);
        }
        return dockerResponse;
    }

    /**
     * @param host
     * @param tag
     * @param repository
     * @return
     * @throws DockerJSONException
     */
    public DockerResponse pullImage(String host, String tag, String repository) throws DockerJSONException {
        DockerResponse dockerResponse = null;
        try {
            dockerResponse = driver.pull(host, tag, repository);
            handleDockerAPIError(dockerResponse);
        } catch (FatalDockerJSONException e) {
            throw new DockerJSONException(e.getMessage(), e);
        }
        return dockerResponse;
    }

    /**
     * @param image
     * @param host
     * @return
     * @throws DockerJSONException
     */
    public Image findAnImage(Image image, String host) throws DockerJSONException {
        DockerResponse dockerResponse = null;

        try {
            dockerResponse = driver.findAnImage(image, host);
            handleDockerAPIError(dockerResponse);
            image = objectMapper.readValue(dockerResponse.getBody(), Image.class);
        } catch (FatalDockerJSONException | IOException e) {
            throw new DockerJSONException(e.getMessage(), e);
        }
        return image;
    }

    /**
     * @param image
     * @param host
     * @return
     * @throws DockerJSONException
     */
    public DockerResponse removeImage(Image image, String host) throws DockerJSONException {
        DockerResponse dockerResponse = null;
        try {
            dockerResponse = driver.removeImage(image, host);
            handleDockerAPIError(dockerResponse);
        } catch (FatalDockerJSONException e) {
            throw new DockerJSONException(e.getMessage(), e);
        }
        return dockerResponse;
    }

    /**
     * @param image
     * @param host
     * @param registryHost
     * @return
     * @throws DockerJSONException
     */
    public DockerResponse removeImageIntoTheRegistry(Image image, String host, String registryHost) throws DockerJSONException {
        DockerResponse dockerResponse = null;
        try {
            dockerResponse = driver.removeImageIntoRepository(image, host, registryHost);
            handleDockerAPIError(dockerResponse);
        } catch (FatalDockerJSONException e) {
            throw new DockerJSONException(e.getMessage(), e);
        }
        return dockerResponse;
    }

    /**
     * @param dockerResponse
     * @throws DockerJSONException
     */
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
            case 204:
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
