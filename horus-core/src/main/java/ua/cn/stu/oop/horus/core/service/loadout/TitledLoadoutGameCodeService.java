package ua.cn.stu.oop.horus.core.service.loadout;

import ua.cn.stu.oop.horus.core.service.GenericService;
import ua.cn.stu.oop.horus.core.service.text.TitleLinkCarrierService;
import ua.cn.stu.oop.horus.core.domain.loadout.TitledLoadoutGameCode;

/**
 *
 * @author alex
 */
public interface TitledLoadoutGameCodeService
        extends GenericService<TitledLoadoutGameCode>,
        TitleLinkCarrierService<TitledLoadoutGameCode> {
}
