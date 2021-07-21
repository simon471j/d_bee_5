package com.example.d_bee_5;

import android.content.Context;
import android.content.SharedPreferences;

public  class PasswordManager {
    static final String ACCOUNT = "account";
    static final String REMEMBERED_USERNAME = "rememberedUsername";
    static final String REMEMBERED_PASSWORD = "rememberedPassword";
    Context context;
    private final SharedPreferences.Editor editor;
    private final SharedPreferences sharedPreferences;

    public PasswordManager(Context context) {
        this.context = context;
        editor = context.getSharedPreferences(ACCOUNT, Context.MODE_PRIVATE).edit();
        sharedPreferences = context.getSharedPreferences(ACCOUNT, Context.MODE_PRIVATE);
    }



//判断sharedPreferences是否存储了账户密码
    Boolean isEmpty() {
        return sharedPreferences.getString(REMEMBERED_USERNAME, null)==null&&sharedPreferences.getString(REMEMBERED_PASSWORD, null)==null;
    }

//    获取账户
    Account getAccount() {
        String username = sharedPreferences.getString(REMEMBERED_USERNAME, null);
        String password = sharedPreferences.getString(REMEMBERED_PASSWORD, null);
        return new Account(username, password);

    }
// 设置账户
    void setAccount(Account account) {
        editor.putString(REMEMBERED_USERNAME, account.username);
        editor.putString(REMEMBERED_PASSWORD, account.password);
        editor.apply();
    }
// 清除 （用于用户在checkBox取消了记住密码）
    void clear() {
        editor.remove(REMEMBERED_USERNAME);
        editor.remove(REMEMBERED_PASSWORD);
        editor.apply();
    }
}
