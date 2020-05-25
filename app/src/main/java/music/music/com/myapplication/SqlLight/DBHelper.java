package music.music.com.myapplication.SqlLight;

/**
 * Created by Kalpesh on 19-05-2020.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static music.music.com.myapplication.SqlLight.Constants.TB_NAME;
import static music.music.com.myapplication.SqlLight.Constants.TD_NAME;


public class DBHelper extends SQLiteOpenHelper {

    SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    //TABLE CREATION
    @Override
    public void onCreate(SQLiteDatabase db) {
        try
        {
            db.execSQL(Constants.CREATE_TB);
            db.execSQL(Constants.CREATE_TB_DIGNO);

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    //TABLE UPGRADE
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TB_NAME);
       db.execSQL("DROP TABLE IF EXISTS "+ TD_NAME);
        onCreate(db);

    }


}