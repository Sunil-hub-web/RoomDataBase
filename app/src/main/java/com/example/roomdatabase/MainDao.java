package com.example.roomdatabase;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MainDao {

    //Insert query
    @Insert(onConflict = REPLACE)
    void insert(MainData mainData);

    //Delete Query
    @Delete
    void delete(MainData mainData);

    //Delete all query
    @Delete
    void reset(List<MainData> mainDataList);

    //Updat Query
    @Query("UPDATE table_name SET text = :sText WHERE ID = :sID")
    void update(int sID,String sText);

    //Get all data query
    @Query("SELECT * FROM table_name")
    List<MainData> getAll();


}
