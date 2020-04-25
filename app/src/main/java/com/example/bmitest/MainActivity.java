package com.example.bmitest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zia.toastex.ToastEx;
import com.zia.toastex.anim.ToastImage;
import com.zia.toastex.anim.ToastText;

public class MainActivity extends AppCompatActivity {
    private EditText et_height, et_weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //绑定控件
        et_height = findViewById(R.id.editText);
        et_weight = findViewById(R.id.editText2);
        Button btn_submit = findViewById(R.id.button);
        ImageView back = findViewById(R.id.imageView);
        final ImageView sex_show = findViewById(R.id.imageView2);
        RadioButton rtn_m = findViewById(R.id.radioButton);
        final RadioButton rtn_w = findViewById(R.id.radioButton2);

        //内部匿名类
        //开始计算
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            public void onClick(View v) {
                //让它输入只能是数字键盘，但是测试无效
/*                et_height.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
                et_weight.setInputType(EditorInfo.TYPE_CLASS_NUMBER);*/
                //获取身高、体重文本
                String height = et_height.getText().toString().trim();
                String weight = et_weight.getText().toString().trim();
                //初始化身高、体重、结果
                double res = 0, heightNum = 0, weightNum = 0;

                //定义性别为男
                String bmisex = getString(R.string.callman);

                //判断性别女按钮点击事件
                //如果点击赋值为美铝
                if (rtn_w.isChecked()) {
                    bmisex = getString(R.string.callwoman);
                    sex_show.setImageResource(R.drawable.lady);
                } else {
                    bmisex = getString(R.string.callman);
                    sex_show.setImageResource(R.drawable.men);
                }

                //判断输入身高、体重不为空
                if (!height.isEmpty() && !weight.isEmpty()) {
                    //转换为小数
                    heightNum = Double.parseDouble(height);
                    weightNum = Double.parseDouble(weight);
                    //判断身高输入是否为0
                    if (heightNum == 0) {
                        //定义一个带图片的Toast,一定要改成getApplicationContext()在
                        @SuppressLint("ShowToast") Toast toastH = Toast.makeText(getApplicationContext(), R.string.toast_zero, Toast.LENGTH_SHORT);//身高为除数不能为0
                        toastH.setGravity(Gravity.CENTER, 0, 0);
                        LinearLayout linearLayout = (LinearLayout) toastH.getView();
                        ImageView imageView = new ImageView(getApplicationContext());
                        imageView.setImageResource(R.drawable.ic_close_black_24dp);
                        linearLayout.addView(imageView, 0);
                        toastH.show();
                    } else {
                        //判断体重输入是否为0
                        if (weightNum == 0) {
                            //默认
//                            Toast.makeText(getApplicationContext(), R.string.toast_zero1, Toast.LENGTH_SHORT).show();//体重不能为
                            ToastEx.error(MainActivity.this,"体重不能为0").show();//使用Toast插件实现
                        } else {
                            res = (weightNum * 10000) / (heightNum * heightNum);//BMI计算公式：体重/身高^2，因为原单位是M，单位改成cm之后放大10000倍
                            res = (double) Math.round(res * 10) / 10;//保留一位小数
                            Intent intent = new Intent(MainActivity.this, BmiresultActivity.class);
                            intent.putExtra("weiNum", weightNum);//传输身高
                            intent.putExtra("heiNum", heightNum);//传输体重
                            intent.putExtra("bmiValue", res);//传输BMI结果
                            intent.putExtra("bmisex", bmisex);//传输性别
                            startActivity(intent);
                        }
                    }
                } else
                    mToast();//用自己自定义的Toast警告
//                    Toast.makeText(MainActivity.this, R.string.toast_warming, Toast.LENGTH_SHORT).show();//警告提示
            }
        });
        //关于BMI指数
        Button btn_BMI = findViewById(R.id.btn_BMI);
        btn_BMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMessageDiaolog();
            }
        });
        //关于作者
        Button btn_kizai = findViewById(R.id.btn_kizai);
        btn_kizai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setView();
            }
        });
        //退出程序的按钮
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //创建dialog提示框
    private void setMessageDiaolog() {
        //创建构建器
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //设置参数
        builder.setTitle("什么是BMI").setIcon(R.drawable.bmi)
                .setMessage(R.string.adout_bmi)
                .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast toast = Toast.makeText(getApplicationContext(), "感谢查看", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.BOTTOM, 0, 0);
                        LinearLayout linearLayout = (LinearLayout) toast.getView();
                        ImageView imageView = new ImageView(getApplicationContext());
                        imageView.setImageResource(R.drawable.ic_sentiment_very_satisfied_black_24dp);
                        linearLayout.addView(imageView, 0);
                        toast.show();
                    }
                });
        builder.create().show();
    }

    //创建setView()设置对话框为自定义View
    private void setView() {
        //创建对话框构建器
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //获取布局
        View view = View.inflate(MainActivity.this, R.layout.aboutme, null);
        //获取布局中的控件一定要加上view（view.findViewById），不然会闪退
        //更新按钮
        final Button btn_updata = view.findViewById(R.id.btn_updata);
        btn_updata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 ToastEx.warning(MainActivity.this,"已经是最新版本").show();
//                Toast.makeText(getApplicationContext(), R.string.updata, Toast.LENGTH_SHORT).show();
            }
        });
        //打分控件
        final RatingBar ratingBar = view.findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                //改变Toast位置
                Toast toast = Toast.makeText(getApplicationContext(), String.valueOf(v) + "星评价", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 200);
                toast.show();

            }
        });
        //设置参数
        builder.setTitle(R.string.adoutBMI).setView(view)
                .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //定义一个带图片的Toast
                        Toast toast = Toast.makeText(MainActivity.this, "感谢查看与评价", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.BOTTOM, 0, 0);
                        LinearLayout linearLayout = (LinearLayout) toast.getView();
                        ImageView imageView = new ImageView(getApplicationContext());
                        imageView.setImageResource(R.drawable.ic_mood_black_24dp);
                        linearLayout.addView(imageView, 0);
                        toast.show();
                    }
                });
        //创建对话框
        builder.create().show();//不能少这句
    }

    //定义一个完全自定义的mToast
    private void mToast() {
        //创建构建器
        LayoutInflater mlif = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        //绑定自定义的布局界面
        assert mlif != null;
        @SuppressLint("InflateParams") View view = mlif.inflate(R.layout.diy_toast, null);
        //绑定控件
        TextView tv_toast = view.findViewById(R.id.tv_toast);
        SpannableString ss = new SpannableString("非法输入！请重新输入！");
        //改变字体颜色
        ss.setSpan(new ForegroundColorSpan(Color.BLUE), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(Color.RED), 5, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //显示文字
        tv_toast.setText(ss);
        Toast myToast = new Toast(this);
        myToast.setView(view);
        myToast.setGravity(Gravity.CENTER, 0, 0);
        myToast.show();//不能少这个
    }

}
