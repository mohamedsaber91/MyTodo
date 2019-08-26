package com.example.mytodo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mytodo.Base.BaseActivity;

public class Show_Item_Detail extends BaseActivity {

    protected TextView detailTitle;
    protected TextView detailContent;
    protected TextView detailDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_show__item__detail);
        initView();
        onShowDetail();

    }

    private void onShowDetail() {
           detailTitle.setText(DataHolderForTodo.currentTodo.getTitle());
           detailContent.setText(DataHolderForTodo.currentTodo.getContent());
           detailDate.setText(DataHolderForTodo.currentTodo.getTododate());
    }

    private void initView() {
        detailTitle =  findViewById(R.id.detail_title);
        detailContent =  findViewById(R.id.detail_content);
        detailDate =  findViewById(R.id.detail_date);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(activity,MainActivity.class));
    }
}
