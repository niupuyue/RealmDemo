package com.paulniu.myapplication.callbackdemo;

import android.os.Handler;
import android.os.HandlerThread;

import com.niupuyue.mylibrary.utils.TimeUtility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Coder: niupuyue
 * Date: 2019/7/17
 * Time: 16:36
 * Desc:
 * Version:
 */
public class SQLiteHelper {

    private static class SQLiteHelperImpl {
        private static final SQLiteHelper INSTANCE = new SQLiteHelper();
    }

    public static SQLiteHelper getInstance() {
        return SQLiteHelperImpl.INSTANCE;
    }

    private Realm realm;

    Handler mHander;

    private SQLiteHelper() {
        // 初始化数据
        final List<Student> students = new ArrayList<>();
        Student st1 = new Student();
        st1.setId(1001);
        st1.setName("小明");
        st1.setAddress("海淀区");
        st1.setAge(22);
        st1.setSchool("清华大学");
        st1.setJoindate(new Date(TimeUtility.convertToLong("2011-09-03 08:00:00", TimeUtility.TIME_FORMAT)));
        students.add(st1);

        Student st2 = new Student();
        st2.setId(1002);
        st2.setName("小红");
        st2.setAddress("房山区");
        st2.setAge(26);
        st2.setSchool("北京大学");
        st2.setJoindate(new Date(TimeUtility.convertToLong("2012-09-03 08:00:00", TimeUtility.TIME_FORMAT)));
        students.add(st2);

        Student st3 = new Student();
        st3.setId(1003);
        st3.setName("小花");
        st3.setAddress("通州区");
        st3.setAge(30);
        st3.setSchool("北航");
        st3.setJoindate(new Date(TimeUtility.convertToLong("2009-09-03 08:00:00", TimeUtility.TIME_FORMAT)));
        students.add(st3);

        Student st4 = new Student();
        st4.setId(1004);
        st4.setName("小飞");
        st4.setAddress("丰台区");
        st4.setAge(26);
        st4.setSchool("首都师范大学");
        st4.setJoindate(new Date(TimeUtility.convertToLong("2018-12-03 08:00:00", TimeUtility.TIME_FORMAT)));
        students.add(st4);

        if (realm == null) {
            realm = Realm.getDefaultInstance();
        }

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(students);
            }
        });

        HandlerThread thread = new HandlerThread("realm");
        thread.start();
        mHander = new Handler(thread.getLooper());

    }

    /**
     * 获取所有的学生
     */
    public void getStudents(final ICallback callback) {
        if (callback == null) return;
        if (realm == null) {
            realm = Realm.getDefaultInstance();
        }
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Student> temp = realm.where(Student.class).findAll();
                List<Student> students = realm.copyFromRealm(temp);
                callback.getStudents(students);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callback.onSuccess();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                callback.onError();
            }
        });
    }

    /**
     * 异步获取所有学生 TODO 这个是错误的
     */
    public void getStudentsAsync(final ICallback callback) {
        if (callback == null) return;
        if (realm == null) {
            realm = Realm.getDefaultInstance();
        }
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(final Realm realm) {
                RealmResults<Student> ss = realm.where(Student.class).findAllAsync();
//                ss.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<Student>>() {
//                    @Override
//                    public void onChange(RealmResults<Student> students, OrderedCollectionChangeSet changeSet) {
//                        List<Student> res = realm.copyFromRealm(students);
//                        callback.getStudents(res);
//                    }
//                });
                ss.addChangeListener(new RealmChangeListener<RealmResults<Student>>() {
                    @Override
                    public void onChange(RealmResults<Student> students) {
                        List<Student> res = realm.copyFromRealm(students);
                        callback.getStudents(res);
                    }
                });
            }
        });
    }


    interface ICallback {
        void getStudents(List<Student> datas);

        void onSuccess();

        void onError();
    }
}
