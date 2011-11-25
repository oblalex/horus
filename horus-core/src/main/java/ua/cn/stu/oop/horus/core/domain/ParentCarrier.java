package ua.cn.stu.oop.horus.core.domain;

/**
 *
 * @author alex
 */
public interface ParentCarrier<P extends GenericDomain> {

    public P getParent();

    public void setParent(P parent);
}
