package com.example.wlghk.alticastdeliv;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    DBHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        cursor = db.rawQuery("SELECT * FROM menulist", null);

        while(cursor.moveToNext()){
            break;
        }
    }

    class DBHelper extends SQLiteOpenHelper {
        static final String DATABASE_NAME = "menu.db";
        static final int DATABASE_VERSION =  1;

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE menulist (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, price INTEGER, kind TEXT, storename TEXT);");

            //땅땅치킨 메뉴
            db.execSQL("INSERT INTO menulist VALUES (null, '골드크런치킹', 19900, '치킨', '땅땅치킨 수원성대점') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '독도애(愛)촌닭', 16900, '치킨', '땅땅치킨 수원성대점') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '매콤찹스', 17000, '치킨', '땅땅치킨 수원성대점') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '뼈닭', 13000, '치킨', '땅땅치킨  수원성대점') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '서울치킨', 16900, '치킨', '땅땅치킨 수원성대점') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '순살 양념 플러스', 19000, '치킨', '땅땅치킨 수원성대점') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '순살 양반후반 플러스', 19000, '치킨', '땅땅치킨 수원성대점') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '순살 후라이드 플러스', 18000, '치킨', '땅땅치킨 수원성대점') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '프리미엄 땅땅불갈비', 17900, '치킨', '땅땅치킨 수원성대점') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '핫 홀릭 치킨', 16500, '치킨', '땅땅치킨 수원성대점') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '핫불갈비', 16900, '치킨', '땅땅치킨 수원성대점') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '허브소이치킨', 15900, '치킨', '땅땅치킨 수원성대점') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '허브순살치킨', 15900, '치킨', '땅땅치킨 수원성대점') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '후왕', 17500, '치킨', '땅땅치킨 수원성대점') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '세트1. 허브순살치킨+불닭', 18500, '치킨', '땅땅치킨 수원성대점') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '세트2. 불닭+땅땅불갈비', 18500, '치킨', '땅땅치킨 수원성대점') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '세트3. 허브순살치킨+땅땅불갈비', 18500, '치킨', '땅땅치킨 수원성대점') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '세트4. 후왕+땅땅불갈비', 18500, '치킨', '땅땅치킨 수원성대점') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '세트5. 후왕+매콤찹스', 18500, '치킨', '땅땅치킨 수원성대점') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '세트5. 후왕+매콤찹스', 18500, '치킨', '땅땅치킨 수원성대점') ");

            //도미노피자
            db.execSQL("INSERT INTO menulist VALUES (null, '7치즈 앤 그릴드 비프', 33900, '피자', '도미노피자 성균관대 자과캠(수원점)') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '글램핑 바비큐', 33900, '피자', '도미노피자 성균관대 자과캠(수원점)') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '베이컨체더치즈', 25900, '피자', '도미노피자 성균관대 자과캠(수원점)') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '불고기', 24900, '피자', '도미노피자 성균관대 자과캠(수원점)') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '블랙타이거 슈림프', 33900, '피자', '도미노피자 성균관대 자과캠(수원점)') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '슈퍼디럭스', 25900, '피자', '도미노피자 성균관대 자과캠(수원점)') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '슈퍼슈프림', 25900, '피자', '도미노피자 성균관대 자과캠(수원점)') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '알로하 하와이안', 23900, '피자', '도미노피자 성균관대 자과캠(수원점)') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '와규 앤 비스테카', 33900, '피자', '도미노피자 성균관대 자과캠(수원점)') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '직화 스테이크', 33900, '피자', '도미노피자 성균관대 자과캠(수원점)') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '치즈', 21900, '피자', '도미노피자 성균관대 자과캠(수원점)') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '치즈케이크 롤', 33900, '피자', '도미노피자 성균관대 자과캠(수원점)') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '콰트로 치즈 퐁듀, 23900', '피자', '도미노피자 성균관대 자과캠(수원점)') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '킹프론 씨푸드', 34900, '피자', '도미노피자 성균관대 자과캠(수원점)') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '페퍼로니', 22900, '피자', '도미노피자 성균관대 자과캠(수원점)') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '포테이토', 25900, '피자', '도미노피자 성균관대 자과캠(수원점)') ");

            //일품향
            db.execSQL("INSERT INTO menulist VALUES (null, '고추짬뽕', 6500, '중국집', '일품향') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '짬뽕', 5500, '중국집', '일품향') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '짜장면', 4500, '중국집', '일품향') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '탕수육', 11000, '중국집', '일품향') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '짜장밥', 6500, '중국집', '일품향') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '볶음짬뽕', 6500, '중국집', '일품향') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '해물육교자', 5500, '중국집', '일품향') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '군만두', 4000, '중국집', '일품향') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '쟁반짜장', 12000, '중국집', '일품향') ");
            db.execSQL("INSERT INTO menulist VALUES (null, '깐풍기', 12000, '중국집', '일품향') ");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
