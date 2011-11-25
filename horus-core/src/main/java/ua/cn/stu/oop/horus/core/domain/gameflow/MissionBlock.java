package ua.cn.stu.oop.horus.core.domain.gameflow;

import ua.cn.stu.oop.horus.core.domain.GenericDomain;

/**
 *
 * @author alex
 */
public class MissionBlock extends GenericDomain {

    private boolean inTrainingMode;
    private long millisecondsDuration;
    private long millisecondsLeft;

    public boolean isInTrainingMode() {
        return inTrainingMode;
    }

    public void setInTrainingMode(boolean inTrainingMode) {
        this.inTrainingMode = inTrainingMode;
    }

    public long getMillisecondsDuration() {
        return millisecondsDuration;
    }

    public void setMillisecondsDuration(long millisecondsDuration) {
        this.millisecondsDuration = millisecondsDuration;
    }

    public long getMillisecondsLeft() {
        return millisecondsLeft;
    }

    public void setMillisecondsLeft(long millisecondsLeft) {
        this.millisecondsLeft = millisecondsLeft;
    }
}
