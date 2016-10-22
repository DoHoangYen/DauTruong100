package com.example.hoangyen.appgame;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;


public class MainActivity extends Activity {
	public static MediaPlayer MP;//tạo đối tượng chơi nhạc màn hinh nào cũng chơi
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);// gọi giao diên start
        MP = MediaPlayer.create(MainActivity.this, R.raw.dautruongbg);// chơi bài nhạc
        MP.setLooping(true);//lặp lại bài hát chơi xong chơi tiếp
		MP.start();// bắt đầu hát
		
        Thread timer = new Thread()// tạo đối tượng thread
        {
        	public void run(){     		
	        	try{
	        		sleep(4000);//hiên trong vong 4s
	        	}catch(InterruptedException e){
	        		e.printStackTrace();//hiển thị lỗi
	        	}finally{
	        		Intent i = new Intent ("com.example.appgame.GiaoDienChinh");//sau 4s thì dù gì cũng hiện qua giao diện chinh
	        		//finish();
	        		startActivity(i);//bắt đầu giao diện chinh
	        	}
        	}
       };
       timer.start();//bat dau chạy thread
    }
}
