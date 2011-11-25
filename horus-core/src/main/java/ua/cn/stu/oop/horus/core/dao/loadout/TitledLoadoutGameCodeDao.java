package ua.cn.stu.oop.horus.core.dao.loadout;

import ua.cn.stu.oop.horus.core.dao.GenericDao;
import ua.cn.stu.oop.horus.core.dao.text.TitleLinkCarrierDao;
import ua.cn.stu.oop.horus.core.domain.loadout.TitledLoadoutGameCode;

/**
 *
 * @author alex
 */
public interface TitledLoadoutGameCodeDao  
        extends GenericDao<TitledLoadoutGameCode>,
        TitleLinkCarrierDao<TitledLoadoutGameCode>{
    
}
