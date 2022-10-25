package com.gnhd.base.umeng;

import android.content.Context;
import android.util.Log;

import com.umeng.umverify.UMResultCode;
import com.umeng.umverify.UMVerifyHelper;
import com.umeng.umverify.listener.UMTokenResultListener;
import com.umeng.umverify.model.UMTokenRet;

/**
 * <pre>
 *     author : Administrator
 *     time   : 2022/05/07
 *     desc   :
 * </pre>
 */
public class UVerifyLoginHelper {

    private UMTokenResultListener mTokenResultListener;
    AuthPageConfig mUIConfig;
    private UMVerifyHelper mPhoneNumberAuthHelper;
    private OnUVerifyLoginListener mListener;

    private UVerifyLoginHelper() {
    }

    public static UVerifyLoginHelper getInstance() {
        return SingletonHolder.S_INSTANCE;
    }

   private static class SingletonHolder {
        private static final UVerifyLoginHelper S_INSTANCE = new UVerifyLoginHelper();
    }

    public void init(Context context, String key,OnUVerifyLoginListener listener) {
        this.mListener = listener;
        mTokenResultListener = new UMTokenResultListener() {
            @Override
            public void onTokenSuccess(String s) {
                UMTokenRet tokenRet = null;
                try {
                    tokenRet = UMTokenRet.fromJson(s);
                    if (UMResultCode.CODE_START_AUTHPAGE_SUCCESS.equals(tokenRet.getCode())) {
                        Log.e("zts", "唤起授权页成功：" + s);
                        if (mListener != null) {
                            mListener.finishSplashPage();
                        }
                    }

                    if (UMResultCode.CODE_SUCCESS.equals(tokenRet.getCode())) {
                        Log.e("zts", "获取token成功：" + s);

                        if (mListener != null) {
                            mListener.loginByOne(tokenRet.getToken());
                        }
                        mUIConfig.release();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTokenFailed(String s) {
                Log.e("zts", "获取token失败：" + s);
                if (mListener != null) {
                    mListener.dismissDialog();
                }
                UMTokenRet tokenRet = null;
                mPhoneNumberAuthHelper.quitLoginPage();

                try {
                    tokenRet = UMTokenRet.fromJson(s);
                    if (UMResultCode.CODE_ERROR_USER_CANCEL.equals(tokenRet.getCode())) {
                        //因为不是必须登录，所以用户取消授权登录，直接关闭页面就好
                    } else {
                        if (mListener != null) {
                            mListener.openLoginPage();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mUIConfig.release();
            }
        };
        mPhoneNumberAuthHelper = UMVerifyHelper.getInstance(context, mTokenResultListener);
        //设置SDK密钥
        mPhoneNumberAuthHelper.setAuthSDKInfo(key);
    }

    public UMVerifyHelper getAuthHelper() {
        return mPhoneNumberAuthHelper;
    }

    public AuthPageConfig getUIConfig() {
        return mUIConfig;
    }

    public UVerifyLoginHelper setUIConfig(AuthPageConfig mUIConfig) {
        this.mUIConfig = mUIConfig;

        return this;
    }

    public void oneKeyLogin(Context context) {
        if (mListener != null) {
            mListener.showDialog();
        }
        mUIConfig.configAuthPage();
        mPhoneNumberAuthHelper.getLoginToken(context, 5000);
    }

    public OnUVerifyLoginListener getListener() {
        return mListener;
    }
}
