package com.paulniu.myapplication;


import java.util.Date;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Coder: niupuyue
 * Date: 2019/7/15
 * Time: 17:41
 * Desc:
 * Version:
 */
public class MyMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        // 获取一个realm的动态Schema对象
        RealmSchema schema = realm.getSchema();
        // 这里面我们需要根据当前的数据库版本进行不同的操作
        // 如果旧版本是0，则我们需要创建Person的表，并且创建Field name和age
//        if (oldVersion == 0) {
//            schema.create("Person")
//                    .addField("name", String.class)
//                    .addField("age", int.class);
//            oldVersion++;
//        }
        // 如果旧版本是1
        // 那我们我们需要获取到person表格，添加一个字段id，并且是long类型，而且是主键
        // 创建RealmObject类型，字段是favoriteDog,他的类型是Dog(我们是通过schema.get("Dog")拿到表结构对象)
        // 创建一个RealmList的字段，名称叫做dogs，他的类型时Dog
//        if (oldVersion == 1) {
//            schema.get("Person")
//                    .addField("id", long.class, FieldAttribute.PRIMARY_KEY)
//                    .addRealmObjectField("favoriteDog", schema.get("Dog"))
//                    .addRealmListField("dogs", schema.get("Dog"));
//            oldVersion++;
//        }
        // 升级版本测试
        if (oldVersion == 2) {
            schema.get("Emp")
                    .removeField("depno")
                    .addRealmObjectField("depno", schema.get("Dept"));
        }
        if (oldVersion == 3) {
            schema.create("Student")
                    .addField("name", String.class)
                    .addField("id", long.class)
                    .addField("age", int.class)
                    .addField("school", String.class)
                    .addField("address", String.class)
                    .addField("joindate", Date.class)
                    .isPrimaryKey("id");
        }
    }
}
