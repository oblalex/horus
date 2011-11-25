package ua.cn.stu.oop.horus.core.service.user.group;

import ua.cn.stu.oop.horus.core.service.GenericService;
import ua.cn.stu.oop.horus.core.service.type.TypeCarrierService;
import ua.cn.stu.oop.horus.core.service.user.UserCarrierService;
import ua.cn.stu.oop.horus.core.domain.user.group.GroupEnterRequest;

/**
 *
 * @author alex
 */
public interface GroupEnterRequestService
        extends GenericService<GroupEnterRequest>,
        TypeCarrierService<GroupEnterRequest>,
        UserCarrierService<GroupEnterRequest>,
        GroupLinkCarrierService<GroupEnterRequest> {
}
