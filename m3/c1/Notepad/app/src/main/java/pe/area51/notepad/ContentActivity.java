package pe.area51.notepad;

import android.app.Activity;
import android.os.Bundle;

public class ContentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        final Note note = getIntent().getParcelableExtra(MainActivity.EXTRA_NOTE);
        ((ContentFragment) getFragmentManager().findFragmentById(R.id.activity_content_fragment_content)).showNote(note);
    }

}
