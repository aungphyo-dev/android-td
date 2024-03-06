package com.example.todolist;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.app.Activity;
import android.widget.CheckBox;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;

import com.example.todolist.TaskInfo;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<TaskInfo> {
    Context context;
    int layoutResourceId;
    static ArrayList<TaskInfo> data=new ArrayList<TaskInfo>();
    DBHelper db;
    public TaskAdapter(Context context, int layoutResourceId,
                       ArrayList<TaskInfo>data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup
            parent) {
/*position: This parameter indicates the position of the item within the data set that the adapter is currently working with.
convertView: This parameter is a recycled view that can be reused to display the data at the specified position.Android uses view
recycling to optimize memory usage and improve performance when dealing with large lists or grids. If convertView is null, a new view
needs to be inflated and initialized. If convertView is not null, it represents a view that has already been created and initialized
but is no longer being used for its original position.
parent: This parameter is the parent ViewGroup to which the returned view will be attached.
It's typically the AdapterView itself (e.g., ListView,GridView). You can use this parameter to obtain layout parameters
and other attributes of the parent ViewGroup
*/
        View row = convertView;
        TaskInfoHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = ((Activity)
                    context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent,
                    false);
            holder = new TaskInfoHolder();
            holder.checkBox= (CheckBox)
                    row.findViewById(R.id.chkTask);
            row.setTag(holder);
        } else {
            holder = (TaskInfoHolder) row.getTag();
        }
        TaskInfo taskInfo = data.get(position);
        holder.checkBox.setText(taskInfo.taskName);
        holder.checkBox.setChecked(taskInfo.taskStatus);
        setCheckBoxDesign(holder.checkBox);
        holder.checkBox.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v ;
                db=new DBHelper(getContext());
                db.updateTaskData(cb.getText().toString(),
                        cb.isChecked()?1:0);
                setCheckBoxDesign(cb);
            }
        });
        holder.checkBox.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showConfirmDialog(v);
                return true;
            }
        });
        return row;}
    private void deleteData(View v){
        CheckBox cb = (CheckBox) v ;
        Toast.makeText(context, cb.getText().toString(),
                Toast.LENGTH_SHORT).show();
        db=new DBHelper(getContext());
        db.deleteTaskData(cb.getText().toString());
        for(int i=0;i<TaskAdapter.data.size();i++){
            if(TaskAdapter.data.get(i).getTaskName().equals(cb.getText().toString())){
                TaskAdapter.data.remove(i);
                notifyDataSetChanged();
            }
        }
    }
    private void showConfirmDialog(View v){
        AlertDialog.Builder builder = new
                AlertDialog.Builder(getContext());
        builder.setTitle("Confirm Delete");
        builder.setMessage("Are you sure you want to delete this item?");
        builder.setPositiveButton("OK", new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        deleteData(v);
                    }
                });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void setCheckBoxDesign(CheckBox cb){
        if(cb.isChecked()){cb.setAlpha(0.3f);
            cb.setTypeface(null, Typeface.ITALIC);
            cb.setTextColor(Color.RED);
        }
        else{
            cb.setAlpha(1.0f);
            cb.setTypeface(null, Typeface.BOLD);
            cb.setTextColor(Color.BLUE);
        }
    }
    public class TaskInfoHolder {
        CheckBox checkBox;
    }
}