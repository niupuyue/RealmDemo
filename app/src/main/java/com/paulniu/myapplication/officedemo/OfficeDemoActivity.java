package com.paulniu.myapplication.officedemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.paulniu.myapplication.R;

import java.util.Iterator;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Coder: niupuyue
 * Date: 2019/7/15
 * Time: 11:53
 * Desc:
 * Version:
 */
public class OfficeDemoActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_change_dog_age;
    private Button btn_add_dog;
    private Button btn_show_dog_info;
    private Button btn_delete_dog_info;

    private EditText et_dog_age;
    private EditText et_dog_name;

    private TextView tv_show_dog_info;

    private Realm realm;

    private RealmAsyncTask task;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office_demo);
        // 实例化realm
        realm = Realm.getDefaultInstance();
        btn_change_dog_age = findViewById(R.id.btn_change_dog_age);
        btn_change_dog_age.setOnClickListener(this);
        btn_add_dog = findViewById(R.id.btn_add_dog);
        btn_add_dog.setOnClickListener(this);
        btn_show_dog_info = findViewById(R.id.btn_show_dog_info);
        btn_show_dog_info.setOnClickListener(this);
        btn_delete_dog_info = findViewById(R.id.btn_delete_dog_info);
        btn_delete_dog_info.setOnClickListener(this);

        et_dog_age = findViewById(R.id.et_dog_age);
        et_dog_name = findViewById(R.id.et_dog_name);

        tv_show_dog_info = findViewById(R.id.tv_show_dog_info);
    }

    private void addPuppy() {
        final String age = et_dog_age.getText().toString().trim();
        final String name = et_dog_name.getText().toString().trim();
        if (TextUtils.isEmpty(age) || TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请输入小狗的年龄和名称", Toast.LENGTH_SHORT).show();
            return;
        }
        if (realm != null) {
            /**
             * 注意此处写入数据库的方式均为同步方式，异步方式在addPuppyAnsy()方法中
             */
            // 方法1
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Dog pupy = realm.createObject(Dog.class);
                    pupy.setName(name);
                    pupy.setAge(Integer.valueOf(age));
                }
            });
            // 方法2
