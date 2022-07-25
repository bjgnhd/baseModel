package com.gnhd.base.umeng;

/**
 * <pre>
 *     author : Administrator
 *     time   : 2022/05/07
 *     desc   :
 * </pre>
 */
public interface OnUVerifyLoginListener {
    void showDialog();
    void dismissDialog();
    void openLoginPage();
    void loginByOne(String token);
    void finishSplashPage();
}
