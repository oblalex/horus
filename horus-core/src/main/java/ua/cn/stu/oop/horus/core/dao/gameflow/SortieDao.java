package ua.cn.stu.oop.horus.core.dao.gameflow;

import ua.cn.stu.oop.horus.core.dao.GenericDao;
import ua.cn.stu.oop.horus.core.dao.loadout.LoadoutCarrierDao;
import ua.cn.stu.oop.horus.core.domain.gameflow.Sortie;

/**
 *
 * @author alex
 */
public interface SortieDao
        extends GenericDao<Sortie>,
        LoadoutCarrierDao<Sortie> {
}
