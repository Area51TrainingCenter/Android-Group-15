package pe.area51.notepad;

import android.net.Uri;

public class NotesContract {

    public static final Uri URI = Uri.parse("content://pe.area51.notepad.ContentProvider/notes");

    public static final String MIME_ITEM = "vnd.android.cursor.item/vnd.pe.area51.notepad.note";
    public static final String MIME_DIR = "vnd.android.cursor.dir/vnd.pe.area51.notepad.note";

    public static final String ID = "_id";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String UNIX_TIME = "unixTime";
}
