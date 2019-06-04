package com.example.mytodo.MyDataBase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TodoDao {
    @Insert
    public void insertTodo(TodoModel todo);

    @Delete
    public void deleteTodo(TodoModel todo);

    @Query("delete  from TodoModel")
    public void deleteAll();

    @Query("select * from TodoModel")
    public List<TodoModel> showAllTodos();

    @Query("select * from TodoModel where id= :t_id")
    public TodoModel showTodoByTitle(int t_id);
}
