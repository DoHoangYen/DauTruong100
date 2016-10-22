package com.example.hoangyen.appgame;

import java.io.IOException;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Diemcao extends ListActivity {

	private static final String TAG = "Diemcao";
	//private final static String ID_EXTRA ="com.example.text._ID";
	private DataHelper dataHelper;

	private Cursor cursor; // tuong duong voi mang
	private SimpleCursorAdapter adapter; // tuong duong voi adapter
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.diemcao);
		dataHelper = new DataHelper(Diemcao.this);
        try {
        	dataHelper.createDataBase();
 
	 	} catch (IOException ioe) {
	 
	 		throw new Error("Unable to create database");
	 	}
	 	try {
	 		dataHelper.openDataBase();
	 
	 	}catch(SQLException sqle){
	 		throw sqle;
 	}
		String sql = "select * from diem order by diemso desc LIMIT 10" ;
		Log.d(TAG, sql);
		cursor = dataHelper.SELECTSQL(sql);
		while (cursor.moveToNext()) {
			String id = cursor.getString(cursor
					.getColumnIndex("name"));
				String subject = cursor.getString(cursor
						.getColumnIndex("diemso"));
		}
		dataHelper.CloseBD();
		 //khoi tao adater
		String[] from = { "name","diemso" };
		int[] to = { R.id.txtTen, R.id.txtDiem };
		adapter = new SimpleCursorAdapter(Diemcao.this,R.layout.infordiem, cursor, from, to);
		setListAdapter(adapter);
	}


}
