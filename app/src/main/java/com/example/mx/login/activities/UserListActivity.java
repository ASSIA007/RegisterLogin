package com.example.mx.login.activities;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mx.login.R;
import com.example.mx.login.adapters.UsersRecyclerAdapter;
import com.example.mx.login.model.User;
import com.example.mx.login.sql.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {


        private AppCompatActivity activity = UserListActivity.this;
        private AppCompatTextView textViewName;
        private RecyclerView recyclerViewUsers;
        private List<User> listUsers;
        private UsersRecyclerAdapter usersRecyclerAdapter;
        private DataBaseHelper databaseHelper;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_user_list);
            getSupportActionBar().setTitle("");
            initViews();
            initObjects();

        }

        private void initViews() {
            textViewName = (AppCompatTextView) findViewById(R.id.textViewName);
            recyclerViewUsers = (RecyclerView) findViewById(R.id.recyclerViewUsers);
        }

        private void initObjects() {
            listUsers = new ArrayList<>();
            usersRecyclerAdapter = new UsersRecyclerAdapter(listUsers);

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerViewUsers.setLayoutManager(mLayoutManager);
            recyclerViewUsers.setItemAnimator(new DefaultItemAnimator());
            recyclerViewUsers.setHasFixedSize(true);
            recyclerViewUsers.setAdapter(usersRecyclerAdapter);
            databaseHelper = new DataBaseHelper(activity);

            String emailFromIntent = getIntent().getStringExtra("EMAIL");
            textViewName.setText(emailFromIntent);

            getDataFromSQLite();
        }

        private void getDataFromSQLite() {
            // AsyncTask is used that SQLite operation not blocks the UI Thread.
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    listUsers.clear();
                    listUsers.addAll(databaseHelper.getAllUser());

                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    usersRecyclerAdapter.notifyDataSetChanged();
                }
            }.execute();
        }
}
