package pe.area51.notepad;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collection;

public class SQLiteManager extends SQLiteOpenHelper {

    //SQLite NO cifra los valores que se ingresan a la base de datos.
    public static final String DATABASE_NAME = "unencrypted-notes-database";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NOTES = "notes";

    public SQLiteManager(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
        El framework de Android espera en algunas clases que el ID tenga como nombre "_id"
        (como en el CursorAdapter por ejemplo). Es por esta razón que es recomendable que el ID
        tenga como nombre "_id".
        */
        final String sql = "CREATE TABLE " + TABLE_NOTES + " (_id INTEGER PRIMARY KEY, unixTime INTEGER, title TEXT, content TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Este método no debería ejecutarse puesto que nuestra versión de la base de datos es 1.
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Este método no debería ejecutarse puesto que nuestra versión de la base de datos es 1.
    }

    public Collection<Note> getNotes() {
        final Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NOTES, null);
        final Collection<Note> notes = new ArrayList<>();
        while (cursor.moveToNext()) {
            final long id = cursor.getLong(cursor.getColumnIndex("_id"));
            final long unixTime = cursor.getLong(cursor.getColumnIndex("unixTime"));
            final String title = cursor.getString(cursor.getColumnIndex("title"));
            final String content = cursor.getString(cursor.getColumnIndex("content"));
            notes.add(new Note(id, unixTime, title, content));
        }
        cursor.close();
        return notes;
    }
}
