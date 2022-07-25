package com.gnhd.base.umeng;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Surface;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.umverify.UMVerifyHelper;

import static com.gnhd.base.umeng.AppUtils.dp2px;


public abstract class BaseUIConfig implements AuthPageConfig {
    public Activity mActivity;
    public Context mContext;
    public UMVerifyHelper mAuthHelper;
    public int mScreenWidthDp;
    public int mScreenHeightDp;
//
//    public static AuthPageConfig init(Constant.UI_TYPE type, Activity activity, UMVerifyHelper authHelper) {
//        switch (type) {
////            case FULL_PORT:
////                return new FullPortConfig(activity, authHelper);
////            case FULL_LAND:
////                return new FullLandConfig(activity, authHelper);
////            case DIALOG_PORT:
////                return new DialogPortConfig(activity, authHelper);
////            case DIALOG_LAND:
////                return new DialogLandConfig(activity, authHelper);
////            case DIALOG_BOTTOM:
////                return new DialogBottomConfig(activity, authHelper);
////            case CUSTOM_VIEW:
////                return new CustomViewConfig(activity, authHelper);
//            case CUSTOM_XML:
//                return new CustomXmlConfig(activity, authHelper);
//            default:
//                return null;
//        }
//    }

    public BaseUIConfig(Activity activity, UMVerifyHelper authHelper) {
        mActivity = activity;
        mContext = activity.getApplicationContext();
        mAuthHelper = authHelper;
    }

    protected View initSwitchView(int marginTop) {
        TextView switchTV = new TextView(mContext);
        RelativeLayout.LayoutParams mLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, dp2px(mContext, 50));
        //一键登录按钮默认marginTop 270dp
        mLayoutParams.setMargins(0, dp2px(mContext, marginTop), 0, 0);
        mLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        switchTV.setText("切换到短信登录方式");
        switchTV.setTextColor(Color.BLACK);
        switchTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13.0F);
        switchTV.setLayoutParams(mLayoutParams);
        return switchTV;
    }

    protected void updateScreenSize(int authPageScreenOrientation) {
        int screenHeightDp = AppUtils.px2dp(mContext, AppUtils.getPhoneHeightPixels(mContext));
        int screenWidthDp = AppUtils.px2dp(mContext, AppUtils.getPhoneWidthPixels(mContext));
        int rotation = mActivity.getWindowManager().getDefaultDisplay().getRotation();
        if (authPageScreenOrientation == ActivityInfo.SCREEN_ORIENTATION_BEHIND) {
            authPageScreenOrientation = mActivity.getRequestedOrientation();
        }
        if (authPageScreenOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                || authPageScreenOrientation == ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
                || authPageScreenOrientation == ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE) {
            rotation = Surface.ROTATION_90;
        } else if (authPageScreenOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                || authPageScreenOrientation == ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
                || authPageScreenOrientation == ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT) {
            rotation = Surface.ROTATION_180;
        }
        switch (rotation) {
            case Surface.ROTATION_0:
            case Surface.ROTATION_180:
                mScreenWidthDp = screenWidthDp;
                mScreenHeightDp = screenHeightDp;
                break;
            case Surface.ROTATION_90:
            case Surface.ROTATION_270:
                mScreenWidthDp = screenHeightDp;
                mScreenHeightDp = screenWidthDp;
                break;
            default:
                break;
        }
    }


    /**
     *  在横屏APP弹竖屏一键登录页面或者竖屏APP弹横屏授权页时处理特殊逻辑
     *  Android8.0只能启动SCREEN_ORIENTATION_BEHIND模式的Activity
     */
    @Override
    public void onResume() {
//       new  XPopup.Builder(mContext)
//                        .isDestroyOnDismiss(true) //
//                            .popupAnimation(PopupAnimation.ScaleAlphaFromCenter)
//                            .asConfirm(
//                                    "提示", "隐私协议测试弹框",
//                                    "取消", "去认证", new OnConfirmListener() {
//                                        @Override
//                                        public void onConfirm() {
//
//                                        }
//                                }, null, false
//                            ).show();

    }

    @Override
    public void release() {
        mAuthHelper.setAuthListener(null);
        mAuthHelper.setUIClickListener(null);
        mAuthHelper.removeAuthRegisterViewConfig();
        mAuthHelper.removeAuthRegisterXmlConfig();
    }
}
