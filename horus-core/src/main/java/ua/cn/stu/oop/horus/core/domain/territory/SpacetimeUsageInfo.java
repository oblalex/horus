package ua.cn.stu.oop.horus.core.domain.territory;

import java.sql.Timestamp;
import ua.cn.stu.oop.horus.core.domain.*;

/**
 *
 * @author alex
 */
public class SpacetimeUsageInfo extends GenericDomain {

    private Belligerent belligerent;
    private Battleground battleground;
    private Timestamp timeStart;
    private Timestamp timeStop;

    public Belligerent getBelligerent() {
        return belligerent;
    }

    public void setBelligerent(Belligerent belligerent) {
        this.belligerent = belligerent;
    }

    public Timestamp getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Timestamp timeStart) {
        this.timeStart = timeStart;
    }

    public Timestamp getTimeStop() {
        return timeStop;
    }

    public void setTimeStop(Timestamp timeStop) {
        this.timeStop = timeStop;
    }

    public Battleground getBattleground() {
        return battleground;
    }

    public void setBattleground(Battleground battleground) {
        this.battleground = battleground;
    }
}
