package fr.treeptik.cloudunit.docker.model;


import java.io.Serializable;
import java.util.Map;

/**
 * Created by guillaume on 21/10/15.
 */
public class Config implements Serializable {

    private Boolean AttachStdin;

    private Boolean AttachStdout;

    private Boolean AttachStderr;

    private Long Memory;

    private Long MemorySwap;

    private String Image;

    private Map<String, String> ExposedPorts;

}
