package com.paulniu.myapplication.callbackdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.niupuyue.mylibrary.utils.CustomToastUtility;
import com.paulniu.myapplication.R;

import java.util.List;

/**
 * Coder: niupuyue
 * Date: 2019/7/17
 * Time: 16:29
 * Desc:
 * Version:
 */
public class CallbackActivity extends AppCompatActivity {

    private TextView tvShow;
    private Button callback01;
    private Button callback02;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callback_demo);
        tvShow = findViewById(R.id.tvCallbackShowMsg);

        callback01 = findViewById(R.id.callback01);
        callback02 = findViewById(R.id.callback02);

        callback01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteHelper.getInstance().getStudents(new SQLiteHelper.ICallback() {
                    @Override
                    public void getStudents(List<Student> datas) {
                        String msg = "";
                        for (Student ss : datas) {
                            msg += ss.toString() + "\n";
                        }
                        tvShow.setText(msg);
                    }

                    @Override
                    public void onSuccess() {
                        CustomToastUtility.makeTextSucess("获取成功");
                    }

                    @Override
                    public void onError() {
                        CustomToastUtility.makeTextError("获取失败");
                    }
                });
            }
        });

        callback02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteHelper.getInstance().getStudentsAsync(new SQLiteHelper.ICallback() {
                    @Override
                    public void getStudents(List<Student> datas) {
                        String msg = "";
                        for (Student ss : datas) {
                            msg += ss.toString() + "\n";
                        }
                        tvShow.setText(msg);
                    }

                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
            }
        });

    }
}
