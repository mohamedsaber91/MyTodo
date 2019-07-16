package com.example.mytodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mytodo.Adapters.TodoAdapter;
import com.example.mytodo.AnotherThreads.DeleteAlltodosThread;
import com.example.mytodo.AnotherThreads.DeleteTodoThread;
import com.example.mytodo.AnotherThreads.ShowAllTodosThread;
import com.example.mytodo.Base.BaseActivity;
import com.example.mytodo.MyDataBase.MyDataBaseManger;
import com.example.mytodo.MyDataBase.TodoModel;

import java.util.List;

public class MainActivity extends BaseActivity {

    protected RecyclerView recyclerView;
    protected TodoAdapter adapter;
    protected RecyclerView.LayoutManager layoutManager;
    protected static final String DTITLE = "title";
    protected static final String DCONTENT = "content";
    protected static final String DTODODATE = "tododate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.todo_recyclerview);
        adapter = new TodoAdapter(null);
        layoutManager = new LinearLayoutManager(activity);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add new Todo", Snackbar.LENGTH_LONG)
                        .setAction("Add", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(activity, AddTodo.class));
                            }
                        }).show();
            }
        });

        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
        adapter.setItemClickListener(clickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            deleteAll();
            finish();
            startActivity(new Intent(activity,AddTodo.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteAll() {
        DeleteAlltodosThread datt = new DeleteAlltodosThread();
        datt.start();
    }


    @Override
    protected void onResume() {
        super.onResume();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ShowAllTodosThread satt = new ShowAllTodosThread(new ShowAllTodosThread.OnPreperedShowingTodosListener() {
                    @Override
                    public void onShowTodo(List<TodoModel> todoModels) {
                        adapter.onDataChanged(todoModels);
                    }
                });
                satt.start();
            }
        });
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            //delete thread
            TodoModel model = adapter.getTodoAt(viewHolder.getAdapterPosition());
            DeleteTodoThread dtt = new DeleteTodoThread(model);
            dtt.start();


        }
    };

    TodoAdapter.onItemClickListener clickListener = new TodoAdapter.onItemClickListener() {
        @Override
        public void onItemClick(TodoModel model) {
            Intent intent = new Intent(activity, Show_Item_Detail.class);
            intent.putExtra(DTITLE, model.getTitle());
            intent.putExtra(DCONTENT, model.getContent());
            intent.putExtra(DTODODATE, model.getTododate());
            startActivity(intent);
        }
    };
}
