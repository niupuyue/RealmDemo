package com.paulniu.myapplication.selfdemo;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Coder: niupuyue
 * Date: 2019/7/17
 * Time: 14:57
 * Desc:
 * Version:
 */
public class Dept extends RealmObject {

    @PrimaryKey
    private long deptno;

    // 部门名称
    private String dname;

    // 部门所在位置
    private String loc;

    public long getDeptno() {
        return deptno;
    }

    public void setDeptno(long deptno) {
        this.deptno = deptno;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    @Override
    public String toString() {
        return "Dept{" +
                "deptno=" + deptno +
                ", dname='" + dname + '\'' +
                ", loc='" + loc + '\'' +
                '}';
    }
}
