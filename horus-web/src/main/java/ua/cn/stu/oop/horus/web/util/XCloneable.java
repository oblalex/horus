package ua.cn.stu.oop.horus.web.util;

/**
 *
 * @author alex
 */
public interface XCloneable<T> {

    public T clone();

    public void cloneToObject(T o);
}
