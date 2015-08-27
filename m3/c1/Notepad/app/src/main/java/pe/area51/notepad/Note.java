package pe.area51.notepad;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {

    private final long id;
    private final long unixTime;
    private final String title;
    private final String content;

    /*
    Debe leerse en el mismo orden en que se escribió.
     */
    public Note(final Parcel parcel) {
        this.id = parcel.readLong();
        this.unixTime = parcel.readLong();
        this.title = parcel.readString();
        this.content = parcel.readString();
    }

    public Note(final long id, final long unixTime, final String title, final String content) {
        this.id = id;
        this.unixTime = unixTime;
        this.title = title;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public long getUnixTime() {
        return unixTime;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Id: " + id + ", UnixTime: " + unixTime + ", Title: " + title + ", Content: " + content;
    }

    /*
    Este método debe devolver "0" o "1" (constante
    "Parcelable.CONTENTS_FILE_DESCRIPTOR"). Se utiliza la constante
    "CONTENTS_FILE_DESCRIPTOR" (o 1), cuando se implementa la interfaz
    "Parcelable" con la clase "FileDescriptor". En todos los otros casos,
    debe devolverse 0.
    */
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeLong(this.id);
        dest.writeLong(this.unixTime);
        dest.writeString(this.title);
        dest.writeString(this.content);
    }

    /*
    El objeto "Creator" es necesario para recrear nuestro objeto
    a partir de un "Parcel". Debe crearse como público y estático.
    */
    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(final Parcel source) {
            return new Note(source);
        }

        @Override
        public Note[] newArray(final int size) {
            return new Note[size];
        }
    };
}
