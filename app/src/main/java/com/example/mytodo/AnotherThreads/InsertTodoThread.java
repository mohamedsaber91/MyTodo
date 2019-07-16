package com.example.mytodo.AnotherThreads;

import com.example.mytodo.MyDataBase.MyDataBaseManger;
import com.example.mytodo.MyDataBase.TodoModel;

public class InsertTodoThread extends Thread {
    TodoModel todoModel;
    public InsertTodoThread(TodoModel todoModel){
        this.todoModel= todoModel;
    }

    @Override
    public void run() {
        super.run();
        MyDataBaseManger.getInstance()
                .todoDao().insertTodo(todoModel);
    }
}
