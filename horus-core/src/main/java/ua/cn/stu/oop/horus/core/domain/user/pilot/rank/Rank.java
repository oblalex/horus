package ua.cn.stu.oop.horus.core.domain.user.pilot.rank;

import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.ParentCarrier;
import ua.cn.stu.oop.horus.core.domain.text.*;

/**
 *
 * @author alex
 */
public class Rank
        extends GenericDomain
        implements TitleLinkCarrier,
        ParentCarrier<Rank> {
    
    private Rank parent;
    private TitleLink titleLink;
    private int salaryScorePerHourOfFlight;
    private float neededExperience;
    
    public float getNeededExperience() {
        return neededExperience;
    }
    
    public void setNeededExperience(float neededExperience) {
        this.neededExperience = neededExperience;
    }
    
    @Override
    public Rank getParent() {
        return parent;
    }
    
    @Override
    public void setParent(Rank parent) {
        this.parent = parent;
    }
    
    public int getSalaryScorePerHourOfFlight() {
        return salaryScorePerHourOfFlight;
    }
    
    public void setSalaryScorePerHourOfFlight(int salaryScorePerHourOfFlight) {
        this.salaryScorePerHourOfFlight = salaryScorePerHourOfFlight;
    }
    
    @Override
    public TitleLink getTitleLink() {
        return titleLink;
    }
    
    @Override
    public void setTitleLink(TitleLink titleLink) {
        this.titleLink = titleLink;
    }
}
