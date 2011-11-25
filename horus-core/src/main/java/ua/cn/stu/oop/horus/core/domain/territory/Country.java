package ua.cn.stu.oop.horus.core.domain.territory;

import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.text.*;
import ua.cn.stu.oop.horus.core.domain.file.DBFile;

/**
 *
 * @author alex
 */
public class Country
        extends GenericDomain
        implements TitleLinkCarrier {

    private TitleLink titleLink;
    private String gameCode;
    private boolean exists;
    private DBFile flagIcon;

    public DBFile getFlagIcon() {
        return flagIcon;
    }

    public void setFlagIcon(DBFile flagIcon) {
        this.flagIcon = flagIcon;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    @Override
    public TitleLink getTitleLink() {
        return titleLink;
    }

    @Override
    public void setTitleLink(TitleLink titleLink) {
        this.titleLink = titleLink;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }
}
