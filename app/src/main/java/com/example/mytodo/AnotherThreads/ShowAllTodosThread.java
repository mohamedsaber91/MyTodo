package com.example.mytodo.AnotherThreads;

import com.example.mytodo.MyDataBase.MyDataBaseManger;
import com.example.mytodo.MyDataBase.TodoModel;

import java.util.List;

public class ShowAllTodosThread extends Thread {
    OnPreperedShowingTodosListener showingTodosListener;
    public ShowAllTodosThread(OnPreperedShowingTodosListener preperedShowingTodosListener){
        this.showingTodosListener = preperedShowingTodosListener;

    }

    @Override
    public void run() {
        super.run();
        List<TodoModel> models = MyDataBaseManger.getInstance()
                .todoDao().showAllTodos();
        showingTodosListener.onShowTodo(models);
    }

    public interface OnPreperedShowingTodosListener{
        void onShowTodo(List<TodoModel> todoModels);
    }
}
