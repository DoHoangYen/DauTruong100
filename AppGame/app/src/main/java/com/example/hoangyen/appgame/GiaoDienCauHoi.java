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
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GiaoDienCauHoi extends Activity {
	private DataHelper dataHelper;//tạo dt dataHelper
	private Cursor cursor; // tạo cusor
	//tạo đối tượng để tham chiếu tới layout
		TextView txtCauhoi;
		TextView A;
		TextView B;
		TextView C;
		TextView txtDiem;
		TextView txtNguoi;
		TextView txtSo;
		TextView txtLoai;
		TextView txtThongBao;
		RelativeLayout rlnThongBao;
		TextView txt25;
		TextView txt75;
		TextView txt50;
		TextView txtX2;
		String str="";
		int check = 0;//biến kiểm tra xem khi nào thì lưu điểm kết thúc trò chơi
		int x;// số người chơi trả lời sai
		String dapan="";//đap an
		public static int diem;//điêm của người chơi
		int sodiem = 0;//điêm cua 1 nguoi choi trong so 100nguoi
		public static int nguoi = 100;
		int cohoi = 0;//cac quyen tro giup
		public static int so;//so cau hoi đã trả lời của người chơi
		final Context bermzkie = this;
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.giaodiencauhoi);//gọi giao diện câu hỏi 
	        //tham chieu toi textview cau hoi ơ layout
	        txtCauhoi = (TextView) findViewById(R.id.txtCauHoi);
	        //gán nội dung
	        A = (TextView) findViewById(R.id.txtA);
	        B = (TextView) findViewById(R.id.txtB);
	        C = (TextView) findViewById(R.id.txtC);
	        txtDiem = (TextView) findViewById(R.id.txtDiem);
	        txtNguoi = (TextView) findViewById(R.id.txtNguoi);
	        txtSo = (TextView) findViewById(R.id.txtSo);
	        txtLoai = (TextView) findViewById(R.id.txtLoai);
	        txtThongBao = (TextView) findViewById(R.id.txtThongBao);
	        txt25 = (TextView) findViewById(R.id.txt25);
	        txt50 = (TextView) findViewById(R.id.txt50);
	        txt75 = (TextView) findViewById(R.id.txt75);
	        txtX2 = (TextView) findViewById(R.id.txtX2);
			rlnThongBao = (RelativeLayout) findViewById(R.id.rltTb);
