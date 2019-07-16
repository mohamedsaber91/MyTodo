package com.example.mytodo.AnotherThreads;

import com.example.mytodo.MyDataBase.MyDataBaseManger;

public class DeleteAlltodosThread extends Thread {
    public DeleteAlltodosThread(){

    }

    @Override
    public void run() {
        super.run();
        MyDataBaseManger.getInstance()
                .todoDao().deleteAll();
    }
}
