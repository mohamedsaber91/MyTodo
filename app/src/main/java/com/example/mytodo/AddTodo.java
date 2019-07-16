package com.example.mytodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.mytodo.AnotherThreads.InsertTodoThread;
import com.example.mytodo.Base.BaseActivity;
import com.example.mytodo.MyDataBase.MyDataBaseManger;
import com.example.mytodo.MyDataBase.TodoModel;

import java.util.List;

public class AddTodo extends BaseActivity implements View.OnClickListener {

    protected TextInputLayout tdoTitle;
    protected TextInputLayout tdoContent;
    protected TextInputLayout tdoDate;
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

    MaterialDialog.SingleButtonCallback singleButtonCallback = new MaterialDialog.SingleButtonCallback() {
        @Override
        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
            dialog.dismiss();
            finish();
            startActivity(new Intent(activity,MainActivity.class));
        }
    };


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

        // get date from user
        date = tdoDate.getEditText().getText().toString();
        if (date.trim().length() == 0) {
            tdoDate.setError(getString(R.string.require));
            return;
        }
        tdoDate.setError(null);

       TodoModel todo =  new TodoModel(title,content,date);
                //add thread
        InsertTodoThread itt = new InsertTodoThread(todo);
        itt.start();

        showConfirmationMessage(R.string.success,
                R.string.new_todo_added,
                R.string.ok,
                singleButtonCallback);
    }



    private void initView() {
        tdoTitle = (TextInputLayout) findViewById(R.id.tdo_title);
        tdoContent = (TextInputLayout) findViewById(R.id.tdo_content);
        tdoDate = (TextInputLayout) findViewById(R.id.tdo_date);
        addTodo = (Button) findViewById(R.id.add_todo);
        addTodo.setOnClickListener(AddTodo.this);
    }
}
