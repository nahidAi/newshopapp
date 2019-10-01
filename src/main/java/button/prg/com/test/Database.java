package button.prg.com.test;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class Database extends SQLiteOpenHelper{
	private Context context;
	public Database (Context context) {
		super( context, info_db.DataBase_Name, null, info_db.DataBase_Version );
		this.context = context;
		Isdatabase();
	}

	@Override
	public void onCreate (SQLiteDatabase sqLiteDatabase) {


	}

	@Override
	public void onUpgrade (SQLiteDatabase sqLiteDatabase, int i, int i1) {

	}
	private void Isdatabase(){
		File check = new File( info_db.Package );
		if (!check.exists())
		{
			check.mkdir();
		}
		check = context.getDatabasePath( info_db.DataBase_Name );
		if (!check.exists()){
			try{
				copyDataBase();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private void copyDataBase() throws IOException {
		// Open your local db as the input stream
		InputStream myInput =context.getAssets().open(info_db.DataBase_Source);
		// Path to the just created empty db
		String outFileName = info_db.Package+info_db.DataBase_Name;
		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);
		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}
		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();
	}

	public List<info_Data> fetchmaindata()
	{
		SQLiteDatabase db = this.getReadableDatabase();
		List<info_Data> data = new ArrayList<>(  );
		String query = "SELECT "+info_db.Data_Name+","+info_db.Data_Body+","+info_db.Data_Fav+","+info_db.Data_Image+","+info_db.Data_Free+" FROM tbl";
		Cursor cursor = db.rawQuery( query,null );
		if (cursor.moveToFirst())
		{
			do {
				info_Data info = new info_Data();
				info.setName( cursor.getString( cursor.getColumnIndex( info_db.Data_Name ) ) );
				info.setBody( cursor.getString( cursor.getColumnIndex( info_db.Data_Body ) ) );
				info.setFav( cursor.getInt( cursor.getColumnIndex( info_db.Data_Fav ) ) );
				info.setImage( cursor.getString( cursor.getColumnIndex( info_db.Data_Image ) ) );
				boolean value = cursor.getInt(cursor.getColumnIndex(info_db.Data_Free))> 0;
				info.setIsFree(value);
				data.add( info );

			}while (cursor.moveToNext());
		}
		cursor.close();
		db.close();

		return data;
	}

	public int fav_value(int id)
	{
		SQLiteDatabase db=this.getReadableDatabase();
		String query = "SELECT "+info_db.Data_Fav+" FROM "+info_db.Table_name+" where "+info_db.Data_Id+"="+id+"";
		int value=0;
		Cursor cursor=db.rawQuery( query,null );
		if (cursor.moveToFirst())
		{
			value=cursor.getInt( cursor.getColumnIndex( info_db.Data_Fav ) );
			do {

			}while (cursor.moveToNext());

		}
		db.close();

		return value;
	}
	public void fav(int status,int id)
	{
		SQLiteDatabase db=this.getWritableDatabase();
		String query="update "+info_db.Table_name+" set "+info_db.Data_Fav+"="+status+" where "+info_db.Data_Id+"="+id+"";
		db.execSQL( query );
		db.close();
	}

	public List<info_Data> search(String sw)
	{
		SQLiteDatabase db=this.getReadableDatabase();
		List<info_Data> data = new ArrayList<>(  );
		String query = "SELECT * FROM "+info_db.Table_name+" where "+info_db.Data_Name+" like '%"+sw+"%'";
		Cursor cursor = db.rawQuery( query,null );
		if (cursor.moveToFirst())
		{
			do {
				info_Data info = new info_Data();
				info.setName( cursor.getString( cursor.getColumnIndex( info_db.Data_Name ) ) );
				info.setBody( cursor.getString( cursor.getColumnIndex( info_db.Data_Body ) ) );
				info.setFav( cursor.getInt( cursor.getColumnIndex( info_db.Data_Fav ) ) );
				info.setImage( cursor.getString( cursor.getColumnIndex( info_db.Data_Image ) ) );
				data.add( info );

			}while (cursor.moveToNext());
		}
		cursor.close();
		db.close();

		return data;

	}
	public List<info_Data>favorite()
	{
		SQLiteDatabase db=this.getReadableDatabase();
		List<info_Data>data=new ArrayList<>(  );
		String query="select * from "+info_db.Table_name+" where "+info_db.Data_Fav+"=1";
		Cursor cursor=db.rawQuery( query,null );
		if (cursor.moveToFirst())
		{
			do {
				info_Data info=new info_Data();
				info.setName( cursor.getString(cursor.getColumnIndex( info_db.Data_Name )  ) );
				info.setBody( cursor.getString( cursor.getColumnIndex( info_db.Data_Body ) ) );
				info.setFav( cursor.getInt( cursor.getColumnIndex( info_db.Data_Fav ) ) );
				info.setImage( cursor.getString( cursor.getColumnIndex( info_db.Data_Image ) ) );
				data.add( info );


			}while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return data;
	}
	public int returnsize()
	{
		SQLiteDatabase db=this.getReadableDatabase();
		String query="select "+info_db.Data_size+" from "+info_db.Table_setting+"";
		Cursor cursor=db.rawQuery( query,null );
		int value=0;
		if (cursor.moveToFirst())
		{
			do {
				value=cursor.getInt( cursor.getColumnIndex( info_db.Data_size ) );

			}while (cursor.moveToNext());
		}
		cursor.close();
		db.close();

		return value;
	}
	public void updatesize(int size)
	{
		SQLiteDatabase db=this.getWritableDatabase();
		String query="update "+info_db.Table_setting+" set "+info_db.Data_size+"="+size+"";
		db.execSQL( query );
		db.close();
	}

}
