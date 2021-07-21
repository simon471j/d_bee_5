package com.example.d_bee_5;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Account.class},version = 1,exportSchema = false)
public abstract class AccountDatabase extends RoomDatabase {
    private static final String DB_NAME = "account_db";
    private static AccountDatabase accountDatabase;

    public static synchronized AccountDatabase getAccountDatabase(Context context) {
        if (accountDatabase == null) {
            accountDatabase = Room.databaseBuilder(context.getApplicationContext(), AccountDatabase.class, DB_NAME).build();
        }
        return accountDatabase;
    }

    public abstract AccountDao accountDao();
}
