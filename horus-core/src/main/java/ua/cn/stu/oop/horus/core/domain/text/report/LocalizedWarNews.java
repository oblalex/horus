package ua.cn.stu.oop.horus.core.domain.text.report;

import ua.cn.stu.oop.horus.core.domain.territory.Node;
import java.sql.Timestamp;
import ua.cn.stu.oop.horus.core.domain.*;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;

/**
 *
 * @author alex
 */
public class LocalizedWarNews extends GenericDomain {

    private AvailableLocale locale;
    private Belligerent receivingBelligerent;
    private Node territoryNode;
    private String newsBody;
    private Timestamp publicationRealTime;
    private Timestamp publicationGameTime;

    public AvailableLocale getLocale() {
        return locale;
    }

    public void setLocale(AvailableLocale locale) {
        this.locale = locale;
    }

    public String getNewsBody() {
        return newsBody;
    }

    public void setNewsBody(String newsBody) {
        this.newsBody = newsBody;
    }

    public Timestamp getPublicationGameTime() {
        return publicationGameTime;
    }

    public void setPublicationGameTime(Timestamp publicationGameTime) {
        this.publicationGameTime = publicationGameTime;
    }

    public Timestamp getPublicationRealTime() {
        return publicationRealTime;
    }

    public void setPublicationRealTime(Timestamp publicationRealTime) {
        this.publicationRealTime = publicationRealTime;
    }

    public Belligerent getReceivingBelligerent() {
        return receivingBelligerent;
    }

    public void setReceivingBelligerent(Belligerent receivingBelligerent) {
        this.receivingBelligerent = receivingBelligerent;
    }

    public Node getTerritoryNode() {
        return territoryNode;
    }

    public void setTerritoryNode(Node territoryNode) {
        this.territoryNode = territoryNode;
    }
}
