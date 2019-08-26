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

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setRecyclerView();

          new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
              @Override
              public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                  return false;
              }

              @Override
              public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                  final int pos = viewHolder.getAdapterPosition();

                  showConfirmationMessage(R.string.confirm, R.string.delete, R.string.yes,
                          new MaterialDialog.SingleButtonCallback() {
                              @Override
                              public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                  TodoModel model = adapter.getTodoAt(pos);
                                  DeleteTodoThread dtt = new DeleteTodoThread(model);
                                  dtt.start();
                                  adapter.removeTodo(pos);


                              }
                          }, R.string.no, new MaterialDialog.SingleButtonCallback() {
                              @Override
                              public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                  dialog.dismiss();
                                  adapter.notifyItemChanged(pos);
                              }
                          });
              }
          }).attachToRecyclerView(recyclerView);


    }

    private void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, AddTodo.class));
                finish();
            }
        });

    }

    private void setRecyclerView() {
        recyclerView = findViewById(R.id.todo_recyclerview);
        adapter = new TodoAdapter(null);
        layoutManager = new LinearLayoutManager(activity);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
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
          showConfirmationMessage(R.string.confirm, R.string.delete_all_todos, R.string.yes,
                  new MaterialDialog.SingleButtonCallback() {
                      @Override
                      public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                          deleteAll();
                      }
                  }, R.string.no, new MaterialDialog.SingleButtonCallback() {
                      @Override
                      public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                          dialog.dismiss();
                      }
                  });

        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteAll() {
        DeleteAlltodosThread datt = new DeleteAlltodosThread();
        datt.start();
        adapter.deleteallTodos();
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
                        adapter.setItemClickListener(new TodoAdapter.onItemClickListener() {
                            @Override
                            public void onItemClick(TodoModel model) {
                                DataHolderForTodo.currentTodo=model;
                                startActivity(new Intent(activity,Show_Item_Detail.class));
                            }
                        });
                    }
                });
                satt.start();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
