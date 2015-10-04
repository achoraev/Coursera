package com.dailyselfie.selfie;

import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends ListActivity {

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    private ArrayList<Selfie> listObjects = new ArrayList<Selfie>();
    private Uri fileUri;
    ListView listView;
    public static String timeStamp;
    private AlarmManager mAlarmManager;
    private Intent mNotificationReceiverIntent;
    private PendingIntent mNotificationReceiverPendingIntent;

    private final long TWO_MINUTES = 2 * 60 * 1000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if (savedInstanceState != null) {
//            onRestoreInstanceState(savedInstanceState);
//        }

        listView = (ListView) findViewById(android.R.id.list);

        // alarm
        // Get the AlarmManager Service
        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Create an Intent to broadcast to the AlarmNotificationReceiver
        mNotificationReceiverIntent = new Intent(MainActivity.this,
                AlarmNotificationReceiver.class);

        // Create an PendingIntent that holds the NotificationReceiverIntent
        mNotificationReceiverPendingIntent = PendingIntent.getBroadcast(
                MainActivity.this, 0, mNotificationReceiverIntent, 0);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.camera) {
            Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            fileUri = getOutputMediaFileUri(REQUEST_IMAGE_CAPTURE);
            camera.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(camera, REQUEST_IMAGE_CAPTURE);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                Bitmap bitmap = null;

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(MainActivity.this.getContentResolver(), fileUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Selfie curSelf = new Selfie();
                curSelf.setUri(fileUri);
                curSelf.setBitmap(bitmap);
                listObjects.add(curSelf);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(MainActivity.this, "User cancel operation.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Capture failed.", Toast.LENGTH_SHORT).show();
            }
        }

        SelfieAdapter adapter = new SelfieAdapter(this, R.layout.selfie_row,
                listObjects);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent viewIntent = new Intent(Intent.ACTION_VIEW);
        viewIntent.setDataAndType(listObjects.get(position).getUri(), "image/*");
        startActivity(viewIntent);
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        listObjects = state.getParcelableArrayList("Selfies");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("Selfies", listObjects);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        mAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + TWO_MINUTES,
                TWO_MINUTES,
                mNotificationReceiverPendingIntent);
        super.onBackPressed();
    }

    private static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {
        File mediaStorageDir = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), "SelfieApp");

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.d("SelfieApp", "failed to create directory");
                    return null;
                }
            }
        }

        // Create a media file name
        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == REQUEST_IMAGE_CAPTURE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }
}
