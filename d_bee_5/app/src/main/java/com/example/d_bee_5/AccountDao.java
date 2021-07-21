package com.example.d_bee_5;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface AccountDao {
    @Insert
    void insertAccount(Account account);

    @Query("SELECT * FROM account WHERE username = :username")
    Account getAccountByUsername(String username);
}
