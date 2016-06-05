package com.xiaolei.houseTax;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;

/**
 * Created by Administrator on 2016/5/29.
 */
public class myAVOSUpload extends AVObject {

    private String TAG ="MyAVOS";


    public myAVOSUpload(String objName){
        super(objName);
    }

    public void putDataArray(String[] index, Object[] value){
        for (int i=0; i<index.length; i++){
            super.put(index[i],value[i]);
            Log.d(TAG, "Upload "+index[i]+" "+value[i]);
        }

        super.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e == null){
                    Log.d(TAG, "success!");
                }else{
                    Log.e(TAG, "上传云端出错");
                }
            }
        });
    }

    public void putData(String index, Object value){
        super.put(index,value);
        Log.d(TAG, "Upload"+index+" "+value);

        super.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e == null){
                    Log.d(TAG, "success!");
                }
            }
        });
    }
}
