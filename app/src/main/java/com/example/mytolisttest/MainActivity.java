package com.example.mytolisttest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
//    android:background="@drawable/todo_item_bg"
public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TodoAdapter todoAdapter;
    private TextInputEditText todoEditText;
    private List<TodoItem> todoItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.todo_recyclerview);
        todoEditText = findViewById(R.id.todo_edittext);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        todoItems = new ArrayList<>();
        todoAdapter = new TodoAdapter(todoItems);
        recyclerView.setAdapter(todoAdapter);
        // Add button
        MaterialButton addButton = findViewById(R.id.add_todo_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoText = todoEditText.getText().toString().trim();
                if (!TextUtils.isEmpty(todoText)) {
                    TodoItem todoItem = new TodoItem(todoText);
                    todoItems.add(todoItem);
                    todoAdapter.notifyItemInserted(todoItems.size() - 1);
                    recyclerView.scrollToPosition(todoItems.size() - 1);
                    todoEditText.setText("");
                }
            }
        });
    }

    // Adapter for RecyclerView
    private class TodoAdapter extends RecyclerView.Adapter<TodoViewHolder> {
        private List<TodoItem> todoList;

        public TodoAdapter(List<TodoItem> todoList) {
            this.todoList = todoList;
        }

        @Override
        public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.todo_item_layout, parent, false);
            return new TodoViewHolder(view);
        }

        @Override
        public void onBindViewHolder(TodoViewHolder holder, int position) {
            // 获取当前任务项
            TodoItem todoItem = todoList.get(position);
            holder.bind(todoItem);

            // 获取任务项的CheckBox控件和TextView控件
            CheckBox checkBox = holder.itemView.findViewById(R.id.todo_item_checkbox);
            TextView textView = holder.itemView.findViewById(R.id.todo_item_textview);

            // 将任务项的文本设置到TextView控件中
            textView.setText(todoItem.getTodoText());

            // 设置任务项的CheckBox状态
            checkBox.setChecked(todoItem.isChecked());

            // 为任务项的CheckBox设置监听器
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // 更新当前任务项的状态
                    todoItem.setChecked(isChecked);
                }
            });

        }

        @Override
        public int getItemCount() {
            return todoList.size();
        }
    }

    // ViewHolder for RecyclerView
    private class TodoViewHolder extends RecyclerView.ViewHolder {
        private TextView todoTextView;

        public TodoViewHolder(View itemView) {
            super(itemView);
            todoTextView = itemView.findViewById(R.id.todo_item_textview);
        }

        public void bind(TodoItem todoItem) {
            todoTextView.setText(todoItem.getTodoText());
        }
    }

    // Model for t0d0 item
    private class TodoItem {
        private String todoText;
        private boolean isChecked;
        public TodoItem(String todoText) {
            this.todoText = todoText;
            this.isChecked = false;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public String getTodoText() {
            return todoText;
        }
    }
}
