package ua.cn.stu.oop.horus.core.service.impl.file.aop;

import java.sql.Timestamp;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import ua.cn.stu.oop.horus.core.domain.file.DBFile;

/**
 *
 * @author alex
 */
@Component
@Aspect
public class ModificationTimeAutoUpdateAspect {
    
    @Pointcut("execution(* ua.cn.stu.oop.horus.core.service.impl..*.save*(..))")
    public void pointcutExecution() {
    }
    
    @Before("pointcutExecution() && args(entity)")
    public void beforePointcut(DBFile entity){
        entity.setLastModificationTime(new Timestamp(System.currentTimeMillis()));
    }
}
