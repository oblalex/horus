package ua.cn.stu.oop.horus.core.domain.type;

/**
 *
 * @author alex
 */
public interface TypeCarrier<T extends EntityType> {

    public T getType();

    public void setType(T type);
}
