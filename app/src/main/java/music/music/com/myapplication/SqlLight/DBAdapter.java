package music.music.com.myapplication.SqlLight;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBAdapter {

    Context c;

    SQLiteDatabase db;
    DBHelper helper;

    public DBAdapter(Context c) {
        this.c = c;
        helper=new DBHelper(c);
    }

    //OPEN DATABASE
    public DBAdapter openDB()
    {
        try {
            db=helper.getWritableDatabase();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return this;
    }

    //CLOSE DATABASE
    public void closeDB()
    {
        try {
            helper.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    //INSERT
    public long add(String name,String pos)
    {
        try
        {
            ContentValues cv=new ContentValues();
            cv.put(Constants.NAME,name);
            cv.put(Constants.POSITION,pos);


            return db.insert(Constants.TB_NAME,Constants.ROW_ID,cv);
        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }

    //INSERT
    public long add1(String name,String pos)
    {
        try
        {
            ContentValues cv=new ContentValues();
            cv.put(Constants.DIGNOSIS,name);
            cv.put(Constants.INSTRUCTION,pos);


            return db.insert(Constants.TD_NAME,Constants.ROW_ID1,cv);
        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;

    }

    //RETRIEVE
    public Cursor getAllPlayers()
    {
        String[] columns={Constants.ROW_ID,Constants.NAME,Constants.POSITION};

        return db.query(Constants.TB_NAME,columns,null,null,null,null,null);

    }

    public Cursor getAllPlayers1()
    {
        String[] columns={Constants.ROW_ID1,Constants.DIGNOSIS,Constants.INSTRUCTION};

        return db.query(Constants.TD_NAME,columns,null,null,null,null,null);

    }

    public void delete(int id) {
       // db.execSQL("delete from "+Constants.TB_NAME+" where id='"+id+"'");

        db.delete(Constants.TB_NAME, "id=?", new String[]{Integer.toString(id)});
    }

    public void delete1(int id) {
       // db.execSQL("delete from "+Constants.TD_NAME+" where id='"+id+"'");

        db.delete(Constants.TD_NAME, "id=?", new String[]{Integer.toString(id)});
    }

    public void deleteall() {

        db.delete(Constants.TB_NAME, null, null);
    }

    public void deleteall2() {

        db.delete(Constants.TD_NAME, null, null);
    }
}