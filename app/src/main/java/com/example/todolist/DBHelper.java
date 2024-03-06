package com.example.todolist;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "Tododata.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tasks(task TEXT PRIMARY KEY, status INTEGER DEFAULT 0)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int
            newVersion) {
        db.execSQL("DROP TABLE if exists tasks");
    }
    public boolean insertTaskData(String task){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("task", task);
        long result=db.insert("tasks", null, contentValues);
        if(result==-1)return false;
        else return true;
    }
    public boolean updateTaskData(String task, int status){
        System.out.print(task+","+status);
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("task", task);
        contentValues.put("status", status);
        Cursor cursor=db.rawQuery("SELECT * FROM tasks WHERE task=?", new String[]{task});
        if(cursor.getCount()>0) {
            long result = db.update("tasks", contentValues,
                    "task=?", new String[]{task});
            if (result == -1) return false;
            else return true;
        }
        else{}
        return false;
    }
    public boolean deleteTaskData(String task){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM tasks WHERE task=?", new String[]{task});
        if(cursor.getCount()>0) {
            long result = db.delete("tasks", "task=?", new
                    String[]{task});
            if (result == -1) return false;
            else return true;
        }
        else{
            return false;
        }
    }
    public Cursor selectData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM tasks", null);
        return cursor;
    }
}