package ua.cn.stu.oop.horus.core.service.Impl.gameflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.gameflow.WeatherReportDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.gameflow.WeatherReport;
import ua.cn.stu.oop.horus.core.service.Impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.gameflow.WeatherReportService;

/**
 *
 * @author alex
 */
@Service("weatherReportService")
public class WeatherReportServiceImpl
        extends GenericServiceImpl<WeatherReport, WeatherReportDaoHibernateImpl>
        implements WeatherReportService {

    @Autowired
    public WeatherReportServiceImpl(WeatherReportDaoHibernateImpl dao) {
        super(dao);
    }
}
