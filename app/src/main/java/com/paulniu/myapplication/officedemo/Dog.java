package com.paulniu.myapplication.officedemo;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Coder: niupuyue
 * Date: 2019/7/15
 * Time: 12:01
 * Desc:
 * Version:
 */
public class Dog extends RealmObject {
    @PrimaryKey
    private long id;

    private String name;

    private int age;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
