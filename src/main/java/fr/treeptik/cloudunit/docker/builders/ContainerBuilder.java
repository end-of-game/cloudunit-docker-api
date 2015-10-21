package fr.treeptik.cloudunit.docker.builders;

import fr.treeptik.cloudunit.docker.model.Config;
import fr.treeptik.cloudunit.docker.model.Container;

/**
 * Created by guillaume on 21/10/15.
 */
public class ContainerBuilder {
    private String name;
    private Config config;

    private ContainerBuilder() {
    }

    public static ContainerBuilder aContainer() {
        return new ContainerBuilder();
    }

    public ContainerBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ContainerBuilder withConfig(Config config) {
        this.config = config;
        return this;
    }

    public ContainerBuilder but() {
        return aContainer().withName(name).withConfig(config);
    }

    public Container build() {
        Container container = new Container();
        container.setName(name);
        container.setConfig(config);
        return container;
    }
}
