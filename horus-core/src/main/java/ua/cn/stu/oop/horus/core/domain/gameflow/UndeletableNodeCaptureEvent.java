package ua.cn.stu.oop.horus.core.domain.gameflow;

import java.sql.Timestamp;
import ua.cn.stu.oop.horus.core.domain.*;
import ua.cn.stu.oop.horus.core.domain.territory.Node;
import ua.cn.stu.oop.horus.core.domain.territory.NodeCarrier;

/**
 *
 * @author alex
 */
public class UndeletableNodeCaptureEvent
        extends GenericDomain
        implements NodeCarrier {

    private Node node;
    private Belligerent belligerent;
    private Timestamp captureRealDate;
    private Timestamp captureGameDate;

    public Belligerent getBelligerent() {
        return belligerent;
    }

    public void setBelligerent(Belligerent belligerent) {
        this.belligerent = belligerent;
    }

    public Timestamp getCaptureGameDate() {
        return captureGameDate;
    }

    public void setCaptureGameDate(Timestamp captureGameDate) {
        this.captureGameDate = captureGameDate;
    }

    public Timestamp getCaptureRealDate() {
        return captureRealDate;
    }

    public void setCaptureRealDate(Timestamp captureRealDate) {
        this.captureRealDate = captureRealDate;
    }

    @Override
    public Node getNode() {
        return node;
    }

    @Override
    public void setNode(Node node) {
        this.node = node;
    }
}
