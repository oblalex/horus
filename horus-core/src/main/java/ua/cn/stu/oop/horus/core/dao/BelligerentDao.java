package ua.cn.stu.oop.horus.core.dao;

import ua.cn.stu.oop.horus.core.dao.text.TitleLinkCarrierDao;
import ua.cn.stu.oop.horus.core.domain.Belligerent;

/**
 *
 * @author alex
 */
public interface BelligerentDao
        extends GenericDao<Belligerent>,
        TitleLinkCarrierDao<Belligerent> {
}
