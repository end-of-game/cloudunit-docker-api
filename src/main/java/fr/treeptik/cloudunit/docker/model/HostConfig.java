package fr.treeptik.cloudunit.docker.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by guillaume on 21/10/15.
 */
public class HostConfig implements Serializable {

    @JsonProperty("Privileged")
    private Boolean privileged;

    @JsonProperty("PublishAllPorts")
    private Boolean publishAllPorts;

    @JsonProperty("Links")
    private List<String> links;

    @JsonProperty("Binds")
    private List<String> binds;

    @JsonProperty("PortBindings")
    private Map<String, List<Object>> portBindings;

    @JsonProperty("VolumesFrom")
    private List<String> volumesFrom;

    @JsonProperty("BlkioWeight")
    private Long blkioWeight;

    @JsonProperty("CapAdd")
    private String capAdd;

    @JsonProperty("CapDrop")
    private String capDrop;

    @JsonProperty("ContainerIDFile")
    private String containerIDFile;

    @JsonProperty("CpusetCpus")
    private String cpusetCpus;

    @JsonProperty("CpusetMems")
    private String cpusetMems;

    @JsonProperty("CpuShares")
    private Long cpuShares;

    @JsonProperty("CpuPeriod")
    private Long cpuPeriod;

    @JsonProperty("Devices")
    private List<String> devices;

    @JsonProperty("Dns")
    private String dns;

    @JsonProperty("DnsSearch")
    private String dnsSearch;

    @JsonProperty("ExtraHosts")
    private String extraHosts;

    @JsonProperty("IpcMode")
    private String ipcMode;

    @JsonProperty("LxcConf")
    private List<String> lxcConf;

    @JsonProperty("Memory")
    private Long memory;

    @JsonProperty("MemorySwap")
    private Long memorySwap;

    @JsonProperty("OomKillDisable")
    private Boolean oomKillDisable;

    @JsonProperty("NetworkMode")
    private String networkMode;

    @JsonProperty("ReadonlyRootfs")
    private Boolean readonlyRootfs;

    @JsonProperty("SecurityOpt")
    private String securityOpt;

    @JsonProperty("Ulimits")
    private List<Object> ulimits;

    @JsonProperty("CgroupParent")
    private String cgroupParent;

    @JsonProperty("PidMode")
    private String pidMode;

    @JsonProperty("RestartPolicy")
    private RestartPolicy restartPolicy;

    @JsonProperty("LogConfig")
    private LogConfig logConfig;

    @JsonProperty("MemoryReservation")
    private Long memoryReservation;

    @JsonProperty("KernelMemory")
    private Long kernelMemory;

    @JsonProperty("CpuQuota")
    private Long cpuQuota;

    @JsonProperty("MemorySwappiness")
    private Boolean memorySwappiness;

    @JsonProperty("DnsOptions")
    private String dnsOptions;

    @JsonProperty("GroupAdd")
    private Boolean groupAdd;

    @JsonProperty("UTSMode")
    private String uTSMode;

    @JsonProperty("ConsoleSize")
    private List<Long> consoleSize;

    @JsonProperty("VolumeDriver")
    private String olumeDriver;


    public Boolean getPrivileged() {
        return privileged;
    }

    public void setPrivileged(Boolean privileged) {
        this.privileged = privileged;
    }

    public Boolean getPublishAllPorts() {
        return publishAllPorts;
    }

    public void setPublishAllPorts(Boolean publishAllPorts) {
        this.publishAllPorts = publishAllPorts;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }

    public List<String> getBinds() {
        return binds;
    }

    public void setBinds(List<String> binds) {
        this.binds = binds;
    }

    public Map<String, List<Object>> getPortBindings() {
        return portBindings;
    }

    public void setPortBindings(Map<String, List<Object>> portBindings) {
        this.portBindings = portBindings;
    }

    public List<String> getVolumesFrom() {
        return volumesFrom;
    }

    public void setVolumesFrom(List<String> volumesFrom) {
        this.volumesFrom = volumesFrom;
    }

    public Long getBlkioWeight() {
        return blkioWeight;
    }

    public void setBlkioWeight(Long blkioWeight) {
        this.blkioWeight = blkioWeight;
    }

