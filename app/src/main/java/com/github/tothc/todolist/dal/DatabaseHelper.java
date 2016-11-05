package com.github.tothc.todolist.dal;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.github.tothc.todolist.model.TodoListItem;
import com.github.tothc.todolist.model.TodoPosition;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "TodoList.DB";
    private static final int DATABASE_VERSION = 1;

    private Dao<TodoPosition, Integer> todoPositionDao = null;
    private Dao<TodoListItem, Integer> todoListItemDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database,ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, TodoPosition.class);
            TableUtils.createTable(connectionSource, TodoListItem.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            List<String> allSql = new ArrayList<String>();

            for (String sql : allSql) {
                db.execSQL(sql);
            }
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "exception during onUpgrade", e);
            throw new RuntimeException(e);
        }

    }

    public Dao<TodoPosition, Integer> getTodoPositionDao() {
        if (todoPositionDao == null) {
            try {
                todoPositionDao = getDao(TodoPosition.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return todoPositionDao;
    }

    public Dao<TodoListItem, Integer> getTodoListItemDao() {
        if (todoListItemDao == null) {
            try {
                todoListItemDao = getDao(TodoListItem.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return todoListItemDao;
    }

}