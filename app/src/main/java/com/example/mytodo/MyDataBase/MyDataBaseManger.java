package com.example.mytodo.MyDataBase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {TodoModel.class},version = 1,exportSchema = false)
public abstract class MyDataBaseManger extends RoomDatabase {
    private static MyDataBaseManger myDataBaseManger;
    public abstract TodoDao todoDao();
    private static final String TODODATABASE = "todo data base";

    public static MyDataBaseManger init(Context context){
        if (myDataBaseManger == null){
              myDataBaseManger = Room.databaseBuilder(context,
                      MyDataBaseManger.class,TODODATABASE)
                      .allowMainThreadQueries()
                      .fallbackToDestructiveMigration()
                      .build();
        }

        return myDataBaseManger;
    }

    public static MyDataBaseManger getInstance(){
        return myDataBaseManger;
    }
}
