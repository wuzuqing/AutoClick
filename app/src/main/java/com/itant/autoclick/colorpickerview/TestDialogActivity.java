//package com.itant.autoclick.activity;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.media.ThumbnailUtils;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.text.TextUtils;
//import android.view.View;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.CheckBox;
//import android.widget.EditText;
//
//import com.itant.autoclick.R;
//import com.itant.autoclick.activity.BaseApplication;
//import com.itant.autoclick.util.CmdData;
//import com.itant.autoclick.util.HandlerUtil;
//import com.itant.autoclick.util.SimilarPicture;
//
///**
// * @author 吴祖清
// * @version $Rev$
// * @createTime 2017/12/23 19:11
// * @des ${TODO}
// * @updateAuthor $Author$
// * @updateDate 2017/12/23$
// * @updateDes ${TODO}
// */
//
//public class TestDialogActivity extends NoAnimatorActivity {
//    private EditText etX;
//    private EditText etY;
//    private View ivTips;
//    private CheckBox mCheckBox;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.dialog_setting_test);
//        etX = (EditText) findViewById(R.id.etX);
//        mCheckBox = (CheckBox) findViewById(R.id.checkbox);
//        etY = (EditText) findViewById(R.id.etY);
//        ivTips =  findViewById(R.id.ivTips);
//
//        Bitmap bitmap8 = ThumbnailUtils.extractThumbnail(null, 8, 8);
//        Bitmap bitmap = SimilarPicture.convertGreyImg(bitmap8);
//        int avg = SimilarPicture.getAvg(bitmap);
//        String binary = SimilarPicture.getBinary(bitmap, avg);
//        String hexString = SimilarPicture.binaryString2hexString(binary);
//
//        initData();
//    }
//
//    private void initData() {
//        etX.setText(String.valueOf(CmdData.textX));
//        etY.setText(String.valueOf(CmdData.textY));
//    }
//
//
//    private int getFloat(EditText editText, int defValue) {
//        if (TextUtils.isEmpty(editText.getText())) return defValue;
//        try {
//            return Integer.parseInt(editText.getText().toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return defValue;
//    }
//
//    public void onClick(View view) {
//        hideSoftKeyboard(etX);
//        int x =getFloat(etX,680);
//        int y =getFloat(etY,120);
//        ivTips.setX(x);
//        ivTips.setY(y - BaseApplication.getStatusBarHeight());
//
//        if (mCheckBox.isChecked()){
//            CmdData.setTestSwipe(x,y);
//        }else{
//            CmdData.setTest(x,y);
//        }
//
//        HandlerUtil.post(new Runnable(){
//            @Override
//            public void run() {
//                finish();
//            }
//        },800);
//
//    }
//
//
//    /**
//     * 隐藏软键盘
//     *
//     * @param view
//     */
//    public static void hideSoftKeyboard(View view) {
//        Context context = view.getContext();
//        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//    }
//
//}
