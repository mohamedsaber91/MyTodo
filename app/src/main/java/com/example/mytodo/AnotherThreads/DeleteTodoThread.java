package com.example.mytodo.AnotherThreads;

import com.example.mytodo.MyDataBase.MyDataBaseManger;
import com.example.mytodo.MyDataBase.TodoModel;

public class DeleteTodoThread extends Thread {
    TodoModel todoModel;
    public DeleteTodoThread(TodoModel todoModel){
        this.todoModel = todoModel;
    }

    @Override
    public void run() {
        super.run();
        MyDataBaseManger.getInstance()
                .todoDao()
                .deleteTodo(todoModel);
    }
}