    public String getCapAdd() {
        return capAdd;
    }

    public void setCapAdd(String capAdd) {
        this.capAdd = capAdd;
    }

    public String getCapDrop() {
        return capDrop;
    }

    public void setCapDrop(String capDrop) {
        this.capDrop = capDrop;
    }

    public String getContainerIDFile() {
        return containerIDFile;
    }

    public void setContainerIDFile(String containerIDFile) {
        this.containerIDFile = containerIDFile;
    }

    public String getCpusetCpus() {
        return cpusetCpus;
    }

    public void setCpusetCpus(String cpusetCpus) {
        this.cpusetCpus = cpusetCpus;
    }

    public String getCpusetMems() {
        return cpusetMems;
    }

    public void setCpusetMems(String cpusetMems) {
        this.cpusetMems = cpusetMems;
    }

    public Long getCpuShares() {
        return cpuShares;
    }

    public void setCpuShares(Long cpuShares) {
        this.cpuShares = cpuShares;
    }

    public Long getCpuPeriod() {
        return cpuPeriod;
    }

    public void setCpuPeriod(Long cpuPeriod) {
        this.cpuPeriod = cpuPeriod;
    }

    public List<String> getDevices() {
        return devices;
    }

    public void setDevices(List<String> devices) {
        this.devices = devices;
    }

    public String getDns() {
        return dns;
    }

    public void setDns(String dns) {
        this.dns = dns;
    }

    public String getDnsSearch() {
        return dnsSearch;
    }

    public void setDnsSearch(String dnsSearch) {
        this.dnsSearch = dnsSearch;
    }

    public String getExtraHosts() {
        return extraHosts;
    }

    public void setExtraHosts(String extraHosts) {
        this.extraHosts = extraHosts;
    }

    public String getIpcMode() {
        return ipcMode;
    }

    public void setIpcMode(String ipcMode) {
        this.ipcMode = ipcMode;
    }

    public List<String> getLxcConf() {
        return lxcConf;
    }

    public void setLxcConf(List<String> lxcConf) {
        this.lxcConf = lxcConf;
    }

    public Long getMemory() {
        return memory;
    }

    public void setMemory(Long memory) {
        this.memory = memory;
    }

    public Long getMemorySwap() {
        return memorySwap;
    }

    public void setMemorySwap(Long memorySwap) {
        this.memorySwap = memorySwap;
    }

    public Boolean getOomKillDisable() {
        return oomKillDisable;
    }

    public void setOomKillDisable(Boolean oomKillDisable) {
        this.oomKillDisable = oomKillDisable;
    }

    public String getNetworkMode() {
        return networkMode;
    }

    public void setNetworkMode(String networkMode) {
        this.networkMode = networkMode;
    }

    public Boolean getReadonlyRootfs() {
        return readonlyRootfs;
    }

    public void setReadonlyRootfs(Boolean readonlyRootfs) {
        this.readonlyRootfs = readonlyRootfs;
    }

    public String getSecurityOpt() {
        return securityOpt;
    }

    public void setSecurityOpt(String securityOpt) {
        this.securityOpt = securityOpt;
    }

    public List<Object> getUlimits() {
        return ulimits;
    }

    public void setUlimits(List<Object> ulimits) {
        this.ulimits = ulimits;
    }

    public String getCgroupParent() {
        return cgroupParent;
    }

    public void setCgroupParent(String cgroupParent) {
        this.cgroupParent = cgroupParent;
    }

    public String getPidMode() {
        return pidMode;
    }

    public void setPidMode(String pidMode) {
        this.pidMode = pidMode;
    }

    public RestartPolicy getRestartPolicy() {
        return restartPolicy;
    }

    public void setRestartPolicy(RestartPolicy restartPolicy) {
        this.restartPolicy = restartPolicy;
    }

    public LogConfig getLogConfig() {
        return logConfig;
    }

    public void setLogConfig(LogConfig logConfig) {
        this.logConfig = logConfig;
    }

    public Long getMemoryReservation() {
        return memoryReservation;
    }

    public void setMemoryReservation(Long memoryReservation) {
        this.memoryReservation = memoryReservation;
    }
}
