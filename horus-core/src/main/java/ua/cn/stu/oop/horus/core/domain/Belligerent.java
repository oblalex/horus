package ua.cn.stu.oop.horus.core.domain;

import ua.cn.stu.oop.horus.core.domain.text.*;

/**
 *
 * @author alex
 */
public class Belligerent extends GenericDomain implements TitleLinkCarrier{

    private short gameCode;
    private boolean selectable;
    private String colorHEXCode;
    private TitleLink titleLink;

    @Override
    public TitleLink getTitleLink() {
        return titleLink;
    }

    @Override
    public void setTitleLink(TitleLink titleLink) {
        this.titleLink = titleLink;
    }

    public String getColorHEXCode() {
        return colorHEXCode;
    }

    public void setColorHEXCode(String colorHEXCode) {
        this.colorHEXCode = colorHEXCode;
    }

    public short getGameCode() {
        return gameCode;
    }

    public void setGameCode(short gameCode) {
        this.gameCode = gameCode;
    }

    public boolean isSelectable() {
        return selectable;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }
}
