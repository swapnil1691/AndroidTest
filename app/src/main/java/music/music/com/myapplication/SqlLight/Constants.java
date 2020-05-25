package music.music.com.myapplication.SqlLight;

/**
 * Created by Kalpesh on 19-05-2020.
 */

public class Constants {

    static final String ROW_ID="id";
    static final String NAME = "name";
    static final String POSITION = "position";
    static final String DOSE = "dose";
    static final String STRENTH = "strenth";
    static final String ADVICE = "advice";
    static final String DURATION = "duratiom";


    static final String ROW_ID1="id";
    static final String DIGNOSIS = "dignosis";
    static final String INSTRUCTION = "instruction";


    //DB PROPERTIES
    static final String DB_NAME="d_DBBB";
    static final String TB_NAME="d_TB";
    static final String TD_NAME="dn_TB";
    static final int DB_VERSION='1';

    static final String CREATE_TB="CREATE TABLE d_TB(id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "name TEXT NOT NULL,position TEXT NOT NULL);";



    static final String CREATE_TB_DIGNO="CREATE TABLE dn_TB(id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "dignosis TEXT NOT NULL,instruction TEXT NOT NULL);";



}
