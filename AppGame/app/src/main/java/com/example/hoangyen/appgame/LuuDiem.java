package com.example.hoangyen.appgame;

import java.io.IOException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class LuuDiem extends Activity {
	private DataHelper dataHelper;//tạo đối tượng DataHelper
	//tạo các đôi tượng để tham chiếu tới các layout
	TextView txtDiem;
	EditText txtTen;
	String diem;
	String ten;
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.luudiem); //gọi layout lưu điểm
	        Intent i = getIntent();
			
			txtDiem = (TextView) findViewById(R.id.txtDiem);
			txtTen = (EditText) findViewById(R.id.txtName);
			
			diem = i.getStringExtra("diem");//nhận điểm từ giao diện câu hỏi
			
			//Log.e("tennnnn",ten);
			
			txtDiem.setText(diem);//hiển thị điểm
			ClickButtonListener();
	    }
	    private void ClickButtonListener() {
	    	ImageButton btnAdd = (ImageButton)findViewById(R.id.btnLuu);
	    	//sự kiện khi nhân button luu
	    	btnAdd.setOnClickListener(new OnClickListener() {
	    	int x = 0;
			public void onClick(View v) {
				dataHelper = new DataHelper(LuuDiem.this);
				   //String word = txtWord.getText().toString();
					try {
			        	dataHelper.createDataBase();
				 	} catch (IOException ioe) {
				 		throw new Error("Unable to create database");
				 	}
				 	dataHelper.openDataBase();
				 	//kiểm tra nhập tên
				 	if(txtTen.getText().toString().trim() == null )
						{
							Toast.makeText(getApplicationContext(), "Name is not null!",
							          Toast.LENGTH_SHORT).show();
						}
						else
						{
							//lưu tên và điểm vào csdl
							ten = txtTen.getText().toString();
							String sql1 = "insert into diem(name,diemso) values ('"+ten+"',"+diem+")";
							
							dataHelper.myDataBase.execSQL(sql1);//thực thi câu sql
							dataHelper.CloseBD();
							Toast.makeText(getApplicationContext(), "Add succeed!",
							          Toast.LENGTH_SHORT).show();
						}  }
		});
	    	ImageButton btnHuy = (ImageButton)findViewById(R.id.btnHuy);
	    	//khi không muốn lưu
	    	btnHuy.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent i=new Intent(getApplication(),GiaoDienChinh.class);//về giao diện chính
					startActivity(i);
				   }
			});	
			
	}
}