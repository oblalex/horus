package ua.cn.stu.oop.horus.core.domain.object;

import ua.cn.stu.oop.horus.core.domain.GenericDomain;

/**
 *
 * @author alex
 */
public class Aircraft extends GenericDomain {

    private TechnologyUnit technologyUnit;
    private int serviceCeilingMetres;
    private int horizontalTurnTimeSeconds;
    private String codeInDynamicSectionOfMissionFile;

    public String getCodeInDynamicSectionOfMissionFile() {
        return codeInDynamicSectionOfMissionFile;
    }

    public void setCodeInDynamicSectionOfMissionFile(String codeInDynamicSectionOfMissionFile) {
        this.codeInDynamicSectionOfMissionFile = codeInDynamicSectionOfMissionFile;
    }

    public TechnologyUnit getTechnologyUnit() {
        return technologyUnit;
    }

    public void setTechnologyUnit(TechnologyUnit technologyUnit) {
        this.technologyUnit = technologyUnit;
    }

    public int getHorizontalTurnTimeSeconds() {
        return horizontalTurnTimeSeconds;
    }

    public void setHorizontalTurnTimeSeconds(int horizontalTurnTimeSeconds) {
        this.horizontalTurnTimeSeconds = horizontalTurnTimeSeconds;
    }

    public int getServiceCeilingMetres() {
        return serviceCeilingMetres;
    }

    public void setServiceCeilingMetres(int serviceCeilingMetres) {
        this.serviceCeilingMetres = serviceCeilingMetres;
    }
}
