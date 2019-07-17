package com.paulniu.myapplication.selfdemo;

import com.niupuyue.mylibrary.utils.TimeUtility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Coder: niupuyue
 * Date: 2019/7/17
 * Time: 14:58
 * Desc:
 * Version:
 */
public class SqliteDatabaseHelper {

    private static class SqliteDatabaseHeplerImp {
        private static final SqliteDatabaseHelper INSTANCE = new SqliteDatabaseHelper();
    }

    public static SqliteDatabaseHelper getInstance() {
        return SqliteDatabaseHeplerImp.INSTANCE;
    }

    private Realm realm;

    private List<Emp> empResult = new ArrayList<>();
    private List<Dept> deptResult = new ArrayList<>();

    private List<Emp> emps = new ArrayList<>();
    private List<Dept> depts = new ArrayList<>();

    /**
     * 构造方法，完成数据的初始化
     */
    private SqliteDatabaseHelper() {
        if (null == realm) {
            realm = Realm.getDefaultInstance();
        }
        insertData();
    }

    /**
     * 初始化数据
     */
    private void insertData() {
        initEmps();
        initDept();

        if (null != realm) {
            // 插入员工信息
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(emps);
                    realm.insertOrUpdate(depts);
                }
            });
        }
    }

    private void initEmps() {

        Dept dept1 = new Dept();
        dept1.setDeptno(10);
        dept1.setDname("大堂");
        dept1.setLoc("石家庄");

        Dept dept2 = new Dept();
        dept2.setDeptno(20);
        dept2.setDname("后厨");
        dept2.setLoc("天津");

        Dept dept3 = new Dept();
        dept3.setDeptno(30);
        dept3.setDname("总经办");
        dept3.setLoc("北京");

        Emp emp1 = new Emp();
        emp1.setEmpno(1001);
        emp1.setEname("小七");
        emp1.setJob("服务员");
        emp1.setMgr(1006);
        emp1.setDepno(dept1);
        emp1.setSal(1500);
        emp1.setHiredate(new Date(TimeUtility.convertToLong("2011-06-07 09:00:00", TimeUtility.TIME_FORMAT)));

        Emp emp2 = new Emp();
        emp2.setEmpno(1002);
        emp2.setEname("小六");
        emp2.setJob("帮厨");
        emp2.setMgr(1005);
        emp2.setDepno(dept2);
        emp2.setSal(1600);
        emp2.setHiredate(new Date(TimeUtility.convertToLong("2009-10-15 16:30:00", TimeUtility.TIME_FORMAT)));

        Emp emp3 = new Emp();
        emp3.setEmpno(1003);
        emp3.setEname("小五");
        emp3.setJob("服务员");
        emp3.setMgr(1006);
        emp3.setDepno(dept1);
        emp3.setSal(1500);
        emp3.setHiredate(new Date(TimeUtility.convertToLong("2010-11-01 12:29:00", TimeUtility.TIME_FORMAT)));

        Emp emp4 = new Emp();
        emp4.setEmpno(1004);
        emp4.setEname("小四");
        emp4.setJob("收银");
        emp4.setMgr(1007);
        emp4.setDepno(dept3);
        emp4.setSal(3500);
        emp4.setHiredate(new Date(TimeUtility.convertToLong("2015-01-01 12:29:00", TimeUtility.TIME_FORMAT)));

        Emp emp5 = new Emp();
        emp5.setEmpno(1005);
        emp5.setEname("小三");
        emp5.setJob("大厨");
        emp5.setMgr(1007);
        emp5.setDepno(dept2);
        emp5.setSal(5000);
        emp5.setHiredate(new Date(TimeUtility.convertToLong("2018-05-01 12:29:00", TimeUtility.TIME_FORMAT)));

        Emp emp6 = new Emp();
        emp6.setEmpno(1006);
        emp6.setEname("小二");
        emp6.setJob("经理");
        emp6.setMgr(1007);
        emp6.setDepno(dept3);
        emp6.setSal(5500);
        emp6.setHiredate(new Date(TimeUtility.convertToLong("2008-03-29 08:00:00", TimeUtility.TIME_FORMAT)));

        Emp emp7 = new Emp();
        emp7.setEmpno(1007);
        emp7.setEname("小一");
        emp7.setJob("老板");
        emp7.setMgr(1007);
        emp7.setDepno(dept3);
        emp7.setSal(10000);
        emp7.setHiredate(new Date(TimeUtility.convertToLong("2000-01-01 08:00:00", TimeUtility.TIME_FORMAT)));

        emps.add(emp1);
        emps.add(emp2);
        emps.add(emp3);
        emps.add(emp4);
        emps.add(emp5);
        emps.add(emp6);
        emps.add(emp7);
    }

    private void initDept() {
        Dept dept1 = new Dept();
        dept1.setDeptno(10);
        dept1.setDname("大堂");
        dept1.setLoc("石家庄");

        Dept dept2 = new Dept();
        dept2.setDeptno(20);
        dept2.setDname("后厨");
        dept2.setLoc("天津");

        Dept dept3 = new Dept();
        dept3.setDeptno(30);
        dept3.setDname("总经办");
        dept3.setLoc("北京");

        depts.add(dept1);
        depts.add(dept2);
        depts.add(dept3);
    }

    /**
     * 查询所有的雇员信息
     */
    public List<Emp> queryAllEmp() {
        if (null != realm) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<Emp> emps = realm.where(Emp.class).findAll();
                    empResult = realm.copyFromRealm(emps);
                }
            });
        }
        return empResult;
    }

    /**
     * 查询所有的部门信息
     */
    public List<Dept> queryAllDept() {
        if (null != realm) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<Dept> depts = realm.where(Dept.class).findAll();
                    deptResult = realm.copyFromRealm(depts);
                }
            });
        }
        return deptResult;
    }

    /**
     * 查询所有员工的平均工资，最高工资，最低工资
     */
    double sal = 0;

    public double averageSal() {
        if (null != realm) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    sal = realm.where(Emp.class).average("sal");
                }
            });
        }
        return sal;
    }

    double high = 0;

    public double highestSal() {
        if (null != realm) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    high = (double) realm.where(Emp.class).max("sal");
                }
            });
        }
        return high;
    }

    Number low = 0;

    public double lowestSal() {
        if (null != realm) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    low = realm.where(Emp.class).min("sal");
                }
            });
        }
        return low.doubleValue();
    }

    /**
     * 获取部门编号是20的所有员工的信息
     */
    public List<Emp> getEmpsForDeptno(final long deptno) {
        if (null != realm) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<Emp> ee = realm.where(Emp.class).equalTo("depno", deptno).findAll();
                    empResult = realm.copyFromRealm(ee);
                }
            });
        }
        return empResult;
    }

    /**
     * 部门名称为"大堂"的所有员工的信息
     */
    public List<Emp> getEmpsForDeptName(final String name) {
        if (null != realm) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<Emp> ee = realm.where(Emp.class).equalTo("depno.dname", name).findAll();
                    empResult = realm.copyFromRealm(ee);
                }
            });
        }
        return empResult;
    }


    /**
     * 清除所有数据
     */
    public void clearData() {
        if (null != realm) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<Emp> emp1 = realm.where(Emp.class).findAll();
                    emp1.deleteAllFromRealm();

                    RealmResults<Dept> dept1 = realm.where(Dept.class).findAll();
                    dept1.deleteAllFromRealm();
                }
            });
        }
    }

}
