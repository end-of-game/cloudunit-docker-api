package fr.treeptik.cloudunit.docker.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by guillaume on 21/10/15.
 */
public class Container implements Serializable {

    @JsonProperty("Config")
    private Config config;

    @JsonProperty("AppArmorProfile")
    private String appArmorProfile;

    @JsonProperty("Args")
    private List<String> args;

    @JsonProperty("Created")
    private String created;

    @JsonProperty("Driver")
    private String driver;

    @JsonProperty("ExecDriver")
    private String execDriver;

    @JsonProperty("ExecIDs")
    private String execIDs;

    @JsonProperty("HostConfig")
    private HostConfig hostConfig;

    @JsonProperty("HostnamePath")
    private String hostnamePath;

    @JsonProperty("HostsPath")
    private String hostsPath;

    @JsonProperty("LogPath")
    private String logPath;

    @JsonProperty("Id")
    private String id;

    @JsonProperty("Image")
    private String image;

    @JsonProperty("MountLabel")
    private String mountLabel;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Path")
    private String path;

    @JsonProperty("ProcessLabel")
    private String processLabel;

    @JsonProperty("ResolvConfPath")
    private String resolvConfPath;

    @JsonProperty("RestartCount")
    private Long restartCount;

    @JsonProperty("Volumes")
    private Map<String, String> volumes;

    @JsonProperty("VolumesRW")
    private Map<String, String> volumesRW;

    @JsonProperty("State")
    private State state;

    @JsonProperty("NetworkSettings")
    private NetworkSettings networkSettings;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public String getAppArmorProfile() {
        return appArmorProfile;
    }

    public void setAppArmorProfile(String appArmorProfile) {
        this.appArmorProfile = appArmorProfile;
    }

    public List<String> getArgs() {
        return args;
    }

    public void setArgs(List<String> args) {
        this.args = args;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getExecDriver() {
        return execDriver;
    }

    public void setExecDriver(String execDriver) {
        this.execDriver = execDriver;
    }

    public String getExecIDs() {
        return execIDs;
    }

    public void setExecIDs(String execIDs) {
        this.execIDs = execIDs;
    }

    public HostConfig getHostConfig() {
        return hostConfig;
    }

    public void setHostConfig(HostConfig hostConfig) {
        this.hostConfig = hostConfig;
    }

    public String getHostnamePath() {
        return hostnamePath;
    }

    public void setHostnamePath(String hostnamePath) {
        this.hostnamePath = hostnamePath;
    }

    public String getHostsPath() {
        return hostsPath;
    }

    public void setHostsPath(String hostsPath) {
        this.hostsPath = hostsPath;
    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }


    public Long getRestartCount() {
        return restartCount;
    }

    public void setRestartCount(Long restartCount) {
        this.restartCount = restartCount;
    }

    public String getResolvConfPath() {
        return resolvConfPath;
    }

    public void setResolvConfPath(String resolvConfPath) {
        this.resolvConfPath = resolvConfPath;
    }

    public String getProcessLabel() {
        return processLabel;
    }

    public void setProcessLabel(String processLabel) {
        this.processLabel = processLabel;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMountLabel() {
        return mountLabel;
    }

    public void setMountLabel(String mountLabel) {
        this.mountLabel = mountLabel;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, String> getVolumes() {
        return volumes;
    }

    public void setVolumes(Map<String, String> volumes) {
        this.volumes = volumes;
    }

    public Map<String, String> getVolumesRW() {
        return volumesRW;
    }

    public void setVolumesRW(Map<String, String> volumesRW) {
        this.volumesRW = volumesRW;
    }
}
