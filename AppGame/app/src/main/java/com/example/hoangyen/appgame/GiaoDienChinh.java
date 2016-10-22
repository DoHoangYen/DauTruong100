package com.example.hoangyen.appgame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class GiaoDienChinh extends Activity 
{
	//tạo các đôi tượng nút đê tham chiếu qua các layout tương ứng
		ImageButton btnStart;
		ImageButton btnHuongDan;
		ImageButton btnDiemCao;
		ImageButton btnExit;
		final Context bermzkie = this;
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);  //gọi layout activity_main
	        
	       ClickButtonListener();// gọi hàm
	    }
	    private void ClickButtonListener() {
			// TODO Auto-generated method stub
			btnStart = (ImageButton) findViewById(R.id.btnSart);//tham chieu tới nut start ở giao diện chính
			btnStart.setOnClickListener(new OnClickListener() //lắng nghe (sự kiện khi nhấn nút start)
			{
		
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				Intent e = new Intent("com.example.appgame.GiaoDienChoi");//gọi tới giao diện chơi 
				startActivity(e);
			}

		});
			btnHuongDan = (ImageButton) findViewById (R.id.btnHuongDan);
			btnHuongDan.setOnClickListener(new OnClickListener() // sự kiện khi bấm nut HuongDan
			{

				public void onClick(View v) {
					Intent e = new Intent("com.example.appgame.huongdan");//tới giao diện hương dẫn
					startActivity(e);
				}
			});	
			//------------------ Grammar us Button -------------------------
			
			btnDiemCao = (ImageButton) findViewById (R.id.btnDiemCao);
			btnDiemCao.setOnClickListener(new OnClickListener()// sự kiện khi bấm nút điểm cao 
			{

						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent e = new Intent("com.example.appgame.Diemcao");//vào giao diện điểm cao
							startActivity(e);//bắt đầu chạy giao diện
						}
					});
			
			//------------------ Exit Button -------------------------
			
			btnExit = (ImageButton) findViewById (R.id.btnThoat);
			btnExit.setOnClickListener(new OnClickListener() //sự kiện khi nhấn nút thoat
			{

						public void onClick(View v) 
						{
							MainActivity.MP.stop();//dừng phát nhạc
						        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(bermzkie);//
						        alertDialogBuilder.setTitle("Hello guy!");
						        alertDialogBuilder
						                .setMessage("Are you sure you want to exit?")//hiện thị thông báo
						                .setCancelable(false)
						                .setPositiveButton("Yes",
						                        new DialogInterface.OnClickListener() //sự khiện khi chọn yes
						                {
						                            public void onClick(DialogInterface dialog, int id) 
						                            {
						                                moveTaskToBack(true);
						                                android.os.Process.killProcess(android.os.Process.myPid());//kill chuong trình
						                                System.exit(1);//thoát chuong trinhf
						                            }
						                        })

						                .setNegativeButton("No", new DialogInterface.OnClickListener() //sự kiện khi chon No
						                {
						                    public void onClick(DialogInterface dialog, int id) {

						                        dialog.cancel();// hủy dialog ở lại giao diện chính
						                    }
						                });

						        AlertDialog alertDialog = alertDialogBuilder.create();//thực thi
						        alertDialog.show();//show thông báo ra màn hình
						    
						}
					});
	}
}