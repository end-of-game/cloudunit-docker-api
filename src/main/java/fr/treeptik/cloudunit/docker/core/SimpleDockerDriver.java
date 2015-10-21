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

public class SimpleDockerDriver implements Driver {

    private static Logger logger = LoggerFactory.getLogger(SimpleDockerDriver.class);

    private JSONClient client;

    /*
        public ConcurrentLinkedQueue<Integer> forbbidenPorts = new ConcurrentLinkedQueue<Integer>();

        private final static String[] fixedPort = {"3306/tcp", "5432/tcp", "11211/tcp",
            "1521/tcp", "27017/tcp"};



        private static JSONObject parser(String message)
            throws ParseException {
            JSONParser jsonParser = new JSONParser();
            JSONObject json = (JSONObject) jsonParser.parse(message);
            return json;
        }

        private static List<String> getList(JSONObject object, String listName) {
            List<String> list = new ArrayList<>();
            JSONArray node = (JSONArray) object.get(listName);
            Iterator<String> iterator = node.iterator();
            while (iterator.hasNext()) {
                list.add(iterator.next());
            }
            return list;
        }

        private static List<Object> getObjectList(JSONObject object, String listName) {
            List<Object> list = new ArrayList<>();
            JSONArray node = (JSONArray) object.get(listName);
            Iterator<Object> iterator = node.iterator();
            while (iterator.hasNext()) {
                list.add(iterator.next());
            }
            return list;
        }


*/

