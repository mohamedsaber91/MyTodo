package com.example.mytodo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mytodo.Base.BaseActivity;

import static com.example.mytodo.MainActivity.DCONTENT;
import static com.example.mytodo.MainActivity.DTITLE;
import static com.example.mytodo.MainActivity.DTODODATE;

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
        Intent intent = getIntent();
        detailTitle.setText(intent.getStringExtra(DTITLE));
        detailContent.setText(intent.getStringExtra(DCONTENT));
        detailDate.setText(intent.getStringExtra(DTODODATE));
    }

    private void initView() {
        detailTitle = (TextView) findViewById(R.id.detail_title);
        detailContent = (TextView) findViewById(R.id.detail_content);
        detailDate = (TextView) findViewById(R.id.detail_date);
    }
}
