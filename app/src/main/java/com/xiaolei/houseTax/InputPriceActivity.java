package com.xiaolei.houseTax;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;

public class InputPriceActivity extends AppCompatActivity {
    public int version;
    public int totalPrice;//总价
    public int prePrice;//房屋原值
    public int yearsBought; //几年前购买的
    public int lowestPrice; //最低过户价
    public int bOnly = 0;//weiyi
    public int bFirst = 0;

    private EditText inputTotalPrice;
    private EditText inputPrePrice;
    private EditText inputYears;
    private EditText inputLowPrice;
    private CheckBox checkBoxOnly;
    private CheckBox checkBoxFirst;
    private MyLeanCloudApp myLeanCloud ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        myLeanCloud = new MyLeanCloudApp();
//        myLeanCloud.onCreate();
        setContentView(R.layout.activity_input_price);

        version = 0x0001;

        // 测试 SDK 是否正常工作的代码
        myAVOSUpload testObject = new myAVOSUpload("startup_record");
        String []userId=new String[3];
        String []IdInfo=new String[3];

        userId[0] = "PhoneType";
        IdInfo[0] = android.os.Build.MODEL;
        userId[1] = "SN";
        IdInfo[1] = android.os.Build.SERIAL;

        userId[2] = "Version";
        IdInfo[2]=version+"";

        testObject.putDataArray(userId, IdInfo);

        inputTotalPrice = (EditText) findViewById(R.id.inputTotalPrice);
        inputPrePrice = (EditText) findViewById(R.id.inputOldPrice);
        inputYears = (EditText) findViewById(R.id.inputYears);
        inputLowPrice = (EditText) findViewById(R.id.inputLowPrice);
        checkBoxOnly = (CheckBox) findViewById(R.id.checkBoxOnly);

        checkBoxOnly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    bOnly = 1;
                    Log.d("main", "checked!");
                } else {
                    bOnly = 0;
                }
            }
        });
        checkBoxFirst = (CheckBox) findViewById(R.id.checkBoxFirst);
        checkBoxFirst.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    bFirst = 1;
                } else {
                    bFirst = 0;
                }
            }
        });
    }

        //开始计算
        public void startCalPay(View v){
            totalPrice = Integer.parseInt(inputTotalPrice.getText().toString());
            prePrice = Integer.parseInt(inputPrePrice.getText().toString());
            yearsBought = Integer.parseInt(inputYears.getText().toString());
            lowestPrice = Integer.parseInt(inputLowPrice.getText().toString());

            Intent intent = new Intent(InputPriceActivity.this, startCalHouseActivity.class);
            intent.putExtra("totalPrice", totalPrice);
            intent.putExtra("yearsBought", yearsBought);
            if (yearsBought < 5) {
                intent.putExtra("prePrice", prePrice);
                intent.putExtra("lowPrice", lowestPrice);
            }
            intent.putExtra("only", bOnly);
            intent.putExtra("first", bFirst);
            startActivity(intent);
        }

        public void showHelp (View v){
            Intent intent = new Intent(InputPriceActivity.this, helpActivity.class);
            startActivity(intent);
        }



}