    /*
            public DockerContainer findOne(String name, String hostIp)
                throws DockerJSONException {
                URI uri = null;
                DockerContainer dockerContainer = new DockerContainer();
                try {
                    uri = new URIBuilder().setScheme("http").setHost(hostIp)
                        .setPath("/containers/" + name + "/json").build();

                    // Docker error management
                    JsonResponse jsonResponse;

                    try {

                        jsonResponse = client.sendGet(uri);

                        switch (jsonResponse.getStatus()) {
                            case 404:
                                throw new ErrorDockerJSONException(
                                    "docker : no such container");
                            case 500:
                                throw new ErrorDockerJSONException("docker : server error");
                        }

                    } catch (IOException e) {
                        throw new FatalDockerJSONException(e.getLocalizedMessage());
                    }

                    // Init maps for volumes and ports

                    Map<String, String> ports = new HashMap<>();
                    Map<String, String> volumes = new HashMap<>();

                    // SubString to remove the "/"
                    String response = jsonResponse.getMessage();

                    dockerContainer.setName(parser(response).get("Name").toString()
                        .substring(1));
                    dockerContainer.setId((String) parser(response).get("Id")
                        .toString());

                    Long memorySwap = (Long) parser(
                        parser(response).get("Config").toString())
                        .get("MemorySwap");
                    Long memory = (Long) parser(
                        parser(response).get("Config").toString()).get("Memory");
                    dockerContainer.setMemorySwap(memorySwap);
                    dockerContainer.setMemory(memory);
                    dockerContainer.setImage((String) parser(
                        parser(response).get("Config").toString()).get("Image"));

                    if (parser(parser(response).get("HostConfig").toString()).get(
                        "VolumesFrom") != null) {
                        dockerContainer.setVolumesFrom(getList(parser(parser(response)
                                .get("HostConfig").toString()),
                            "VolumesFrom"));
                    }

                    dockerContainer.setIp((String) parser(
                        parser(response).get("NetworkSettings").toString()).get(
                        "IPAddress"));

                    if (parser(parser(response).get("NetworkSettings").toString()).get(
                        "Ports") != null) {
                        for (Object port : parser(
                            parser(
                                parser(response).get("NetworkSettings")
                                    .toString()).get("Ports").toString())
                            .keySet()) {

                            if (!Arrays.asList(fixedPort).contains(port.toString())) {
                                Object forwardedPort = (Object) getObjectList(
                                    parser(parser(
                                        parser(response).get("NetworkSettings")
                                            .toString()).get("Ports")
                                        .toString()),
                                    port.toString()).get(0);
                                ports.put(port.toString(),
                                    parser(forwardedPort.toString())
                                        .get("HostPort").toString());
                            }
                        }
                    }

                    if (parser(response).get("Volumes") != null) {
                        for (Object volume : parser(
                            parser(response).get("Volumes").toString()).keySet()) {

                            volumes.put(volume.toString(),
                                parser(parser(response).get("Volumes").toString())
                                    .get(volume.toString()).toString());

                        }
                    }

                    dockerContainer.setVolumes(volumes);

                    logger.debug("Volumes : " + volumes);

                    dockerContainer.setPorts(ports);
                    dockerContainer.setCmd(getList(parser(parser(response)
                        .get("Config").toString()), "Cmd"));
                    if (parser(parser(response).get("State").toString()).get("Running")
                        .toString().equals("true")) {
                        dockerContainer.setState("Running");
                    }

                    if (parser(parser(response).get("State").toString()).get("Running")
                        .toString().equals("false")
                        && parser(parser(response).get("State").toString())
                        .get("Paused").toString().equals("false")) {
                        dockerContainer.setState("Paused");
                    }

                    if (parser(parser(response).get("State").toString()).get("Paused")
                        .toString().equals("true")) {
                        dockerContainer.setState("Paused");
                    }

                } catch (NumberFormatException | URISyntaxException | ParseException e) {
                    StringBuilder msgError = new StringBuilder(256);
                    msgError.append(name).append(",hostIP=").append(hostIp)
                        .append(",uri=").append(uri);
                    logger.error("" + msgError, e);
                    throw new FatalDockerJSONException("docker : error fatal");
                }

                return dockerContainer;
            }

            public DockerContainer findOneWithImageID(String name, String hostIp)
                throws DockerJSONException {
                URI uri = null;
                DockerContainer dockerContainer = new DockerContainer();
                try {
                    uri = new URIBuilder().setScheme("http").setHost(hostIp).setPath("/containers/" + name + "/json").build();
                    JsonResponse jsonResponse;
                    // Docker error management
                    try {
                        jsonResponse = client.sendGet(uri);
                        switch (jsonResponse.getStatus()) {
                            case 404:
                                throw new ErrorDockerJSONException(
                                    "docker : no such container");
                            case 500:
                                throw new ErrorDockerJSONException("docker : server error");
                        }

                    } catch (IOException e) {
                        throw new FatalDockerJSONException(e.getLocalizedMessage());
                    }

                    // Init maps for volumes and ports

                    Map<String, String> ports = new HashMap<>();
                    Map<String, String> volumes = new HashMap<>();

                    // SubString to remove the "/"
                    String response = jsonResponse.getMessage();

                    dockerContainer.setImageID((String) parser(response).get("Image")
                        .toString());
                    dockerContainer.setName(parser(response).get("Name").toString()
                        .substring(1));
                    dockerContainer.setId((String) parser(response).get("Id")
                        .toString());

                    Long memorySwap = (Long) parser(
                        parser(response).get("Config").toString())
                        .get("MemorySwap");
                    Long memory = (Long) parser(
                        parser(response).get("Config").toString()).get("Memory");
                    dockerContainer.setMemorySwap(memorySwap);
                    dockerContainer.setMemory(memory);
                    dockerContainer.setImage((String) parser(
                        parser(response).get("Config").toString()).get("Image"));

                    if (parser(parser(response).get("HostConfig").toString()).get(
                        "VolumesFrom") != null) {
                        dockerContainer.setVolumesFrom(getList(parser(parser(response)
                                .get("HostConfig").toString()),
                            "VolumesFrom"));
                    }

                    dockerContainer.setIp((String) parser(
                        parser(response).get("NetworkSettings").toString()).get(
                        "IPAddress"));

                    if (parser(parser(response).get("NetworkSettings").toString()).get(
                        "Ports") != null) {
                        for (Object port : parser(
                            parser(
                                parser(response).get("NetworkSettings")
                                    .toString()).get("Ports").toString())
                            .keySet()) {

                            if (!Arrays.asList(fixedPort).contains(port.toString())) {
                                Object forwardedPort = (Object) getObjectList(
                                    parser(parser(
                                        parser(response).get("NetworkSettings")
                                            .toString()).get("Ports")
                                        .toString()),
                                    port.toString()).get(0);
                                ports.put(port.toString(),
                                    parser(forwardedPort.toString())
                                        .get("HostPort").toString());
                            }
                        }
                    }

                    if (parser(response).get("Volumes") != null) {
                        for (Object volume : parser(
                            parser(response).get("Volumes").toString()).keySet()) {

                            volumes.put(volume.toString(),
                                parser(parser(response).get("Volumes").toString())
                                    .get(volume.toString()).toString());

                        }
                    }

                    dockerContainer.setVolumes(volumes);

                    logger.debug("Volumes : " + volumes);

                    dockerContainer.setPorts(ports);
                    dockerContainer.setCmd(getList(parser(parser(response)
                        .get("Config").toString()), "Cmd"));
                    if (parser(parser(response).get("State").toString()).get("Running")
                        .toString().equals("true")) {
                        dockerContainer.setState("Running");
                    }

                    if (parser(parser(response).get("State").toString()).get("Running")
                        .toString().equals("false")
                        && parser(parser(response).get("State").toString())
                        .get("Paused").toString().equals("false")) {
                        dockerContainer.setState("Paused");
                    }

                    if (parser(parser(response).get("State").toString()).get("Paused")
                        .toString().equals("true")) {
                        dockerContainer.setState("Paused");
                    }

                } catch (NumberFormatException | URISyntaxException | ParseException e) {
                    StringBuilder msgError = new StringBuilder(256);
                    msgError.append(name).append(",hostIP=").append(hostIp)
                        .append(",uri=").append(uri);
                    logger.error("" + msgError, e);
                    throw new FatalDockerJSONException("docker : error fatal");
                }

                return dockerContainer;
            }

            public String checkDockerInfos(String hostAddress)
                throws DockerJSONException {
                URI uri = null;
                JsonResponse jsonResponse;
                try {
                    uri = new URIBuilder().setScheme("http").setHost(hostAddress)
                        .setPath("/info").build();

                    jsonResponse = client.sendGet(uri);
                    if (jsonResponse.getStatus() == 500) {
                        throw new ErrorDockerJSONException("server error");
                    }

                } catch (NumberFormatException | URISyntaxException | IOException e) {

                    throw new FatalDockerJSONException("docker : error fatal");
                }
                return jsonResponse.getMessage();
            }

            /**
             * /containers/json : not same format as an inspect of container List all
             * Running or Paused containers retrieve name (with cloudunit format), image
             * and state
             *
             * @param hostAddress : host address
             * @return
             * @throws DockerJSONException
             */
    /*    public List<DockerContainer> listAllContainers(String hostAddress)
            throws DockerJSONException {

        URI uri = null;
        List<DockerContainer> listContainers = new ArrayList<>();
        try {

            uri = new URIBuilder().setScheme("http").setHost(hostAddress)
                    .setPath("/containers/json").build();

            if (logger.isDebugEnabled()) {
                logger.debug("uri : " + uri);
            }

            JsonResponse jsonResponse;
            try {
                jsonResponse = client.sendGet(uri);
                switch (jsonResponse.getStatus()) {
                    case 400:
                        throw new ErrorDockerJSONException("docker : bad parameter");
                    case 500:
                        throw new ErrorDockerJSONException("docker : server error");
                }

            } catch (IOException e) {
                e.printStackTrace();
                throw new DockerJSONException("Error : listAllContainers "
                        + e.getLocalizedMessage(), e);
            }

            if (logger.isDebugEnabled()) {
                logger.debug("response : " + jsonResponse);
            }

            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(jsonResponse.getMessage());
            JSONArray array = (JSONArray) obj;
            for (int i = 0; i < array.size(); i++) {
                String containerDescription = array.get(i).toString();
                try {
                    String firstSubString = (parser(containerDescription).get(
                            "Names").toString()).substring(4);
                    String Names = null;
                    // for container with link where the link name is also show
                    if (firstSubString.lastIndexOf(",") != -1) {
                        Names = firstSubString.substring(0,
                                firstSubString.lastIndexOf(",") - 1);

                    } else {
                        Names = firstSubString.substring(0,
                                firstSubString.lastIndexOf("\""));
                    }
                    if (logger.isDebugEnabled()) {
                        logger.debug("Names=[" + Names + "]");
                    }
                    if (Names.trim().length() > 0) {
                        DockerContainer dockerContainer = findOne(Names,
                                hostAddress);
                        if (dockerContainer != null) {
                            listContainers.add(dockerContainer);
                        }
                    }
                } catch (ParseException e) {
                    throw new DockerJSONException("Error : listAllContainers "
                            + e.getLocalizedMessage(), e);
                }
            }
        } catch (NumberFormatException | URISyntaxException | ParseException e) {
            StringBuilder msgError = new StringBuilder(256);
            msgError.append(",hostIP=").append(hostAddress).append(",uri=")
                    .append(uri);
            logger.error("" + msgError, e);
            throw new FatalDockerJSONException("docker : error fatal");
        }
        return listContainers;
    }
*/
    public DockerResponse create(Container container, String hostIp) throws FatalDockerJSONException {
        URI uri = null;
        String body = null;
        DockerResponse dockerResponse = null;
        try {
            uri = new URIBuilder().setScheme("http").setHost(hostIp)
                    .setPath("/containers/create")
                    .setParameter("name", container.getName()).build();
            body = new ObjectMapper().writeValueAsString(container);
            dockerResponse = client.sendPost(uri, body, "application/json");
        } catch (URISyntaxException | IOException | JSONClientException e) {
            StringBuilder contextError = new StringBuilder(256);
            contextError.append("uri : " + uri + " - ");
            contextError.append("request body : " + body + " - ");
            contextError.append("server response : " + dockerResponse);
            logger.error(contextError.toString());
            throw new FatalDockerJSONException("An error has occured for create container request due to " + e.getMessage(), e);
        }

        return dockerResponse;
    }
/*
    public void remove(String name, String hostIp)
            throws DockerJSONException {
        URI uri = null;

        if (logger.isInfoEnabled()) {
            logger.info("container name : " + name);
        }
        try {
            //this.kill(name, hostIp);
            uri = new URIBuilder().setScheme("http").setHost(hostIp)
                    .setPath("/containers/" + name).setParameter("v", "1").setParameter("force",
                            "true")
                    .build();
            if (logger.isInfoEnabled()) {
                logger.info("URI DELETE =  " + uri);
            }

            int statusCode = client.sendDelete(uri);
            switch (statusCode) {
                case 400:
                    throw new ErrorDockerJSONException("docker : bad parameter");
                case 404:
                    throw new ErrorDockerJSONException("docker : no such container");
                case 500: {
                    // TODO : il faut comprendre le pourquoi de l'erreur
                    StringBuilder msgError = new StringBuilder(256);
                    msgError.append("name=").append(name).append(", hostIp=")
                            .append(hostIp);
                    logger.error("docker : server error for removing " + msgError);
                    break;
                }
            }

        } catch (URISyntaxException | IOException e) {
            StringBuilder msgError = new StringBuilder(256);
            msgError.append(name).append(",hostIP=").append(hostIp)
                    .append(",uri=").append(uri);
            logger.error("" + msgError, e);
            throw new FatalDockerJSONException("docker : error fatal");
        }
    }

    // methodes d'appels pour les backups

    public DockerContainer start(DockerContainer dockerContainer, String hostIp)
            throws DockerJSONException {
        URI uri = null;
        try {
            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost(hostIp)
                    .setPath(
                            "/containers/" + dockerContainer.getName()
                                    + "/start").build();
            JSONObject config = new JSONObject();

            config.put("Privileged", Boolean.FALSE);
            config.put("PublishAllPorts", Boolean.TRUE);

            JSONArray link = new JSONArray();
            if (dockerContainer.getLinks() != null) {
                link.addAll(dockerContainer.getLinks());
                config.put("Links", link);
            }

            if (dockerContainer.getVolumesFrom() != null) {
                JSONArray listVolumesFrom = new JSONArray();
                if (dockerContainer.getVolumesFrom() != null) {
                    for (int i = 0, iMax = dockerContainer.getVolumesFrom()
                            .size(); i < iMax; i++) {
                        listVolumesFrom.add(dockerContainer.getVolumesFrom()
                                .get(i));
                    }
                }
                config.put("VolumesFrom", listVolumesFrom);
            }

            if (dockerContainer.getPortsToOpen() != null) {
                JSONObject portsBinding = new JSONObject();
                dockerContainer
                        .getPortsToOpen()
                        .stream()
                        .map(t -> t.toString() + "/tcp")
                        .forEach(
                                t -> portsBinding.put(t, Arrays
                                        .asList((new JSONObject(
                                                new HashMap<String, String>() {

                                                    private static final long
                                                            serialVersionUID =
                                                            1L;

                                                    {
                                                        put("HostPort",
                                                                getARandomHostPorts(
                                                                        hostIp)
                                                                        .toString());
                                                        put("HostIp", "0.0.0.0");
                                                    }
                                                })))));

                config.put("PortBindings", portsBinding);
            }

            // ajout des volumes provenant de l'hote
            final List volumes = new ArrayList<String>() {
                {
                    add("/etc/localtime:/etc/localtime:ro");
                    add("/etc/timezone:/etc/timezone:ro");
                }
            };

            BooleanSupplier isRunningIntoKVM = () -> "true"
                    .equalsIgnoreCase(System.getenv().get("CU_KVM"));
            if (isRunningIntoKVM.getAsBoolean()) {
                volumes.add("/dev/urandom:/dev/urandom");
            }

            Predicate<String> isContainerGit = s -> s.contains("-git-");
            if (isContainerGit.test(dockerContainer.getName())) {
                volumes.add("/var/log/cloudunit/git/auth-"
                        + dockerContainer.getId() + ":/var/log/cloudunit");
            }

            config.put("Binds", volumes);

            /**
             * Gestion du binding de port
             */
    /*
            JSONObject portBindsConfigJSONFinal = new JSONObject();
            if (dockerContainer.getPortBindings() != null) {
                /**
                 * pour chaque ports à Binder (ex: 53/udp) on récupère le
                 * tableau avec les deux maps ex :- map1 = HostIp , 172.17.42.1
                 * - map2 = HostPort , 53
                 */
              /*  for (Entry<String, Map<String, String>[]> portKey : dockerContainer
                        .getPortBindings().entrySet()) {

                    logger.info("port/protocol to configure : "
                            + portKey.getKey());

                    // On convertie le tableau en list pour itérer dessus
                    List<Map<String, String>> listOfMapsConfig = Arrays
                            .asList(portKey.getValue());

                    JSONObject portConfigJSON = new JSONObject();
                    for (Map<String, String> portConfigMap : listOfMapsConfig) {
                        JSONArray portConfigJSONArray = new JSONArray();

                        // transfert HostIP and HostPort avec leur valeurs dans
                        // un JSONArray
                        for (Entry<String, String> hostBindingMap : portConfigMap
                                .entrySet()) {
                            logger.info(hostBindingMap.getKey() + " : "
                                    + hostBindingMap.getValue());

                            portConfigJSON.put(hostBindingMap.getKey(),
                                    hostBindingMap.getValue());
                            portConfigJSONArray.add(portConfigJSON);
                        }
                        portBindsConfigJSONFinal.put(portKey.getKey(),
                                portConfigJSONArray);
                        config.put("PortBindings", portBindsConfigJSONFinal);
                    }
                }
            }

            int statusCode = client.sendPostForStart(uri,
                    config.toJSONString(), "application/json");

            switch (statusCode) {
                case 304:
                    throw new WarningDockerJSONException(
                            "container already started");
                case 404:
                    throw new ErrorDockerJSONException("docker : no such container");
                case 500:
                    throw new ErrorDockerJSONException("docker : error server");
            }
            dockerContainer = this.findOne(dockerContainer.getName(), hostIp);
        } catch (URISyntaxException | IOException e) {
            StringBuilder msgError = new StringBuilder(256);
            msgError.append(dockerContainer).append(",hostIP=").append(hostIp)
                    .append(",uri=").append(uri);
            logger.error("" + msgError, e);
            throw new FatalDockerJSONException("docker : error fatal");
        }
        return dockerContainer;

    }

    public void stop(DockerContainer dockerContainer, String hostIp)
            throws DockerJSONException {
        URI uri = null;
        try {
            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost(hostIp)
                    .setPath(
                            "/containers/" + dockerContainer.getName()
                                    + "/stop").setParameter("t", "5").build();
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
            msgError.append(dockerContainer).append(",hostIP=").append(hostIp)
                    .append(",uri=").append(uri);
            logger.error("" + msgError, e);
            throw new FatalDockerJSONException("docker : error fatal");
        }
    }

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
