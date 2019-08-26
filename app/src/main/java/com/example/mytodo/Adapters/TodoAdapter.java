package com.example.mytodo.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mytodo.MyDataBase.TodoModel;
import com.example.mytodo.R;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    List<TodoModel> list;
    onItemClickListener itemClickListener;

    public void setItemClickListener(onItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public TodoAdapter(List<TodoModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.todo_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
         final TodoModel model = list.get(position);
         viewHolder.title.setText(model.getTitle());
         viewHolder.date.setText(model.getTododate());
         if (itemClickListener != null){
             viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     itemClickListener.onItemClick(model);
                 }
             });
         }
    }

    public void onDataChanged(List<TodoModel> todos){
        this.list = todos;
    }

    public TodoModel getTodoAt(int pos){
       return this.list.get(pos);
    }

    public void deleteallTodos(){
        list.clear();
        notifyDataSetChanged();
    }

    public void removeTodo(int pos){
        list.remove(pos);
        notifyItemRemoved(pos);
    }

    @Override
    public int getItemCount() {
        if (list == null) return 0;
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.view_title);
            date = itemView.findViewById(R.id.view_date);
        }
    }

    public interface onItemClickListener{
        public void onItemClick(TodoModel model);
    }
}
