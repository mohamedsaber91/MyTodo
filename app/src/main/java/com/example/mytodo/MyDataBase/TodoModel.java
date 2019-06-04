package com.example.mytodo.MyDataBase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class TodoModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    int id;

    @ColumnInfo
    String title;

    @ColumnInfo
    String content;

    @ColumnInfo
    String tododate;

    public TodoModel() {
    }

    @Ignore
    public TodoModel(String title, String content, String tododate) {
        this.title = title;
        this.content = content;
        this.tododate = tododate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTododate() {
        return tododate;
    }

    public void setTododate(String tododate) {
        this.tododate = tododate;
    }
}