//	        txtThongBao.setVisibility(View.GONE);
			rlnThongBao.setVisibility(View.GONE);
	        
	        Intent i = getIntent();//lấy intent bên giao diện chơi
			String chude = i.getStringExtra("chude");//truyen vao chu de
			String loai = i.getStringExtra("loaicauhoi");//truyen vào laoij cau hỏi dễ hay khó
			String socauhoi = i.getStringExtra("cauhoiso");// truyền vào số câu hỏi mà ngchoi đã trả lời
			String diemhientai = i.getStringExtra("diem");//điểm hiện tại
			String songuoichoi = i.getStringExtra("songuoi");//số người chơi còn lại
			//ép kiểu về int
			nguoi = Integer.parseInt(songuoichoi);
	    	diem = Integer.parseInt(diemhientai);
	    	so = Integer.parseInt(socauhoi);
	    	//gán và các textbox
	    	txtDiem.setText(String.valueOf(String.valueOf(diem)));
        	txtNguoi.setText(String.valueOf(songuoichoi));
        	txtSo.setText(String.valueOf(so));
        	
	    	
	    	//khi điểm =0 thì không dc sử dụng các quyền trợ giúp
	    	if(diem == 0)
	    	{
	    		txt25.setEnabled(false);
	    		txt50.setEnabled(false);
	    		txt75.setEnabled(false);
	    	}
	    	else
	    	{
	    		txt25.setEnabled(true);
	    		txt50.setEnabled(true);
	    		txt75.setEnabled(true);
	    	}
	    	//chọn loại cau hỏi
			if(loai.equals("0"))
				txtLoai.setText("Dễ");
			else
				txtLoai.setText("Khó");
			//lấy dữ liệu câu hỏi từ database
	        dataHelper = new DataHelper(GiaoDienCauHoi.this);//tạ đt datahelper trên giao diện câu hỏi
			try {
	        	dataHelper.createDataBase();
		 	} catch (IOException ioe) {
		 		throw new Error("Unable to create database");
		 	}
		 	dataHelper.openDataBase();
	        //Log.e("random",String.valueOf(x));
			String sql = "select * from bangcauhoi where chude='"+chude+"' and loaicauhoi="+loai ;//lấy ra nd câu hỏi
			Log.e("cau lenh",sql);
			try{
				cursor = dataHelper.SELECTSQL(sql);//đưa dữ liệu vào cursor
				if(cursor!=null){
		    		if  (cursor.moveToFirst()) {//di chuyen cusror về dòng đầu tiên trả về false nêu ko có
			    			str = cursor.getString(cursor.getColumnIndex("cauhoi"));//lấy cột câu hỏi đây là cột chứa nd
			    			txtCauhoi.setText(str);
			    			//settext đáp án gán đáp án
			    			str = cursor.getString(cursor.getColumnIndex("dapanA"));
			    			A.setText(str);
			    			str = cursor.getString(cursor.getColumnIndex("dapanB"));
			    			B.setText(str);
			    			str = cursor.getString(cursor.getColumnIndex("dapanC"));
			    			C.setText(str);
			    			dapan = cursor.getString(cursor.getColumnIndex("dapanDung"));
			    			//Log.e("Dap an",dapan);
		    		}
		    	}
				else
					txtCauhoi.setText("No result");
			}
			catch(SQLException ex)
			{
				txtCauhoi.setText("No result");
			}
			dataHelper.CloseBD();//đóng database
			//ClickTextListener();
	    }
	    //hiển thị màu của đáp án đúng
	    //hàm kiểm tra câu trả lời đúng..câu nào dung là background màu đo
	    public void cautraloidung()
	    {
	    	if(dapan.equals("1"))
			{
	    		A.setBackgroundColor(Color.RED);
			}
	    	else if(dapan.equals("2"))
	    	{
	    		B.setBackgroundColor(Color.RED);
	    	}
	    	else
	    	{
	    		C.setBackgroundColor(Color.RED);
	    	}
	    }
	    //hàm chọn đáp án A
	    public void chooseA(View v)
	    {
	    	A = (TextView) findViewById(R.id.txtA);
	    	A.setBackgroundColor(Color.CYAN);//chuyên sang màu CYAN xanh da trời
	    	txtThongBao.setText("Hãy chờ xem câu trả lời bạn chọn có đúng không?");
//	    	txtThongBao.setVisibility(View.VISIBLE);
			rlnThongBao.setVisibility(View.VISIBLE);
	    	Thread timer1 = new Thread(){
	        	public void run(){     		
		        	try{
		        		sleep(3000);//chờ 3s rồi thông báo sẽ bị tắt
		        		//xử lí thread
		        		runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            	//kiểm tra đáp án với phương án A
                            	//khong dùng quyên giải thoát
                            	if(dapan.equals("1") && cohoi != 2 &&  cohoi != 3 && cohoi != 4)
                    	    	{
                    	        	txtThongBao.setText("Chúc mừng! Câu trả lời chính xác! Cùng chờ xem có bao nhiêu người trong số "+String.valueOf(nguoi)+" người trả lời sai nhé!");
                    	        	cho100guoi();//gọi hàm xem có bao nhiu người trả lời sai
                    	    	}
                            	// khi dung 1 trong 3 quyền
                    	    	else if (cohoi == 2 ||  cohoi == 3 || cohoi == 4)
                    	    	{
//									txtThongBao.setVisibility(View.GONE);
									rlnThongBao.setVisibility(View.GONE);
                    	    		cho100guoi();//gọi hàm cho100nguoi
                    	    	}
                            	//khi tra lời sai
                    	        else
                    	        {
                    	        	txtThongBao.setText("Rất tiếc! Câu trả lời không chính xác! Chúng ta phải dùng cuộc chơi ở đây.");
//                    	        	txtThongBao.setVisibility(View.VISIBLE);
									rlnThongBao.setVisibility(View.VISIBLE);
                    	        	KetThuctroChoi();//gọi hàm kết thúc trò chơi
                    	        }
                    	        
                    	        cautraloidung();//hiển thị câu trả lời đúng
                            }
                        });
		        	}catch(InterruptedException e){
		        		e.printStackTrace();
		        	}finally{
		        		
		        	}
	        	}
	       };
	       timer1.start();//chạy thread
	    }
	    public void chooseB(View v)
	    {
	    	B = (TextView) findViewById(R.id.txtB);
	    	B.setBackgroundColor(Color.CYAN);//màu của phương án mà người chơi chọn
	    	txtThongBao.setText("Hãy chờ xem câu trả lời bạn chọn có đúng không?");
//	    	txtThongBao.setVisibility(View.VISIBLE);
			rlnThongBao.setVisibility(View.VISIBLE);//cho phép hiển thị thông bao
	    	Thread timer1 = new Thread(){
	        	public void run(){     		
		        	try{
		        		sleep(3000);//chờ 3s để thông báo tắt
		        		//xử lí thread
		        		runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            	//đáp án B là đáp đúng (2) và ko dug quyên giai thoát
                            	if(dapan.equals("2")&& cohoi != 2 &&  cohoi != 3 && cohoi != 4)
                    	    	{
                    	        	txtThongBao.setText("Chúc mừng! Câu trả lời chính xác! Cùng chờ xem có bao nhiêu người trong số "+String.valueOf(nguoi)+" người trả lời sai nhé!");
                    	        	cho100guoi();
                    	    	}
                            	//ko trả lời đc mà dùng quyền giải thoát
                    	    	else if (cohoi == 2 ||  cohoi == 3 || cohoi == 4)
                    	    	{
//                    	    		txtThongBao.setVisibility(View.GONE);
									rlnThongBao.setVisibility(View.GONE);//ẩn thông báo
                    	    		cho100guoi();
                    	    	}
                            	//khi người chơi trả lời sai
                    	        else
                    	        {
                    	        	txtThongBao.setText("Rất tiếc! Câu trả lời không chính xác! Chúng ta phải dùng cuộc chơi ở đây.");
//                    	        	txtThongBao.setVisibility(View.VISIBLE);
									rlnThongBao.setVisibility(View.VISIBLE);//hiện thong báo
                    	        	KetThuctroChoi();//gọi hàm kết thúc trò chơi
                    	        }
                    	        //txtThongBao.setVisibility(View.VISIBLE);
                    	        cautraloidung();//hện thị câu trả lời đúng
                            }
                        });
		        	}catch(InterruptedException e){
		        		e.printStackTrace();
		        	}finally{
		        		
		        	}
	        	}
	       };
	       timer1.start();//bắt đầu thread
	    	
	    }
	    public void chooseC(View v)
	    {
	    	C = (TextView) findViewById(R.id.txtC);
	    	C.setBackgroundColor(Color.CYAN);txtThongBao.setText("Hãy chờ xem câu trả lời bạn chọn có đúng không?");
//	    	txtThongBao.setVisibility(View.VISIBLE);
			rlnThongBao.setVisibility(View.VISIBLE);
	    	Thread timer1 = new Thread(){
	        	public void run(){     		
		        	try{
		        		sleep(3000);
		        		runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            	//so sanh C với đáp án đúng
                            	if(dapan.equals("3")&& cohoi != 2 &&  cohoi != 3 && cohoi != 4)
                    	    	{
                    	        	txtThongBao.setText("Chúc mừng! Câu trả lời chính xác! Cùng chờ xem có bao nhiêu người trong số "+String.valueOf(nguoi)+" người trả lời sai nhé!");
                    	        	cho100guoi();//chờ xem 100 người có bao nhiu người tl sai
                    	    	}
                            	//ko tra lời mà dùng quyền trợ giúp
                    	    	else if (cohoi == 2 ||  cohoi == 3 || cohoi == 4)
                    	    	{
//                    	    		txtThongBao.setVisibility(View.GONE);//ẩn thông báo
									rlnThongBao.setVisibility(View.GONE);
                    	    		cho100guoi();
                    	    	}
                            	//trả lời sai
                    	        else
                    	        {
                    	        	txtThongBao.setText("Rất tiếc! Câu trả lời không chính xác! Chúng ta phải dùng cuộc chơi ở đây.");
//                    	        	txtThongBao.setVisibility(View.VISIBLE);
									rlnThongBao.setVisibility(View.VISIBLE);
                    	        	KetThuctroChoi();//gọi hàm kết thúc trò chơi
                    	        }
                    	        
                    	        cautraloidung();//hiển thị câu trả lời đúng
                            }
                        });
		        	}catch(InterruptedException e){
		        		e.printStackTrace();
		        	}finally{
		        		
		        	}
	        	}
	       };
	       timer1.start();
	    	
	    }
	    
	    //chờ 100 người trả lời
	    public void cho100guoi()
	    {
	    	Random rd=new Random();//tạo dt random
	        x=rd.nextInt((nguoi)+1);//chọn ngẫu nhiên số người sẽ trả lời sai
	    	Thread timer = new Thread(){
	        	public void run(){     		
		        	try{
		        		sleep(4000);//chờ 4s
		        		//xử lí thread
		        		runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            	if(nguoi  > 0)
        		    	        {
                            		sodiem = 1000/nguoi;//lấy số điểm tương ứng  với mỗi người chơi 
        		    	        }
                            	//nhân đôi số điểm
        		    	        if(cohoi == 1)
        		    	        {
        		    	        	txtThongBao.setText("Đã có "+String.valueOf(x)+" người chơi trong số "+String.valueOf(nguoi)+" người chơi trả lời sai!" +
		    	        					"Bạn được cộng thêm "+String.valueOf(sodiem*x*2));
        		    	        	txtThongBao.setTextColor(Color.BLUE);
		    		    	        diem = diem + sodiem*x*2;//điểm của người chơi
		    		    	        nguoi = nguoi - x;//số người còn lại
		    		    	        if(nguoi < 0)
		    		    	        	nguoi = 0;
		    		    	        Thread timer1 = new Thread(){
		    		    	        	//xử lí thread
		    		    	        	public void run(){     		
		    		    		        	try{
		    		    		        		sleep(3000);//chờ 3s
		    		    		        		runOnUiThread(new Runnable() {
		    		                                @Override
		    		                                public void run() {
		    		                                	txtThongBao.setText("Bạn đã chọn nhân đối số điểm nên số điểm hiện tại của bạn là "+String.valueOf(diem));
		    		                                	//chuyển các số nguyên về xau kitu
		    		                                	txtDiem.setText(String.valueOf(diem));
		    		                                	txtNguoi.setText(String.valueOf(nguoi));
		    		                                	rlnThongBao.setVisibility(View.VISIBLE);
		    		                                	if(nguoi <= 0)
		    			        		    	        {
		    		                                		check =1;
		    		                                		GoiLuudiem();//gọi hàm này khi đã chiên thắng
		    			        		    	        }
		    		                                }
		    		                            });
		    		    		        	}catch(InterruptedException e){
		    		    		        		e.printStackTrace();
		    		    		        	}finally{
		    		    		        		
		    		    		        	}
		    		    	        	}
		    		    	       };
		    		    	       timer1.start();//bắt đầu thread
        		    	        }
        		    	        //chọn quyền giải thoats
        		    	        else if(cohoi==2 || cohoi == 3 || cohoi == 4)
        		    	        {
        		    	        	nguoi = nguoi - x;//số người còn lại
        		    	        	if(nguoi < 0)
        		    	        		nguoi = 0;
        		    	        	Thread timer = new Thread(){
		    		    	        	public void run(){     		
		    		    		        	try{
		    		    		        		sleep(2000);//chờ 2s ban da su dung quyen tro g
		    		    		        		//xử lí thread
		    		    		        		runOnUiThread(new Runnable() {
		    		                                @Override
		    		                                
		    		                                public void run() {
		    		                                	txtNguoi.setText(String.valueOf(nguoi));
		    		        		    	        	txtThongBao.setText("Đây là câu trả lời đúng");
		    		        		    	        	cautraloidung();
		    		                                	rlnThongBao.setVisibility(View.VISIBLE);
		    		                                	Thread timer4 = new Thread(){
		    				    		    	        	public void run(){     		
		    				    		    		        	try{
		    				    		    		        		sleep(3000);
		    				    		    		        		runOnUiThread(new Runnable() {
		    				    		                                @Override
		    				    		                                public void run() {
		    				    		                                	if(nguoi <= 0)
		    				    			        		    	        {
		    				    		                                		check =1;
		    				    		                                		GoiLuudiem();//gọi hàm này khi chiến thắng
		    				    			        		    	        }
		    				    		                                }
		    				    		                            });
		    				    		    		        	}catch(InterruptedException e){
		    				    		    		        		e.printStackTrace();
		    				    		    		        	}finally{
		    				    		    		        		
		    				    		    		        	}
		    				    		    	        	}
		    				    		    	       };
		    				    		    	       timer4.start();
		    		                                }
		    		                            });
		    		    		        	}catch(InterruptedException e){
		    		    		        		e.printStackTrace();
		    		    		        	}finally{
		    		    		        		
		    		    		        	}
		    		    	        	}
		    		    	       };
		    		    	       timer.start();
        		    	        	//txtThongBao.setVisibility(View.VISIBLE);	
        		    	        }
        		    	        //ko chọn gi hết
        		    	        else
        		    	        {
        		    	        	txtThongBao.setText("Đã có "+String.valueOf(x)+" người chơi trong số "+String.valueOf(nguoi)+" người chơi trả lời sai!" +
		    	        					"Bạn được cộng thêm "+String.valueOf(sodiem*x));
	        		    	        	txtThongBao.setTextColor(Color.BLUE);
		        		    	        diem = diem + sodiem*x;//điêm người chơi dc cộng dựa trên số người trả lời sai
		        		    	        nguoi = nguoi - x;
		        		    	        txtNguoi.setText(String.valueOf(nguoi));
		        		    	       
		        		    	        txtDiem.setText(String.valueOf(diem));
		        		    	        rlnThongBao.setVisibility(View.VISIBLE);
		        		    	        Thread timer4 = new Thread(){
			    		    	        	public void run(){     		
			    		    		        	try{
			    		    		        		sleep(3000);//chờ 3s
			    		    		        		//xử lí thread
			    		    		        		runOnUiThread(new Runnable() {
			    		                                @Override
			    		                                public void run() {
			    		                                	if(nguoi == 0)
			    			        		    	        {
			    		                                		check =1;
			    		                                		//Log.e("check",String.valueOf(check));
			    		                                		GoiLuudiem();//gọi hàm này khi chiến thắng
			    			        		    	        }
			    		                                }
			    		                            });
			    		    		        	}catch(InterruptedException e){
			    		    		        		e.printStackTrace();
			    		    		        	}finally{
			    		    		        		
			    		    		        	}
			    		    	        	}
			    		    	       };
			    		    	       timer4.start();
		        		    	        
        		    	        }
        		    	        //ẩn các thông baó đã thông báo
        		    	        Thread timer2 = new Thread(){
        		    	        	public void run(){     		
        		    		        	try{
        		    		        		sleep(3000);
        		    		        		runOnUiThread(new Runnable() {
        		                               @Override
        		                               public void run() {
        		                            	rlnThongBao.setVisibility(View.GONE);  //ẩn thông baos
        		                               }
        		                           });
        		    		        	}catch(InterruptedException e){
        		    		        		e.printStackTrace();
        		    		        	}finally{
        		    		        		
        		    		        	}
        		    	        	}
        		    	       };
        		    	       timer2.start();
                            }
                        });
		        	}catch(InterruptedException e){
		        		e.printStackTrace();
		        	}finally{
		        	}
	        	}
	       };
	       timer.start();
	       //chơi tiếp
	       //chơi tiếp
	       if(check != 1)
	       {
		       Thread timer3 = new Thread(){
		        	public void run(){     		
			        	try{
			        		sleep(9000);//chờ 9s
			        		runOnUiThread(new Runnable() {
	                          @Override
	                          public void run() {
	                        	  //hộp thoại AlertDialog tạo đối tượng của lớp AlertDialog.Builder
	                        	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(bermzkie);
	      				        alertDialogBuilder.setTitle("Hello!");//tiêu đề
	      				        alertDialogBuilder
	      				                .setMessage("Bạn đã sẵn sàng qua câu hỏi tiếp theo?")
	      				                .setCancelable(false)
	      				                .setPositiveButton("Yes",
	      				                		//sự kiện khi nhấn chọn Yes
	      				                        new DialogInterface.OnClickListener() {
	      				                            public void onClick(DialogInterface dialog, int id) {
	      				                            	Intent e = new Intent("com.example.appgame.CauHoiTiep");//qua giao diện câu hoi tiếp theo
	      				                				startActivity(e);
	      				                            }
	      				                        })
	      				                        //sự kiện khi nhấn chọn No
	      				                .setNegativeButton("No", new DialogInterface.OnClickListener() {
	      				                    public void onClick(DialogInterface dialog, int id) {
	      				                    	Intent e = new Intent("com.example.appgame.GiaoDienChinh");//về giao diện chính
	      				        				startActivity(e);
	      				                    }
	      				                });
	
	      				        AlertDialog alertDialog = alertDialogBuilder.create();
	      				        alertDialog.show();                      	
	                          }
	                      });
			        	}catch(InterruptedException e){
			        		e.printStackTrace();
			        	}finally{
			        		
			        	}
		        	}
		       };
		       timer3.start();
	       }
	    }
	    public void chooseX2(View v)
	    {
	    	cohoi = 1;//x2 dặt biến co hoi =1
	        txtThongBao.setText("Bạn đã chọn nhân đôi số điểm ở câu này!");
	        rlnThongBao.setVisibility(View.VISIBLE);
	        Thread timer = new Thread(){
	        	public void run(){     		
		        	try{
		        		sleep(2000);//đợi 2s
		        		runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            	rlnThongBao.setVisibility(View.GONE);//sau 2s thì ẩn thông báo
                            }
                        });
		        	}catch(InterruptedException e){
		        		e.printStackTrace();
		        	}finally{
		        		
		        	}
	        	}
	       };
	       timer.start();
	    }
	    public void choose25(View v)
	    {
	    	cohoi = 2;//giá trị bằng 2 khi chon -25% số điểm
	    	diem = (int) (diem - diem*0.25);//bị trừ điểm ngay khi nhấn
	        txtThongBao.setText("Bạn đã chọn quyền giải thoát 25% số điểm! " +
	        		".Số điểm bây giờ sẽ là " + String.valueOf(diem)+
	        		". Nếu không chọn quyền giải thoát bạn sẽ chọn phương án nào!");
	        rlnThongBao.setVisibility(View.VISIBLE);
	        txtDiem.setText(String.valueOf(diem));
	        Thread timer = new Thread(){
	        	public void run(){     		
		        	try{
		        		sleep(2000);//chờ 2s để hiện thông báo
		        		runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            	rlnThongBao.setVisibility(View.GONE);//ẩn thông báo
                            }
                        });
		        	}catch(InterruptedException e){
		        		e.printStackTrace();
		        	}finally{
		        		
		        	}
	        	}
	       };
	       timer.start();
	    }
	    public void choose50(View v)
	    {
	    	cohoi = 3;
	    	diem = (int) (diem - diem*0.50);//trừ 50% số điểm
	        txtThongBao.setText("Bạn đã chọn quyền giải thoát 50% số điểm! " +
	        		". Số điểm bây giờ sẽ là " + String.valueOf(diem)+
	        		". Nếu không chọn quyền giải thoát bạn sẽ chọn phương án nào!");
	        rlnThongBao.setVisibility(View.VISIBLE);//hiện thông báo
	        txtDiem.setText(String.valueOf(diem));
	        Thread timer = new Thread(){
	        	public void run(){     		
		        	try{
		        		sleep(2000);
		        		runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            	rlnThongBao.setVisibility(View.GONE);//bỏ qua thông báo
                            }
                        });
		        	}catch(InterruptedException e){
		        		e.printStackTrace();
		        	}finally{
		        		
		        	}
	        	}
	       };
	       timer.start();
	    }
	    public void choose75(View v)
	    {
	    	diem = (int) (diem - diem*0.75);//trừ 75% số điểm
	        txtThongBao.setText("Bạn đã chọn quyền giải thoát 75% số điểm! " +
	        		". Số điểm bây giờ sẽ là " + String.valueOf(diem)+
	        		". Nếu không chọn quyền giải thoát bạn sẽ chọn phương án nào!");
	    	cohoi = 4;
	        txtThongBao.setText("Bạn đã chọn quyền giải thoát 75% số điểm!");
	        rlnThongBao.setVisibility(View.VISIBLE);
	        txtDiem.setText(String.valueOf(diem));
	        Thread timer = new Thread(){
	        	public void run(){     		
		        	try{
		        		sleep(2000);
		        		runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            	rlnThongBao.setVisibility(View.GONE);
                            }
                        });
		        	}catch(InterruptedException e){
		        		e.printStackTrace();
		        	}finally{	
		        	}
	        	}
	       };
	       timer.start();
	    }
	    //lưu điểm khi người chơi chiến thắng ko thắng thì ko lưu
	    public void luuDiem()
	    {
	    	//tạo đối tượng hộp thoại
	    	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(bermzkie);
		        alertDialogBuilder.setTitle("Điểm cao!");
		        alertDialogBuilder
		                .setMessage("Bạn có muốn lưu điểm lại không?")
		                .setCancelable(false)
		                .setPositiveButton("Yes",
		                		//sự kiện khi chọn yes
		                        new DialogInterface.OnClickListener() {
		                            public void onClick(DialogInterface dialog, int id) {
		                            	Intent e = new Intent("com.example.appgame.LuuDiem");//vào giao diện lưu điểm
		                            	 e.putExtra("diem", String.valueOf(diem));//truyền điểm qua giao điện điểm
		                				startActivity(e);
		                            }
		                        })
		                        //sự kiện khi chon No
		                .setNegativeButton("No", new DialogInterface.OnClickListener() {
		                    public void onClick(DialogInterface dialog, int id) {
		                    	Intent e = new Intent("com.example.appgame.GiaoDienChinh");//về giao diên chính
		        				startActivity(e);
		                    }
		                });

		        AlertDialog alertDialog = alertDialogBuilder.create();
		        alertDialog.show();  
	    }
	    //gọi hàm này khi 100 người đã bị loại khỏi cuộc chơi
	    public void GoiLuudiem()
	    {
	    	   txtThongBao.setText("Chúc mừng bạn đã giành chiến thắng. Số điểm bạn đạt được là "+String.valueOf(diem));
	    	   txtThongBao.setTextColor(Color.RED);
	    	   rlnThongBao.setVisibility(View.VISIBLE);
	    	Thread timer2 = new Thread(){
	        	public void run(){     		
		        	try{
		        		sleep(3000);
		        		runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                        	   luuDiem();  //gọi hàm lưu điểm                         	
                           }
                       });
		        	}catch(InterruptedException e){
		        		e.printStackTrace();
		        	}finally{
		        		
		        	}
	        	}
	       };
	       timer2.start();
	    }
	    //khi thua thì gọi hàm này
	    public void KetThuctroChoi()
	    {
	    	Thread timer2 = new Thread(){
	        	public void run(){     		
		        	try{
		        		sleep(3000);//chờ 3s
		        		runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                        	   Intent e = new Intent("com.example.appgame.GiaoDienChinh");//về giao diện chính
		        				startActivity(e);                     	
                           }
                       });
		        	}catch(InterruptedException e){
		        		e.printStackTrace();
		        	}finally{
		        		
		        	}
	        	}
	       };
	       timer2.start();
	    }
}