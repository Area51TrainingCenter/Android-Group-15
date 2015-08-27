package pe.area51.notepad;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class NotepadContentProvider extends ContentProvider {

    public static final Uri URI = Uri.parse("content://pe.area51.notepad.ContentProvider");

    private SQLiteManager sqLiteManager;

    private static final int NOTES = 1;
    private static final int NOTES_ID = 2;
    private static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(URI.getAuthority(), "notes", NOTES);
        uriMatcher.addURI(URI.getAuthority(), "notes/#", NOTES_ID);
    }

    @Override
    public boolean onCreate() {
        sqLiteManager = new SQLiteManager(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (uriMatcher.match(uri) != UriMatcher.NO_MATCH) {
            if (uriMatcher.match(uri) == NOTES_ID) {
                selection = "_id=" + uri.getLastPathSegment();
            }
            return sqLiteManager.getReadableDatabase().query(SQLiteManager.TABLE_NOTES, projection, selection, selectionArgs, null, null, sortOrder);
        }
        return null;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case NOTES_ID:
                return NotesContract.MIME_ITEM;
            case NOTES:
                return NotesContract.MIME_DIR;
            default:
                return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (uriMatcher.match(uri) != UriMatcher.NO_MATCH) {
            final long id = sqLiteManager.getWritableDatabase().insert(SQLiteManager.TABLE_NOTES, null, values);
            return id != -1 ? ContentUris.withAppendedId(NotesContract.URI, id) : null;
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (uriMatcher.match(uri) != UriMatcher.NO_MATCH) {
            if (uriMatcher.match(uri) == NOTES_ID) {
                selection = "_id=" + uri.getLastPathSegment();
            }
            return sqLiteManager.getWritableDatabase().delete(SQLiteManager.TABLE_NOTES, selection, selectionArgs);
        }
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (uriMatcher.match(uri) != UriMatcher.NO_MATCH) {
            if (uriMatcher.match(uri) == NOTES_ID) {
                selection = "_id=" + uri.getLastPathSegment();
            }
            return sqLiteManager.getWritableDatabase().update(SQLiteManager.TABLE_NOTES, values, selection, selectionArgs);
        }
        return 0;
    }

}
