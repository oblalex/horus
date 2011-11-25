package ua.cn.stu.oop.horus.core.domain.servercommander;

import ua.cn.stu.oop.horus.core.domain.servercommander.config.ConfigScheme;
import ua.cn.stu.oop.horus.core.domain.servercommander.config.ConfigSchemeCarrier;
import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.text.StringTitleCarrier;

/**
 *
 * @author alex
 */
public class ServerCommander
        extends GenericDomain
        implements ConfigSchemeCarrier,
                   StringTitleCarrier {

    private boolean ready;
    private boolean connected;
    private boolean inTrainingMode;
    private String title;
    private String commanderAddress;
    private ConfigScheme configScheme;

    @Override
    public ConfigScheme getConfigScheme() {
        return configScheme;
    }

    @Override
    public void setConfigScheme(ConfigScheme configScheme) {
        this.configScheme = configScheme;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isInTrainingMode() {
        return inTrainingMode;
    }

    public void setInTrainingMode(boolean inTrainingMode) {
        this.inTrainingMode = inTrainingMode;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public String getCommanderAddress() {
        return commanderAddress;
    }

    public void setCommanderAddress(String commanderAddress) {
        this.commanderAddress = commanderAddress;
    }
}
