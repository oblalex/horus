package ua.cn.stu.oop.horus.core.service.territory;

import ua.cn.stu.oop.horus.core.domain.territory.Airfield;
import ua.cn.stu.oop.horus.core.service.GenericService;
import ua.cn.stu.oop.horus.core.service.text.TitleLinkCarrierService;
import ua.cn.stu.oop.horus.core.service.type.TypeCarrierService;

/**
 *
 * @author alex
 */
public interface AirfieldService
        extends GenericService<Airfield>,
        TitleLinkCarrierService<Airfield>,
        NodeCarrierService<Airfield>,
        TypeCarrierService<Airfield> {
}
