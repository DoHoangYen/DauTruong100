package com.example.hoangyen.appgame;

import java.io.IOException;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class CauHoiTiep extends Activity {
	private DataHelper dataHelper;
	private Cursor cursor; // tạo con trỏ để lưu kết quả truy vấn từ csdl
		// tạo các biến để tham chiếu tới các layout
		ImageButton btnDe; 
		ImageButton btnKho;
		TextView txtLoai;
		TextView cauhoiso;
		String str="";
		int socau;
		int diemhientai;
		int songuoi;
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.giaodienchoi); //gọi giao diện chơi
	        txtLoai = (TextView) findViewById(R.id.txtLoaiCauHoi);
	        cauhoiso = (TextView) findViewById(R.id.txtCauHoiSo);
	        btnDe = (ImageButton) findViewById(R.id.btnDe);
	        btnKho = (ImageButton) findViewById (R.id.btnKho);
	        socau = GiaoDienCauHoi.so + 1;//số câu hỏi đã trả lời
	        songuoi = GiaoDienCauHoi.nguoi;// số người hiện tại còn chơi
	        
	        txtLoai.setText(String.valueOf(socau));//lấy loại câu hỏi
	        diemhientai = GiaoDienCauHoi.diem;// điểm hiện tại của nguoi chơi
	        
	        dataHelper = new DataHelper(CauHoiTiep.this);// khởi tạo dt datahelper
			try {
	        	dataHelper.createDataBase();
		 	} 
			catch (IOException ioe) 
			{
		 		throw new Error("Unable to create database");
		 	}
		 	dataHelper.openDataBase();//mở csdl
		 	String sql1 = "select count(*) from bangcauhoi";// câu truy vấn lấy ra số câu hỏi trong bảng bangcauhoi
		 	Cursor mCount= dataHelper.SELECTSQL(sql1);// luu kết quả truy vấn vào cursor
		 	mCount.moveToFirst();//di chuyển cursor đến dòng đầu tiên
		 	int count= mCount.getInt(0);// lấy giá trị cột 0(cột đầu tiên) chính là count(*) số câu hỏi
		 	mCount.close();// đóng cursor
		 	Random rd=new Random();//tạo dt random
	        int x=rd.nextInt((count-1+1)+1);//lấy ngẫu nhiên câu hỏi từ 0 đên count(*)
	        if(x==0)
	        	x = x+1;
	        
			String sql = "select * from bangcauhoi where _id="+ x ;//lấy nội dung câu hỏi
			Log.e("sql",sql);
			try{
				cursor = dataHelper.SELECTSQL(sql);//đua nd câu hỏi và cursor
				if(cursor!=null){
		    		if  (cursor.moveToFirst())//chuyen cursor ve dong dau tien của csdl
		    		{
			    			str = cursor.getString(cursor.getColumnIndex("chude"));// lấy ra chủ đề của câu hỏi
			    			txtLoai.setText(str);//đạt vào txtloai để hiển thị lên màn hình
			    			cauhoiso.setText(String.valueOf(socau));//hiện thị số câu hỏi (đã +1)
			    			
		    		}
		    	}
				else
					txtLoai.setText("No result");//hết câu hỏi
			}
			catch(SQLException ex)
			{
				Log.e("vao day rioa","huhuhu");
				txtLoai.setText("No result");
			}
			dataHelper.CloseBD();// đóng csdl
			ClickButtonListener();//gọi hàm 
	    }
	    private void ClickButtonListener() {
	    	btnDe.setOnClickListener(new OnClickListener() {
	    	int x = 0;
			public void onClick(View v) {
				Intent i=new Intent(getApplication(),GiaoDienCauHoi.class);
				i.putExtra("diem", String.valueOf(diemhientai));//bỏ điểm vào cột điểm
				i.putExtra("songuoi", String.valueOf(songuoi));
				//Log.e("diem diem diem",String.valueOf(diemhientai));
				i.putExtra("cauhoiso", String.valueOf(socau));
				//Log.e("cau hoi cau hoi theo",String.valueOf(diemhientai));
				i.putExtra("chude", str);
				i.putExtra("loaicauhoi",String.valueOf(x));
				startActivity(i);
			   }
		});
			btnKho.setOnClickListener(new OnClickListener() {
			int x = 1;
				public void onClick(View v) {
					Intent i=new Intent(getApplication(),GiaoDienCauHoi.class);
					i.putExtra("diem", String.valueOf(diemhientai));
					i.putExtra("cauhoiso", String.valueOf(socau));
					i.putExtra("songuoi", String.valueOf(songuoi));
					i.putExtra("chude", str);
					i.putExtra("loaicauhoi",String.valueOf(x));
					startActivity(i);
				   }
			});	
			
	}
}