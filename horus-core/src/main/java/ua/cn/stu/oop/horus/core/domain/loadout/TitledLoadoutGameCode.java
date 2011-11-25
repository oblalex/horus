package ua.cn.stu.oop.horus.core.domain.loadout;

import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.text.*;

/**
 *
 * @author alex
 */
public class TitledLoadoutGameCode
        extends GenericDomain
        implements TitleLinkCarrier {

    private TitleLink titleLink;
    private String gameLogCode;

    public String getGameLogCode() {
        return gameLogCode;
    }

    public void setGameLogCode(String gameLogCode) {
        this.gameLogCode = gameLogCode;
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
