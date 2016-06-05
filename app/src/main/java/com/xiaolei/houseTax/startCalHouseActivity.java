package com.xiaolei.houseTax;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class startCalHouseActivity extends AppCompatActivity {

    public int noTaxYear=5;
    public int noYingyeTaxYear=2;


    private int totalPrice;//总价
    private int prePrice;//房屋原值
    private int yearsBought; //几年前购买的
    private int lowestPirce; //最低过户价
    private int bonly;
    private int bfirst;
    //再次输入，为了合理避税：
    private int shoufu;//首付款


    //所有税费结果
    private float wangqianPrice;//网签价格
    private float qishui;//契税 总房款的1%
    private float yingyeshui;//营业税 5%
    private float gerensuodeshui; //个人所得税 差额20%
    private float totalTax; //个人所得税 差额20%

    private String TAG="计算付款页面";

    private EditText editTextInputShoufu;

    private TextView textWangqianPrice;
    private TextView textTotalTax;
    private TextView textqishui;
    private TextView idyingyeTax;
    private TextView idgerensuode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_cal_house);

        Intent intent = getIntent();
        totalPrice = intent.getIntExtra("totalPrice", 0);
        prePrice = intent.getIntExtra("prePrice", 0);
        yearsBought = intent.getIntExtra("yearsBought", 0);
        lowestPirce = intent.getIntExtra("lowPrice", 0);
        bonly = intent.getIntExtra("only", 0);
        bfirst = intent.getIntExtra("first", 0);
        editTextInputShoufu = (EditText)findViewById(R.id.inputShoufu);
        //result

        textWangqianPrice = (TextView)findViewById(R.id.WangqianId);
        textTotalTax = (TextView)findViewById(R.id.textTotalTax);
        textqishui = (TextView)findViewById(R.id.qishui);
        idyingyeTax = (TextView)findViewById(R.id.idyingyeTax);
        idgerensuode = (TextView)findViewById(R.id.idgerensuode);


        Log.d(TAG, "输入的总价为："+totalPrice+"年限： "+yearsBought);
    }

    private void updateUI(){
        textWangqianPrice.setText(wangqianPrice+"");
        textTotalTax.setText(""+totalTax);
        textqishui.setText(qishui+"");
        idyingyeTax.setText(yingyeshui+"");
        idgerensuode.setText(gerensuodeshui+"");

        myAVOSUpload priceUploader = new myAVOSUpload("Price");
        String []item=new String[5];
        item[0]="shoufu";
        item[1]="TotalPrice";
        item[2]="prePrice";
        item[3]="wangqianjia";
        item[4] = "years";

        Integer[] price=new Integer[5];
        price[0]=shoufu;
        price[1]=totalPrice;
        price[2]=prePrice;
        price[3]=(int)wangqianPrice;
        price[4]=yearsBought;

        priceUploader.putDataArray(item,price);
    }

    private  void showALertMoneyNotEnough(int leak){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("首付不够哦！");
        dialog.setMessage("首付还差"+leak);
        dialog.show();
    }

    //计算需要的所有税率
    public void reCalPay(View v){
        int daikuan,maxTotalPrice;//maxTotalPrice是在银行批贷时的报的总价
        double maxDaikuan;

        double rateDaikuan;
        if (bfirst==1){
            rateDaikuan = 0.7;
        }else{
            rateDaikuan = 0.6;
        }
        shoufu = Integer.parseInt(editTextInputShoufu.getText().toString());

        daikuan = (totalPrice-shoufu);
        maxDaikuan = totalPrice*0.9*rateDaikuan;

        if (maxDaikuan+shoufu<totalPrice){
            Log.e(TAG,"你的首付不够哦！！！！");
            showALertMoneyNotEnough(totalPrice-((int) maxDaikuan+shoufu));
            return;
        }

        maxTotalPrice = daikuan*10/7;
        if (maxTotalPrice>lowestPirce){
            wangqianPrice = maxTotalPrice;
        }else{
            wangqianPrice = lowestPirce;
        }

        qishui = wangqianPrice/100;
        gerensuodeshui = 0;
        yingyeshui = 0;

        if (yearsBought>=noTaxYear&&bonly==1){

        }else{
            if (yearsBought<noYingyeTaxYear){
                yingyeshui = (float)0.05*(wangqianPrice);
            }
            if (wangqianPrice<=prePrice){

            }else{
                gerensuodeshui = (wangqianPrice-prePrice)/5;
                Log.d(TAG, "有营业税："+yingyeshui);
            }

        }
        totalTax = yingyeshui+gerensuodeshui+qishui;
        Log.d(TAG, "营业税："+yingyeshui);
        updateUI();


        return;
    }


}
