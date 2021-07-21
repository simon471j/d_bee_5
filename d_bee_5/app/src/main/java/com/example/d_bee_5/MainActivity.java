package com.example.d_bee_5;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {


    private Button login;
    private Button register;
    private CheckBox checkBox;
    private TextInputEditText inputUsername;
    private TextInputEditText inputPassword;
    private AccountDatabase accountDatabase;
    PasswordManager passwordManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        创建用于操作记住密码功能的类
        passwordManager = new PasswordManager(this);
        login = findViewById(R.id.btn_login);
        register = findViewById(R.id.btn_register);
        inputUsername = findViewById(R.id.tiet_username);
        inputPassword = findViewById(R.id.tiet_password);
        checkBox = findViewById(R.id.cb_remember_password);
//        检查是否记住密码 如果是 初始化账号密码
        initAccount();
        //获取数据库操作实例
        accountDatabase = AccountDatabase.getAccountDatabase(this);

        // 登陆事件
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputUsername.getText() != null) {
                    Observable.create(new ObservableOnSubscribe<Account>() {
                        @Override
                        public void subscribe(@NotNull ObservableEmitter<Account> emitter) throws Exception {
        //从数据库中查找有无该账号
                            Account account = accountDatabase.accountDao().getAccountByUsername(inputUsername.getText().toString());
                            if (account != null) {
//                                如果有则发射
                                emitter.onNext(account);
                            } else
//                                如果没有 报错 并且会toast提示用户
                                emitter.onError(new Exception("没有查询到账号"));
                        }
                    }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Account>() {
                        @Override
                        public void onSubscribe(@NotNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NotNull Account account) {


                            if (account.password.equals(Objects.requireNonNull(inputPassword.getText()).toString())) {
                                Intent intent = new Intent(MainActivity.this, MyRecyclerView.class);
//                                页面的跳转
                                startActivity(intent);
                                Toast.makeText(MainActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
//                                检查用户是否想存储账户
                                if (checkBox.isChecked()){
                                    passwordManager.setAccount(new Account(inputUsername.getText().toString(), inputPassword.getText().toString()));
                                }
                                if (!checkBox.isChecked())
                                    passwordManager.clear();
                                finish();

                            } else
                                Toast.makeText(MainActivity.this, "账号和密码不符", Toast.LENGTH_SHORT).show();
                        }


                        @Override
                        public void onError(@NotNull Throwable e) {
                            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
                } else
                    Toast.makeText(MainActivity.this, "输入为空", Toast.LENGTH_SHORT).show();
            }
        });


        //注册事件
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputUsername.getText() != null && inputPassword.getText() != null) {

                    Observable.create(new ObservableOnSubscribe<Object>() {
                        @Override
                        public void subscribe(@NotNull ObservableEmitter<Object> emitter) throws Exception {
//                            在数据库中检查时候有重复账户 若有则提示用户 用户名被注册
                            Account account = accountDatabase.accountDao().getAccountByUsername(inputUsername.getText().toString());
                            if (account != null)
                                emitter.onError(new Exception("该账户已被注册"));

                            else {
                                accountDatabase.accountDao().insertAccount(new Account(inputUsername.getText().toString(), inputPassword.getText().toString()));
                                emitter.onNext("注册成功");
                            }
                        }
                    }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Object>() {
                        @Override
                        public void onSubscribe(@NotNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NotNull Object o) {
                            Toast.makeText(MainActivity.this, o.toString(), Toast.LENGTH_SHORT).show();
                        }


                        @Override
                        public void onError(@NotNull Throwable e) {
                            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            Log.d("onError", "onError: " + e.toString());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });


                } else Toast.makeText(MainActivity.this, "输入为空", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initAccount() {
        if (!passwordManager.isEmpty()){
            checkBox.setChecked(true);
            Account rememberedAccount = passwordManager.getAccount();
            inputUsername.setText(rememberedAccount.username);
            inputPassword.setText(rememberedAccount.password);
        }else checkBox.setChecked(false);
    }


}