//            realm.beginTransaction();
//            Dog dog = realm.createObject(Dog.class);
//            dog.setAge(Integer.valueOf(age));
//            dog.setName(name);
//            realm.commitTransaction();
            // 方法3
            Dog dog = new Dog();
            dog.setName(name);
            dog.setAge(Integer.valueOf(age));
            realm.beginTransaction();
            Dog dd = realm.copyToRealm(dog);
            realm.commitTransaction();
        }
    }

    private void addPuppyAnsy() {
        final String age = et_dog_age.getText().toString().trim();
        final String name = et_dog_name.getText().toString().trim();
        if (TextUtils.isEmpty(age) || TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请输入小狗的年龄和名称", Toast.LENGTH_SHORT).show();
            return;
        }
        if (realm != null) {
            task = realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    // 执行插入数据库操作
                    Dog dog = realm.createObject(Dog.class);
                    dog.setAge(Integer.valueOf(age));
                    dog.setName(name);
                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    // 插入数据库成功
                    Toast.makeText(OfficeDemoActivity.this, "数据插入成功", Toast.LENGTH_SHORT).show();
                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    // 插入数据库失败
                    Toast.makeText(OfficeDemoActivity.this, "数据插入失败", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void addPuppyAnsyWithKey() {
        String name = et_dog_name.getText().toString().trim();
        String age = et_dog_age.getText().toString().trim();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(age)) {
            Toast.makeText(this, "请输入小狗的年龄和姓名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (realm != null) {
            final Dog dog = new Dog();
            dog.setId(System.currentTimeMillis());
            dog.setName(name);
            dog.setAge(Integer.valueOf(age));
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(dog);
                    // 或者另外一种
//                    realm.insertOrUpdate(dog);
                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    Toast.makeText(OfficeDemoActivity.this, "插入成功！", Toast.LENGTH_SHORT).show();
                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    Toast.makeText(OfficeDemoActivity.this, "插入失败！", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void showPuppy() {
        final String name = et_dog_name.getText().toString().trim();
        final String age = et_dog_age.getText().toString().trim();
        if (TextUtils.isEmpty(name) && TextUtils.isEmpty(age)) {
            // 两个都为空，查找全部
            if (realm != null) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmResults<Dog> results = realm.where(Dog.class).findAll();
                        tv_show_dog_info.setText(results.toString());
                    }
                });
            }
        } else if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(age)) {
            if (realm != null) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmResults<Dog> results = realm.where(Dog.class).equalTo("name", name).equalTo("age", Integer.valueOf(age)).findAll();
                        tv_show_dog_info.setText(results.toString());
                    }
                });
            }
        } else if (!TextUtils.isEmpty(name) && TextUtils.isEmpty(age)) {
            if (realm != null) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmResults<Dog> results = realm.where(Dog.class).equalTo("name", name).findAll();
                        tv_show_dog_info.setText(results.toString());
                    }
                });
            }
        } else if (TextUtils.isEmpty(name) && !TextUtils.isEmpty(age)) {
            if (realm != null) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmResults<Dog> results = realm.where(Dog.class).equalTo("age", Integer.valueOf(age)).findAll();
                        tv_show_dog_info.setText(results.toString());
                    }
                });
            }
        }
    }

    private OrderedRealmCollectionChangeListener<RealmResults<Dog>> listener = new OrderedRealmCollectionChangeListener<RealmResults<Dog>>() {
        @Override
        public void onChange(RealmResults<Dog> dogs, OrderedCollectionChangeSet changeSet) {
            // 数据查询到了，更新数据

        }
    };

    private void queryAllPuppyAsync() {
        if (realm != null) {
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<Dog> dogs = realm.where(Dog.class).findAllAsync();
                    dogs.addChangeListener(listener);
                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    Toast.makeText(OfficeDemoActivity.this, "查询成功！", Toast.LENGTH_SHORT).show();
                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    Toast.makeText(OfficeDemoActivity.this, "查询成功！", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void queryAllPuppy() {
        if (realm != null) {
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<Dog> dogs = realm.where(Dog.class).findAll();
                    if (dogs.size() > 0) {
                        // 将最终的数据打印出来
                        String log = "";
                        Iterator it = dogs.iterator();
                        while (it.hasNext()) {
                            Dog dog = (Dog) it.next();
                            log += dog.getId() + "；" + dog.getName() + "；" + dog.getAge() + "\n";
                        }
                        tv_show_dog_info.setText(log);
                    }
                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    Toast.makeText(OfficeDemoActivity.this, "查询成功！", Toast.LENGTH_SHORT).show();
                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    Toast.makeText(OfficeDemoActivity.this, "查询成功！", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void queryLess3Puppy() {
        if (realm != null) {
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<Dog> dogs = realm.where(Dog.class).lessThan("age", 3).findAll();
                    if (dogs.size() > 0) {
                        // 将最终的数据打印出来
                        String log = "";
                        Iterator it = dogs.iterator();
                        while (it.hasNext()) {
                            Dog dog = (Dog) it.next();
                            log += dog.getId() + "；" + dog.getName() + "；" + dog.getAge() + "\n";
                        }
                        tv_show_dog_info.setText(log);
                    }
                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    Toast.makeText(OfficeDemoActivity.this, "查询成功！", Toast.LENGTH_SHORT).show();
                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    Toast.makeText(OfficeDemoActivity.this, "查询成功！", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    // 查找名字=aaa 的小狗
    private void queryEqualPuppy() {
        if (realm != null) {
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<Dog> dogs = realm.where(Dog.class).equalTo("name", "aaa").findAll();
                    if (dogs.size() > 0) {
                        // 将最终的数据打印出来
                        String log = "";
                        Iterator it = dogs.iterator();
                        while (it.hasNext()) {
                            Dog dog = (Dog) it.next();
                            log += dog.getId() + "；" + dog.getName() + "；" + dog.getAge() + "\n";
                        }
                        tv_show_dog_info.setText(log);
                    }
                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    Toast.makeText(OfficeDemoActivity.this, "查询成功！", Toast.LENGTH_SHORT).show();
                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    Toast.makeText(OfficeDemoActivity.this, "查询成功！", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void updatePuppy() {
        final String name = et_dog_name.getText().toString().trim();
        final String age = et_dog_age.getText().toString().trim();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(age)) {
            Toast.makeText(this, "请输入小狗的年龄和姓名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (realm != null) {
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    // 找到名字是输入框名字的小狗，把年龄修改成输入框的年龄
                    RealmResults<Dog> results = realm.where(Dog.class).equalTo("name", name).findAll();
                    if (results.size() > 0) {
                        // 找到的有数据
                        Iterator it = results.iterator();
                        while (it.hasNext()) {
                            Dog puppy = (Dog) it.next();
                            puppy.setAge(Integer.valueOf(age));
                        }
                    }
                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    Toast.makeText(OfficeDemoActivity.this, "更新成功！", Toast.LENGTH_SHORT).show();
                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    Toast.makeText(OfficeDemoActivity.this, "更新失败！", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void deletePuppy() {
        final String name = et_dog_name.getText().toString().trim();
        final String age = et_dog_age.getText().toString().trim();
        if (realm != null) {
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(age)) {
                        RealmResults<Dog> dogs = realm.where(Dog.class).equalTo("name", name).and().equalTo("age", Integer.valueOf(age)).findAll();
                        dogs.deleteAllFromRealm();
                    } else if (TextUtils.isEmpty(name) && TextUtils.isEmpty(age)) {
                        RealmResults<Dog> dogs = realm.where(Dog.class).findAll();
                        dogs.deleteAllFromRealm();
                    } else if (!TextUtils.isEmpty(name) && TextUtils.isEmpty(age)) {
                        RealmResults<Dog> dogs = realm.where(Dog.class).equalTo("name", name).findAll();
                        dogs.deleteAllFromRealm();
                    } else if (TextUtils.isEmpty(name) && !TextUtils.isEmpty(age)) {
                        RealmResults<Dog> dogs = realm.where(Dog.class).equalTo("age", age).findAll();
                        dogs.deleteAllFromRealm();
                    }
                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    Toast.makeText(OfficeDemoActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    Toast.makeText(OfficeDemoActivity.this, "删除失败！", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (task != null && !task.isCancelled()) {
            task.cancel();
        }
        if (realm != null && !realm.isClosed()) {
            realm.close();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_show_dog_info:
                // 显示信息
//                showPuppy();
//                queryAllPuppy();
                queryLess3Puppy();
                break;
            case R.id.btn_add_dog:
                // 添加信息
                addPuppyAnsyWithKey();
                break;
            case R.id.btn_change_dog_age:
                // 修改信息
                updatePuppy();
                break;
            case R.id.btn_delete_dog_info:
                // 删除小狗信息
                deletePuppy();
                break;
        }
    }
}
