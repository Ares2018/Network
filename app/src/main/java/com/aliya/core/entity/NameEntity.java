package com.aliya.core.entity;

/**
 * name entity
 *
 * @author a_liYa
 * @date 2017/12/29 21:13.
 */
public class NameEntity {

    private String name;

    //空的构造函数必须要有，具体原因可以百度下

    public NameEntity() {
    }

    public NameEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
