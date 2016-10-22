package com.example.hoangyen.appgame;

import java.io.IOException;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class GiaoDienChoi extends Activity {
	private DataHelper dataHelper;//tạo đt của lớp DataHelper
	private Cursor cursor; // tạo curor
		ImageButton btnDe;
		ImageButton btnKho;
		TextView txtLoai;
		TextView cauhoiso;
		String str="";
	public int so = 1;//biến số câu hỏi
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.giaodienchoi); //gọi layout giao dien chơi
	        txtLoai = (TextView) findViewById(R.id.txtLoaiCauHoi);
	        cauhoiso = (TextView) findViewById(R.id.txtCauHoiSo);
	        btnDe = (ImageButton) findViewById(R.id.btnDe);
	        btnKho = (ImageButton) findViewById (R.id.btnKho);
	        
	        dataHelper = new DataHelper(GiaoDienChoi.this);//tạo database
			try {
	        	dataHelper.createDataBase();
		 	} catch (IOException ioe) {
		 		throw new Error("Unable to create database");
		 	}
		 	dataHelper.openDataBase();//mở database
		 	String sql1 = "select count(*) from bangcauhoi";//lấy số lượng câu hỏi
		 	Cursor mCount= dataHelper.SELECTSQL(sql1);//đem vào  cusror
		 	mCount.moveToFirst();//lấ dòng đầu tiên trả về false nếu rỗng
		 	int count= mCount.getInt(0);//lấy giá trị cột đầu tiên
		 	mCount.close();// đóng cursor
		 	Random rd=new Random();//tạo đối tượng random
		 	
		 	
	        int x=rd.nextInt((count-1+1)+1);//lấy 1 câu ngẫu nhiên
	        if(x==0)
	        	x = x+1;
			String sql = "select * from bangcauhoi where _id="+ x ;//lấy dòng của câu hỏi đó
			Log.e("sql",sql);
			try{
				cursor = dataHelper.SELECTSQL(sql);//tạo cursor
				if(cursor!=null){
		    		if  (cursor.moveToFirst()) {
			    			str = cursor.getString(cursor.getColumnIndex("chude"));//lấy cột chủ đề
			    			txtLoai.setText(str);//lấy loại câu hỏi hiển thì lên textbox
			    			cauhoiso.setText(String.valueOf(so));//số câu hỏi mà người choi trả lời
			    			Log.e("ket qua",str);
		    		}
		    	}
				else
					txtLoai.setText("No result");
			}
			catch(SQLException ex)
			{
				
				txtLoai.setText("No result");
			}
			dataHelper.CloseBD();
			ClickButtonListener();
	    }
	    private void ClickButtonListener() {
	    	//sự kiện khi nhân chon button De
	    	btnDe.setOnClickListener(new OnClickListener() {
	    	int x = 0;
			public void onClick(View v) {
				Intent i=new Intent(getApplication(),GiaoDienCauHoi.class);
				i.putExtra("diem", String.valueOf(0));
				i.putExtra("songuoi", String.valueOf(100));
				i.putExtra("cauhoiso", String.valueOf(1));
				i.putExtra("chude", str);
				i.putExtra("loaicauhoi",String.valueOf(x));
				startActivity(i);
			   }
		});
	    	//sự kiện button Kho
			btnKho.setOnClickListener(new OnClickListener() {
			int x = 1;
				public void onClick(View v) {
					//truyền các giá trị vào intent
					Intent i=new Intent(getApplication(),GiaoDienCauHoi.class);
					i.putExtra("diem", String.valueOf(0));//truyền điểm 0
					i.putExtra("songuoi", String.valueOf(100));//số người hiện tại là 100
					i.putExtra("cauhoiso", String.valueOf(1));//câu hỏi đã trả lời là 1
					i.putExtra("chude", str);
					i.putExtra("loaicauhoi",String.valueOf(x));
					startActivity(i);
				   }
			});	
			
	}
}