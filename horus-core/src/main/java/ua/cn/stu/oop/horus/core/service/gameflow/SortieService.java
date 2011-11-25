package ua.cn.stu.oop.horus.core.service.gameflow;

import ua.cn.stu.oop.horus.core.service.GenericService;
import ua.cn.stu.oop.horus.core.service.loadout.LoadoutCarrierService;
import ua.cn.stu.oop.horus.core.domain.gameflow.Sortie;

/**
 *
 * @author alex
 */
public interface SortieService
        extends GenericService<Sortie>,
        LoadoutCarrierService<Sortie> {
}
