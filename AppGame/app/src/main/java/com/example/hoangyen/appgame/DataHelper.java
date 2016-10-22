package com.example.hoangyen.appgame;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHelper extends SQLiteOpenHelper {

	private static String DB_PATH = "/data/data/com.example.hoangyen.appgame/databases/";//lấy đường dẫn tới csdl
    private static String DB_NAME = "datagame.db";//ten csdl
    public SQLiteDatabase myDataBase; //đối tượng database ta sẽ làm việc với dt này
    private final Context myContext;// trạng thái của class DataHelper
 
    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DataHelper(Context context)//tạo contructor
    {
 
    	super(context, DB_NAME, null, 1);
        this.myContext = context;
    }	
    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException// hàm khởi tạo database
    {
    	boolean dbExist = checkDataBase();//kiểm tra xem ten của database tồn tại chưa
    	if(dbExist){
    		//ko làm gì hết
    	}else{
    		//By calling this method and empty database will be created into the default system path
               //of your application so we are gonna be able to overwrite that database with our database.
        	this.getReadableDatabase();//lấy csdl ra từ thư mục assets
        	try {
    			copyDataBase();//copy csdl vào máy ảo
    		} catch (IOException e) {
        		throw new Error("Error copying database");
        	}
    	}
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
    	SQLiteDatabase checkDB = null;
    	try{
    		String myPath = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    	}catch(SQLiteException e){
    		//database does't exist yet.
    	}
    	if(checkDB != null){
    		checkDB.close();
    	}
    	return checkDB != null ? true : false;
    }
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{
    	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(DB_NAME);//đọc dữ liệu từ file system
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME;
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);//ghi dữ liệu từ file hệ thống
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
    }
    //mở cơ sở dữ liệu
    public void openDataBase() throws SQLException
    {
    	//Open the database
        String myPath = DB_PATH + DB_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }
    @Override
	public synchronized void close() {
    	    if(myDataBase != null)
    		    myDataBase.close();
    	    super.close();
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
	}
	//trả về câu truy ván chứa trong cursor
	public Cursor SELECTSQL(String sql) {
		return myDataBase.rawQuery(sql, null);
	}
	// phuong thuc nay de dong DB khi khong su dung
	public void CloseBD() {
		if (myDataBase != null && myDataBase.isOpen())
			myDataBase.close();
	}
 
}
