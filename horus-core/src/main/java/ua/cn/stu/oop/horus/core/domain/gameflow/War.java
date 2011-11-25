package ua.cn.stu.oop.horus.core.domain.gameflow;

import java.sql.Timestamp;
import ua.cn.stu.oop.horus.core.domain.*;

/**
 *
 * @author alex
 */
public class War extends GenericDomain {

    private Belligerent winner;
    private Timestamp startRealTime;
    private Timestamp startGameTime;
    private Timestamp stopRealTime;
    private Timestamp stopGameTime;

    public Timestamp getStartGameTime() {
        return startGameTime;
    }

    public void setStartGameTime(Timestamp startGameTime) {
        this.startGameTime = startGameTime;
    }

    public Timestamp getStartRealTime() {
        return startRealTime;
    }

    public void setStartRealTime(Timestamp startRealTime) {
        this.startRealTime = startRealTime;
    }

    public Timestamp getStopGameTime() {
        return stopGameTime;
    }

    public void setStopGameTime(Timestamp stopGameTime) {
        this.stopGameTime = stopGameTime;
    }

    public Timestamp getStopRealTime() {
        return stopRealTime;
    }

    public void setStopRealTime(Timestamp stopRealTime) {
        this.stopRealTime = stopRealTime;
    }

    public Belligerent getWinner() {
        return winner;
    }

    public void setWinner(Belligerent winner) {
        this.winner = winner;
    }
}
