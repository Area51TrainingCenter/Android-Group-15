package pe.area51.camera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends Activity {

    private final static int REQUEST_TAKE_PHOTO = 1;

    private ImageView photoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        photoImageView = (ImageView) findViewById(R.id.activity_main_imageview_photo);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_take_photo) {
            final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (isActivityAvailable(intent)) {
                startActivityForResult(intent, REQUEST_TAKE_PHOTO);
            } else {
                Toast.makeText(this, R.string.take_photo_error, Toast.LENGTH_LONG).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            final Bitmap photoBitmap = (Bitmap) data.getExtras().get("data");
            photoImageView.setImageBitmap(photoBitmap);
        }
    }

    private boolean isActivityAvailable(final Intent intent) {
        return intent.resolveActivity(getPackageManager()) != null;
    }
}
