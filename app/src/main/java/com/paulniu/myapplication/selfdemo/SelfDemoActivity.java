package com.paulniu.myapplication.selfdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.paulniu.myapplication.R;

import java.util.List;

/**
 * Coder: niupuyue
 * Date: 2019/7/17
 * Time: 14:38
 * Desc:这里的demo是以oracle数据库中的经典案例emp表为例子所有的一系列操作，具体的操作内容，可以看下面的内容
 * Version:
 */
public class SelfDemoActivity extends AppCompatActivity {

    private Button btn01;
    private Button btn02;
    private Button btn03;
    private Button btnClear;

    private TextView showMsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_demo);

        showMsg = findViewById(R.id.showMsg);
        btn01 = findViewById(R.id.btn01);
        btn02 = findViewById(R.id.btn02);
        btn03 = findViewById(R.id.btn03);
        btnClear = findViewById(R.id.btnClear);

        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Emp> emps = SqliteDatabaseHelper.getInstance().queryAllEmp();
                String msg = "";
                for (Emp emp : emps) {
                    msg += emp.toString() + "\n";
                }
                List<Dept> depts = SqliteDatabaseHelper.getInstance().queryAllDept();
                for (Dept dept : depts) {
                    msg += dept.toString() + "\n";
                }
                showMsg.setText(msg);
            }
        });

        btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "";
                msg = msg + "平均工资：" + SqliteDatabaseHelper.getInstance().averageSal() + "\n" + "最高工资：" + SqliteDatabaseHelper.getInstance().highestSal() + "\n" + "最低工资：" + SqliteDatabaseHelper.getInstance().lowestSal();
                showMsg.setText(msg);
            }
        });

        btn03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "";
                List<Emp> emp = SqliteDatabaseHelper.getInstance().getEmpsForDeptName("大堂");
                for (Emp em : emp) {
                    msg += em.toString() + "\n";
                }
                showMsg.setText(msg);
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SqliteDatabaseHelper.getInstance().clearData();
            }
        });

    }


}
