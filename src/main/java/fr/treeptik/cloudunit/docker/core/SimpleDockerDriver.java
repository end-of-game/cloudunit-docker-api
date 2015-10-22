/*
 * Copyright (c) 2015
 *
 * LICENCE : CloudUnit is available under the GNU Affero General Public License : https://gnu.org/licenses/agpl.html
 * but CloudUnit is licensed too under a standard commercial license.
 * Please contact our sales team if you would like to discuss the specifics of our Enterprise license.
 * If you are not sure whether the AGPL is right for you,
 * you can always test our software under the AGPL and inspect the source code before you contact us
 * about purchasing a commercial license.
 *
 * LEGAL TERMS : CloudUnit is a registered trademark of Treeptik and cannot be used to endorse
 * or promote products derived from this project without prior written permission from Treeptik.
 * Products or services derived from this software may not be called "CloudUnit"
 * nor may "Treeptik" or similar confusing terms appear in their names without prior written permission.
 * For any questions, contact us : contact@treeptik.fr
 */

package fr.treeptik.cloudunit.docker.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.treeptik.cloudunit.docker.model.Container;
import fr.treeptik.cloudunit.dto.DockerResponse;
import fr.treeptik.cloudunit.exception.FatalDockerJSONException;
import fr.treeptik.cloudunit.exception.JSONClientException;
import fr.treeptik.cloudunit.utils.JSONClient;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class SimpleDockerDriver implements DockerDriver {

    private static Logger logger = LoggerFactory.getLogger(SimpleDockerDriver.class);

    private JSONClient client = new JSONClient();

    private ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public DockerResponse find(Container container, String host) throws FatalDockerJSONException {
        URI uri = null;
        String body = new String();
        DockerResponse dockerResponse = null;
        try {
            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost(host)
                    .setPath("/containers/" + container.getName() + "/json")
                    .build();
            dockerResponse = client.sendGet(uri);
        } catch (URISyntaxException | JSONClientException e) {
            StringBuilder contextError = new StringBuilder(256);
            contextError.append("uri : " + uri + " - ");
            contextError.append("request body : " + body + " - ");
            contextError.append("server response : " + dockerResponse);
            logger.error(contextError.toString());
            throw new FatalDockerJSONException("An error has occurred for find a container request due to " + e.getMessage(), e);
        }

        return dockerResponse;
    }

    @Override
    public DockerResponse findAll(String host) throws FatalDockerJSONException {
        URI uri = null;
        String body = new String();
        DockerResponse dockerResponse = null;
        try {
            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost(host)
                    .setPath("/containers/json")
                    .build();
            dockerResponse = client.sendGet(uri);
        } catch (URISyntaxException | JSONClientException e) {
            StringBuilder contextError = new StringBuilder(256);
            contextError.append("uri : " + uri + " - ");
            contextError.append("request body : " + body + " - ");
            contextError.append("server response : " + dockerResponse);
            logger.error(contextError.toString());
            throw new FatalDockerJSONException("An error has occurred for find all containers request due to " + e.getMessage(), e);
        }

        return dockerResponse;
    }

    @Override
    public DockerResponse create(Container container, String host) throws FatalDockerJSONException {
        URI uri = null;
        String body = new String();
        DockerResponse dockerResponse = null;
        try {
            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost(host)
                    .setPath("/containers/create")
                    .setParameter("name", container.getName())
                    .build();
            body = objectMapper.writeValueAsString(container.getConfig());
            dockerResponse = client.sendPost(uri, body, "application/json");
        } catch (URISyntaxException | IOException | JSONClientException e) {
            StringBuilder contextError = new StringBuilder(256);
            contextError.append("uri : " + uri + " - ");
            contextError.append("request body : " + body + " - ");
            contextError.append("server response : " + dockerResponse);
            logger.error(contextError.toString());
            throw new FatalDockerJSONException("An error has occurred for create container request due to " + e.getMessage(), e);
        }
        return dockerResponse;
    }

    @Override
    public DockerResponse start(Container container, String host) throws FatalDockerJSONException {
        URI uri = null;
        String body = new String();
        DockerResponse dockerResponse = null;
        try {
            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost(host)
                    .setPath(
                            "/containers/" + container.getName()
                                    + "/start")
                    .build();
            body = objectMapper.writeValueAsString(container.getConfig().getHostConfig());
            dockerResponse = client.sendPost(uri,
                    body, "application/json");
        } catch (URISyntaxException | IOException | JSONClientException e) {
            StringBuilder contextError = new StringBuilder(256);
            contextError.append("uri : " + uri + " - ");
            contextError.append("request body : " + body + " - ");
            contextError.append("server response : " + dockerResponse);
            logger.error(contextError.toString());
            throw new FatalDockerJSONException("An error has occurred for start container request due to " + e.getMessage(), e);
        }
        return dockerResponse;
    }

    @Override
    public DockerResponse stop(Container container, String host) throws FatalDockerJSONException {
        URI uri = null;
        String body = new String();
        DockerResponse dockerResponse = null;
        try {
            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost(host)
                    .setPath(
                            "/containers/" + container.getName()
                                    + "/stop")
                    .build();
            dockerResponse = client.sendPost(uri,
                    body, "application/json");
        } catch (URISyntaxException | JSONClientException e) {
            StringBuilder contextError = new StringBuilder(256);
            contextError.append("uri : " + uri + " - ");
            contextError.append("request body : " + body + " - ");
            contextError.append("server response : " + dockerResponse);
            logger.error(contextError.toString());
            throw new FatalDockerJSONException("An error has occurred for stop container request due to " + e.getMessage(), e);
        }
        return dockerResponse;
    }

    @Override
    public DockerResponse kill(Container container, String host) throws FatalDockerJSONException {
        URI uri = null;
        String body = new String();
        DockerResponse dockerResponse = null;
        try {
            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost(host)
                    .setPath("/containers/" + container.getName() + "/kill")
                    .build();
            dockerResponse = client.sendPost(uri, "", "application/json");
        } catch (URISyntaxException | JSONClientException e) {
            StringBuilder contextError = new StringBuilder(256);
            contextError.append("uri : " + uri + " - ");
            contextError.append("request body : " + body + " - ");
            contextError.append("server response : " + dockerResponse);
            logger.error(contextError.toString());
            throw new FatalDockerJSONException("An error has occurred for kill container request due to " + e.getMessage(), e);
        }
        return dockerResponse;
    }

    @Override
    public DockerResponse remove(Container container, String host) throws FatalDockerJSONException {
        URI uri = null;
        String body = new String();
        DockerResponse dockerResponse = null;
        try {
            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost(host)
                    .setPath("/containers/" + container.getName())
                    .setParameter("v", "1")
                    .setParameter("force",
                            "true")
                    .build();
            dockerResponse = client.sendDelete(uri);
        } catch (URISyntaxException | JSONClientException e) {
            StringBuilder contextError = new StringBuilder(256);
            contextError.append("uri : " + uri + " - ");
            contextError.append("request body : " + body + " - ");
            contextError.append("server response : " + dockerResponse);
            logger.error(contextError.toString());
            throw new FatalDockerJSONException("An error has occurred for remove request due to " + e.getMessage(), e);
        }
        return dockerResponse;
    }

    @Override
    public DockerResponse commit(Container container, String host, String tag, String repository) throws FatalDockerJSONException {
        URI uri = null;
        String body = new String();
        DockerResponse dockerResponse = null;
        try {
            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost(host)
                    .setPath("/commit")
                    .setParameter("container", container.getName())
                    .setParameter("tag", tag)
                    .setParameter("repo", repository + tag)
                    .build();
            dockerResponse = client.sendPost(uri, "", "application/json");
        } catch (URISyntaxException | JSONClientException e) {
            StringBuilder contextError = new StringBuilder(256);
            contextError.append("uri : " + uri + " - ");
            contextError.append("request body : " + body + " - ");
            contextError.append("server response : " + dockerResponse);
            logger.error(contextError.toString());
            throw new FatalDockerJSONException("An error has occurred for commit request due to " + e.getMessage(), e);
        }
        return dockerResponse;
    }

    @Override
    public DockerResponse push(String host, String tag, String repository) throws FatalDockerJSONException {
        URI uri = null;
        String body = new String();
        DockerResponse dockerResponse = null;
        try {
            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost(host)
                    .setPath("/images/" + repository + tag + "/push")
                    .setParameter("tag", tag.toLowerCase()).build();
            dockerResponse = client.sendPostToRegistryHost(uri, "",
                    "application/json");
            dockerResponse = client.sendPost(uri, "", "application/json");
        } catch (URISyntaxException | JSONClientException e) {
            StringBuilder contextError = new StringBuilder(256);
            contextError.append("uri : " + uri + " - ");
            contextError.append("request body : " + body + " - ");
            contextError.append("server response : " + dockerResponse);
            logger.error(contextError.toString());
            throw new FatalDockerJSONException("An error has occurred for push request due to " + e.getMessage(), e);
        }
        return dockerResponse;
    }

    @Override
    public DockerResponse pull(String host, String tag, String repository) throws FatalDockerJSONException {
        URI uri = null;
        String body = new String();
        DockerResponse dockerResponse = null;
        try {
            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost(host)
                    .setPath("/images/create")
                    .setParameter("fromImage", repository + tag)
                    .setParameter("tag", tag.toLowerCase())
                    .build();
            dockerResponse = client.sendPostToRegistryHost(uri, "",
                    "application/json");
            dockerResponse = client.sendPost(uri, "", "application/json");
        } catch (URISyntaxException | JSONClientException e) {
            StringBuilder contextError = new StringBuilder(256);
            contextError.append("uri : " + uri + " - ");
            contextError.append("request body : " + body + " - ");
            contextError.append("server response : " + dockerResponse);
            logger.error(contextError.toString());
            throw new FatalDockerJSONException("An error has occurred for pull request due to " + e.getMessage(), e);
        }
        return dockerResponse;
    }

    @Override
    public DockerResponse removeImage(String host, String tag, String repository)
            throws FatalDockerJSONException {

        URI uri = null;
        String body = new String();
        DockerResponse dockerResponse = null;
        try {
            uri = new URIBuilder().setScheme("http").setHost(host)
                    .setPath("/images/" + repository + tag).build();
            dockerResponse = client.sendDelete(uri);
        } catch (URISyntaxException | JSONClientException e) {
            StringBuilder contextError = new StringBuilder(256);
            contextError.append("uri : " + uri + " - ");
            contextError.append("request body : " + body + " - ");
            contextError.append("server response : " + dockerResponse);
            logger.error(contextError.toString());
            throw new FatalDockerJSONException("An error has occurred for removeImage request due to " + e.getMessage(), e);
        }
        return dockerResponse;
    }

    @Override
    public DockerResponse removeImageIntoRepository(String host, String tag, String repository)
            throws FatalDockerJSONException {
        URI uri = null;
        String body = new String();
        DockerResponse dockerResponse = null;
        try {
            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost(repository)
                    .setPath(
                            "/v1/repositories/" + repository + "/tags/"
                                    + tag.toLowerCase()).build();
            dockerResponse = client.sendDelete(uri);
        } catch (URISyntaxException | JSONClientException e) {
            StringBuilder contextError = new StringBuilder(256);
            contextError.append("uri : " + uri + " - ");
            contextError.append("request body : " + body + " - ");
            contextError.append("server response : " + dockerResponse);
            logger.error(contextError.toString());
            throw new FatalDockerJSONException("An error has occurred for removeImage request due to " + e.getMessage(), e);
        }
        return dockerResponse;
    }

}
