package pe.area51.audiorecorder;

import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends Activity {

    private MediaRecorder mediaRecorder;
    private TextView statusTextView;
    private boolean isRecording;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        statusTextView = (TextView) findViewById(R.id.activity_main_textview_status);
        isRecording = false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_switch_recording:
                if(isRecording){
                    stopRecording();
                    releaseRecorder();
                    isRecording = false;
                    item.setTitle(R.string.rec);
                    statusTextView.setText(R.string.ready);
                }else{
                    try {
                        initMediaRecorder();
                        startRecording();
                        isRecording = true;
                        item.setTitle(R.string.stop);
                        statusTextView.setText(R.string.recording);
                    } catch (IOException e) {
                        e.printStackTrace();
                        releaseRecorder();
                        Toast.makeText(this, R.string.rec_error, Toast.LENGTH_LONG).show();
                    }
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initMediaRecorder() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        final String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        final String fileName = "voice_recording_" + System.currentTimeMillis() + ".3gp";
        mediaRecorder.setOutputFile(path + "/" + fileName);
    }

    private void startRecording() throws IOException {
        mediaRecorder.prepare();
        mediaRecorder.start();
    }

    private void stopRecording(){
        mediaRecorder.stop();
    }

    private void releaseRecorder(){
        mediaRecorder.reset();
        mediaRecorder.release();
        mediaRecorder = null;
    }
}
