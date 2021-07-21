package com.example.d_bee_5;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "account")
public class Account {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    public int id;

    @ColumnInfo(name = "username", typeAffinity = ColumnInfo.TEXT)
    public String username;

    @ColumnInfo(name = "password", typeAffinity = ColumnInfo.TEXT)
    public String password;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @NonNull
    @NotNull
    @Override
    public String toString() {
        return username+" "+password;
    }
}
