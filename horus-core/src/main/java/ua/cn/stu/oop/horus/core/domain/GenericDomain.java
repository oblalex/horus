package ua.cn.stu.oop.horus.core.domain;

import java.io.Serializable;

/**
 *
 * @author alex
 */
abstract public class GenericDomain implements Serializable{

    private Long id;

    public GenericDomain() {
    }
    
    public GenericDomain(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
