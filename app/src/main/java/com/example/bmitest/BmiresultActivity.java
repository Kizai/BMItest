package com.example.bmitest;

import androidx.appcompat.app.AppCompatActivity;
import pl.droidsonroids.gif.GifImageView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zia.toastex.ToastEx;

public class BmiresultActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmiresult);
        Button button = findViewById(R.id.button2);
        TextView textView = findViewById(R.id.textView);
        GifImageView gifImageView = findViewById(R.id.gifimage_show);
        Intent intent = getIntent();
        TextView textView1 = findViewById(R.id.textView2);
        //运动建议
        Button btn_sport = findViewById(R.id.btn_sport);
        btn_sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sportDialog();
            }
        });
        //不服，再测一下
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BmiresultActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        //接收身高体重
        double hei = intent.getDoubleExtra("heiNum", 0.0);//身高
        double wei = intent.getDoubleExtra("weiNum", 0.0);//体重
        //传递BMI的数值进行判断
        double Bmi = intent.getDoubleExtra("bmiValue", 0.0);//接收bmi值
        String sex = intent.getStringExtra("bmisex");//接收性别
        String advice = "";
        //进行比较得出结论
        if (Bmi <= 18.4) {
            advice = getString(R.string.thin);
            gifImageView.setImageResource(R.drawable.ps);
        } else if (Bmi >= 18.5 && Bmi <= 23.9) {
            advice = getString(R.string.slim);
            gifImageView.setImageResource(R.drawable.zc);
        } else if (Bmi >= 24 && Bmi <= 27.9) {
            advice = getString(R.string.fat);
            gifImageView.setImageResource(R.drawable.sr);
        } else if (Bmi >= 28 && Bmi <= 39) {
            advice = getString(R.string.fater);
            gifImageView.setImageResource(R.drawable.fp);
        } else if (Bmi >= 40) {
            advice = getString(R.string.fatest);
            gifImageView.setImageResource(R.drawable.gz);
        }
        textView.setText(sex + ",您的BMI值是：" + Bmi + "," + advice);
        textView1.setText("您的身高：" + hei + "cm" + "\n您的体重：" + wei + "kg" + "\n测试结果如下");

        setTitle("测试结果");
    }

    //运动建议的dialog
    private void sportDialog() {
        final TextView tv_sport = (TextView) findViewById(R.id.tv_sport);
        final String[] sports = new String[]{"篮球", "跑步", "爬山", "游泳", "不想运动"};
        //创建构建器
        final AlertDialog.Builder builder = new AlertDialog.Builder(BmiresultActivity.this);
        //设置参数
        builder.setIcon(R.drawable.ic_motorcycle_black_24dp).setTitle("请选择你喜欢的运动")
                .setSingleChoiceItems(sports, 0, new DialogInterface.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //选择什么显示什么
                        ToastEx.success(BmiresultActivity.this, sports[which]).show();
                        switch (sports[which]) {
                            case "篮球":
                                tv_sport.setText("你选择的是:" + sports[which] + "\n打篮球的好处："+"\n打篮球可以增强体内营养物质的消耗，使整个肌体的代谢能力增强，进而提高食欲。另外，还会促进胃肠蠕动和消化液分泌，改善肝脏和胰腺的功能，" +
                                        "从而使整个消化系统的功能得到提高，为人的健康和长寿提供良好的物质d保证。");
                                break;
                            case "跑步":
                                tv_sport.setText("你选择的是:" + sports[which] + "\n跑步的好处：" + "\n经常跑步可以增强人体的柔韧度，保护心脏，通过跑步增强了人体各部位的抗损伤能力，同时提高供血量，对心脏大脑的健康都有好处，" +
                                        "可以缓解疲劳、消除紧张感、消除焦虑，抑郁的情绪，通过锻炼，人的精神可以得到振奋，心情会更加的开朗，对学习，工作都有一定好处。");
                                break;
                            case "爬山":
                                tv_sport.setText("你选择的是:" + sports[which] + "\n爬山的好处：" + "\n减肥最基本的道理是消耗的热量大于吸收的热量，运动是人体最主要的能量消耗渠道，但并不是说参加什么运动都可以取得良好减肥作用。理想的减肥运动方式是强度较低的运动，" +
                                        "由于供氧充分，持续时间长，总的能量消耗多。爬山正是这样一种运动强度适宜，持续时间较长的运动方式，因而具有独到的减肥效果。");
                                break;
                            case "游泳":
                                tv_sport.setText("你选择的是:" + sports[which] + "\n游泳的好处：" + "\n1、健身的作用。游泳是一项比较消耗体力的活动，它能够提高身体的体能，长期游泳可以使自己保持很好的体力。2、有减肥的作用。游泳，它的体力消耗量比较大，" +
                                        "可以消耗我们体内的脂肪，能够通过游泳来进行塑身、减肥。");
                                break;
                            case "不想运动":
                                tv_sport.setText("你选择的是:" + sports[which] + "\n不想运动的后果：" + "\n世界卫生组织最新统计数字表明，每年全球有两百万人死于“身体缺乏活动”，所以，为了自己，也为爱你的人和你爱的人，赶快运动起来，哪怕是最简单的步行!不要为了便捷而放弃健康，放弃生命｡");
                                break;
                        }
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*switch (sports[which]) {
                    case "篮球":
                        tv_sport.setText("你选择的是:" + sports[which] + "\n打篮球的好处：\n"+R.string.bask);
                        break;
                    case "跑步":
                        tv_sport.setText("你选择的是:" + sports[which] + "\n跑步的好处：\n" + R.string.run);
                        break;
                    case "爬山":
                        tv_sport.setText("你选择的是:" + sports[which] + "\n爬山的好处：\n" + R.string.shan);
                        break;
                    case "游泳":
                        tv_sport.setText("你选择的是:" + sports[which] + "\n游泳的好处：\n" + R.string.swing);
                        break;
                    case "不想运动":
                        tv_sport.setText("你选择的是:" + sports[which] + "\n不想运动的后果：\n" + R.string.nosport);
                        break;
                }*/
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.create().dismiss();//关闭
            }
        });
        builder.create().show();
    }
}

