package ua.cn.stu.oop.horus.core.domain.servercommander.config;

import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.text.StringTitleCarrier;

/**
 *
 * @author alex
 */
public class ConfigScheme
        extends GenericDomain
        implements StringTitleCarrier {

    private String title;
    private String description;
    private boolean trainingModeOnly;

    public boolean isTrainingModeOnly() {
        return trainingModeOnly;
    }

    public void setTrainingModeOnly(boolean trainingModeOnly) {
        this.trainingModeOnly = trainingModeOnly;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }
}
