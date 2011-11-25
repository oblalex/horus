package ua.cn.stu.oop.horus.core.dao.hibernateImpl.gameflow;

import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.gameflow.WeatherReportDao;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.gameflow.WeatherReport;

/**
 *
 * @author alex
 */
@Repository("WeatherReportDaoHibernateImpl")
public class WeatherReportDaoHibernateImpl
        extends GenericDaoHibernateImpl<WeatherReport>
        implements WeatherReportDao {

    public WeatherReportDaoHibernateImpl() {
        super(WeatherReport.class);
    }
}
