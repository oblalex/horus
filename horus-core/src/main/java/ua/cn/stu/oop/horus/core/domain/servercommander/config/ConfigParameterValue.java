package ua.cn.stu.oop.horus.core.domain.servercommander.config;

import ua.cn.stu.oop.horus.core.domain.GenericDomain;

/**
 *
 * @author alex
 */
public class ConfigParameterValue
        extends GenericDomain
        implements ConfigSchemeCarrier {

    private ConfigScheme configScheme;
    private ConfigParameter parameter;
    private String value;

    @Override
    public ConfigScheme getConfigScheme() {
        return configScheme;
    }

    @Override
    public void setConfigScheme(ConfigScheme configScheme) {
        this.configScheme = configScheme;
    }

    public ConfigParameter getParameter() {
        return parameter;
    }

    public void setParameter(ConfigParameter parameter) {
        this.parameter = parameter;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
