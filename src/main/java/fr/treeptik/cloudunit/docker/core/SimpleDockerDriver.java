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
    public DockerResponse find(Container container, String hostIp) throws FatalDockerJSONException {
        URI uri = null;
        String body = new String();
        DockerResponse dockerResponse = null;
        try {
            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost(hostIp)
                    .setPath("/containers/" + container.getName() + "/json")
                    .build();
            dockerResponse = client.sendGet(uri);
        } catch (URISyntaxException | JSONClientException e) {
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
    public DockerResponse findAll(String hostIp) throws FatalDockerJSONException {
        URI uri = null;
        String body = new String();
        DockerResponse dockerResponse = null;
        try {
            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost(hostIp)
                    .setPath("/containers/json")
                    .build();
            dockerResponse = client.sendGet(uri);
        } catch (URISyntaxException | JSONClientException e) {
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
    public DockerResponse create(Container container, String hostIp) throws FatalDockerJSONException {
        URI uri = null;
        String body = new String();
        DockerResponse dockerResponse = null;
        try {
            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost(hostIp)
                    .setPath("/containers/create")
                    .setParameter("name", container.getName())
                    .build();
            body = objectMapper.writeValueAsString(container.getConfig());
            logger.debug("body = " + body);
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
    public DockerResponse start(Container container, String hostIp) throws FatalDockerJSONException {
        URI uri = null;
        String body = new String();
        DockerResponse dockerResponse = null;
        try {
            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost(hostIp)
                    .setPath(
                            "/containers/" + container.getName()
                                    + "/start")
                    .build();
            body = objectMapper.writeValueAsString(container.getConfig().getHostConfig());
            logger.debug("body = " + body);
            dockerResponse = client.sendPost(uri,
                    body, "application/json");
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
    public DockerResponse stop(Container container, String hostIp) throws FatalDockerJSONException {
        URI uri = null;
        String body = new String();
        DockerResponse dockerResponse = null;
        try {
            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost(hostIp)
                    .setPath(
                            "/containers/" + container.getName()
                                    + "/stop")
                    .build();
            logger.debug("body = " + body);
            dockerResponse = client.sendPost(uri,
                    body, "application/json");
        } catch (URISyntaxException | JSONClientException e) {
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
    public DockerResponse remove(Container container, String hostIp) throws FatalDockerJSONException {
        URI uri = null;
        String body = new String();
        DockerResponse dockerResponse = null;
        try {
            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost(hostIp)
                    .setPath("/containers/" + container.getName())
                    .setParameter("v", "1")
                    .setParameter("force",
                            "true")
                    .build();
            logger.debug("body = " + body);
            dockerResponse = client.sendDelete(uri);
        } catch (URISyntaxException | JSONClientException e) {
            StringBuilder contextError = new StringBuilder(256);
            contextError.append("uri : " + uri + " - ");
            contextError.append("request body : " + body + " - ");
            contextError.append("server response : " + dockerResponse);
            logger.error(contextError.toString());
            throw new FatalDockerJSONException("An error has occurred for create container request due to " + e.getMessage(), e);
        }
        return dockerResponse;
    }
/*

    public void kill(String name, String hostIp)
            throws DockerJSONException {
        URI uri = null;
        try {
            uri = new URIBuilder().setScheme("http").setHost(hostIp)
                    .setPath("/containers/" + name + "/kill").build();
            int statusCode = client.sendPost(uri, "", "application/json");
            switch (statusCode) {
                case 304:
                    throw new WarningDockerJSONException(
                            "container already stopped");
                case 404:
                    throw new ErrorDockerJSONException("docker : no such container");
                case 500:
                    throw new ErrorDockerJSONException("docker : server error");
            }

        } catch (URISyntaxException | IOException e) {
            StringBuilder msgError = new StringBuilder(256);
            msgError.append(name).append(",hostIP=").append(hostIp)
                    .append(",uri=").append(uri);
            logger.error(msgError.toString(), e);
            throw new FatalDockerJSONException("docker : error fatal");
        }
    }

    public String commit(String name, String tag, String hostIp, String repo)
            throws DockerJSONException {
        URI uri = null;
        Map<String, Object> response = null;
        try {
            uri = new URIBuilder().setScheme("http").setHost(hostIp)
                    .setPath("/commit").setParameter("container", name)
                    .setParameter("tag", tag)
                    .setParameter("repo", "localhost:5000/" + repo + tag)
                    .build();
            response = client
                    .sendPostAndGetImageID(uri, "", "application/json");
            int statusCode = (int) response.get("code");
            switch (statusCode) {
                case 304:
                    throw new WarningDockerJSONException(
                            "container already stopped");
                case 404:
                    throw new ErrorDockerJSONException("docker : no such container");
                case 500:
                    throw new ErrorDockerJSONException("docker : server error");
            }
        } catch (URISyntaxException | WarningDockerJSONException
                | ErrorDockerJSONException | IOException e) {
            StringBuilder msgError = new StringBuilder(256);
            msgError.append(name).append(",hostIP=").append(hostIp)
                    .append(",uri=").append(uri);
            logger.error(msgError.toString(), e);
            throw new FatalDockerJSONException("docker : error fatal");
        }

        return (String) response.get("body");
    }

    public String push(String name, String tag, String hostIp)
            throws DockerJSONException {
        URI uri = null;
        Map<String, Object> response = null;
        try {
            uri = new URIBuilder().setScheme("http").setHost(hostIp)
                    .setPath("/images/" + name.toLowerCase() + "/push")
                    .setParameter("tag", tag.toLowerCase()).build();
            response = client.sendPostWithRegistryHost(uri, "",
                    "application/json");
            int statusCode = (int) response.get("code");

            switch (statusCode) {
                case 304:
                    throw new WarningDockerJSONException(
                            "container already stopped");
                case 404:
                    throw new ErrorDockerJSONException("docker : no such container");
                case 500:
                    throw new ErrorDockerJSONException("docker : server error");
            }
        } catch (URISyntaxException | WarningDockerJSONException
                | ErrorDockerJSONException | IOException e) {
            StringBuilder msgError = new StringBuilder(256);
            msgError.append(name.toLowerCase()).append(",hostIP=")
                    .append(hostIp).append(",uri=").append(uri);
            logger.error(msgError.toString(), e);
            throw new FatalDockerJSONException("docker : error fatal");
        }

        logger.info((String) response.get("body"));

        return (String) response.get("body");

    }

    public String pull(String name, String tag, String hostIp)
            throws DockerJSONException {
        URI uri = null;
        Map<String, Object> response = null;
        try {
            uri = new URIBuilder().setScheme("http").setHost(hostIp)
                    .setPath("/images/create")
                    .setParameter("fromImage", name.toLowerCase())
                    .setParameter("tag", tag.toLowerCase()).build();
            response = client.sendPostWithRegistryHost(uri, "",
                    "application/json");
            int statusCode = (int) response.get("code");

            switch (statusCode) {
                case 304:
                    throw new WarningDockerJSONException(
                            "container already stopped");
                case 404:
                    throw new ErrorDockerJSONException("docker : no such container");
                case 500:
                    throw new ErrorDockerJSONException("docker : server error");
            }
        } catch (URISyntaxException | WarningDockerJSONException
                | ErrorDockerJSONException | IOException e) {
            StringBuilder msgError = new StringBuilder(256);
            msgError.append(name.toLowerCase()).append(",hostIP=")
                    .append(hostIp).append(",uri=").append(uri);
            logger.error(msgError.toString(), e);
            throw new FatalDockerJSONException("docker : error fatal");
        }

        logger.info((String) response.get("body"));

        return (String) response.get("body");

    }

    public void deleteImage(String id, String hostIp)
            throws DockerJSONException {
        URI uri = null;
        try {
            uri = new URIBuilder().setScheme("http").setHost(hostIp)
                    .setPath("/images/" + id).build();
            int statusCode = client.sendDelete(uri);
            switch (statusCode) {
                case 304:
                    throw new WarningDockerJSONException(
                            "container already stopped");
                case 404:
                    throw new ErrorDockerJSONException("docker : no such container");
                case 500:
                    throw new ErrorDockerJSONException("docker : server error");
            }
        } catch (URISyntaxException | WarningDockerJSONException
                | ErrorDockerJSONException | IOException e) {
            StringBuilder msgError = new StringBuilder(256);
            msgError.append(id).append(",hostIP=").append(hostIp)
                    .append(",uri=").append(uri);
            logger.error(msgError.toString(), e);
            throw new FatalDockerJSONException("docker : error fatal");
        }
    }

    /**
     * @param registryIP
     * @param tag
     * @param repository
     * @return
     * @throws DockerJSONException
     * @throws ParseException      Appels à la registry docker pour la suppression des
     *                             containers liés au snapshot
     */
    /*
    public void deleteImageIntoTheRegistry(String registryIP, String tag,
                                           String repository)
            throws DockerJSONException {
        URI uri = null;
        try {

            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost(registryIP)
                    .setPath(
                            "/v1/repositories/" + repository + "/tags/"
                                    + tag.toLowerCase()).build();
            int statusCode = client.sendDelete(uri);
            switch (statusCode) {
                case 401:
                    throw new WarningDockerJSONException("Requires authorization");
                case 404:
                    throw new ErrorDockerJSONException("docker : Tag not found : "
                            + tag);
                case 500:
                    throw new ErrorDockerJSONException("docker : server error");
            }
        } catch (URISyntaxException | WarningDockerJSONException
                | ErrorDockerJSONException | IOException e) {
            StringBuilder msgError = new StringBuilder(256);
            msgError.append(tag.toLowerCase()).append(",hostIP=")
                    .append(registryIP).append(",uri=").append(uri);
            logger.error(msgError.toString(), e);
            throw new FatalDockerJSONException("docker : error fatal");
        }
    }

    public Integer getARandomHostPorts(String hostIp) {
        int port = randPort(2599, 2900);

        // on supprime tous les ports ouverts de forbiddenPorts car ils ne sont
        // plus succeptibles de déclencher une erreur
        if (!forbbidenPorts.isEmpty()) {
            forbbidenPorts.stream().filter(t -> isPortOpened(hostIp, t))
                    .forEach(t -> forbbidenPorts.remove(t));
        }

        if (isPortOpened(hostIp, port) | forbbidenPorts.contains(port)) {
            port = getARandomHostPorts(hostIp);
        }
        // on ajoute le port à la liste des ports à ne pas ouvrir tant que
        // l'opération n'est pas terminée
        forbbidenPorts.add(port);

        return port;
    }

    private boolean isPortOpened(String ip, Integer port) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(ip, port), 500);
            socket.close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private int randPort(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    */
}
