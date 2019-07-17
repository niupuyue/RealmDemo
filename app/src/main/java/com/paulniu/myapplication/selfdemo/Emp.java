package com.paulniu.myapplication.selfdemo;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Coder: niupuyue
 * Date: 2019/7/17
 * Time: 14:51
 * Desc:
 * Version:
 */
public class Emp extends RealmObject {

    @PrimaryKey
    private long empno;

    private String ename;

    // 职位
    private String job;

    // 上级id
    private long mgr;

    // 入职时间
    private Date hiredate;

    // 薪水
    private double sal;

    //部门编号
    private Dept depno;

    public long getEmpno() {
        return empno;
    }

    public void setEmpno(long empno) {
        this.empno = empno;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public long getMgr() {
        return mgr;
    }

    public void setMgr(long mgr) {
        this.mgr = mgr;
    }

    public Date getHiredate() {
        return hiredate;
    }

    public void setHiredate(Date hiredate) {
        this.hiredate = hiredate;
    }

    public double getSal() {
        return sal;
    }

    public void setSal(double sal) {
        this.sal = sal;
    }

    public Dept getDepno() {
        return depno;
    }

    public void setDepno(Dept depno) {
        this.depno = depno;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "empno=" + empno +
                ", ename='" + ename + '\'' +
                ", job='" + job + '\'' +
                ", mgr=" + mgr +
                ", hiredate=" + hiredate +
                ", sal=" + sal +
                ", depno=" + depno +
                '}';
    }
}
