package com.example.mytodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mytodo.AnotherThreads.InsertTodoThread;
import com.example.mytodo.Base.BaseActivity;
import com.example.mytodo.MyDataBase.TodoModel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddTodo extends BaseActivity implements View.OnClickListener {

    protected TextInputLayout tdoTitle;
    protected TextInputLayout tdoContent;
    protected Button addTodo;
    protected String title, content, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_add_todo);
        initView();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.add_todo) {
             getNewTodoDataAndAdd();

        }
    }




    private void getNewTodoDataAndAdd() {
        // take title from user
        title = tdoTitle.getEditText().getText().toString();
        if (title.trim().length() == 0) {
            tdoTitle.setError(getString(R.string.require));
            return ;
        }
        tdoTitle.setError(null);

        //take content from user
        content = tdoContent.getEditText().getText().toString();
        if (content.trim().length() == 0) {
            tdoContent.setError(getString(R.string.require));
            return ;
        }
        tdoContent.setError(null);

        // set date
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy / MM / dd // hh:mm");
        date = simpleDateFormat.format(new Date());

       TodoModel todo =  new TodoModel(title,content,date);
                //add thread
        InsertTodoThread itt = new InsertTodoThread(todo);
        itt.start();

        Toast.makeText(activity, "successful adding", Toast.LENGTH_SHORT).show();
        finish();
        startActivity(new Intent(activity,MainActivity.class));
    }



    private void initView() {
        tdoTitle =  findViewById(R.id.tdo_title);
        tdoContent =  findViewById(R.id.tdo_content);
        addTodo =  findViewById(R.id.add_todo);
        addTodo.setOnClickListener(AddTodo.this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }
}
