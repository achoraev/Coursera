package com.mymodernui.modern;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    int colorCode = 0;
    String url = "http://www.moma.org/m";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar slider = (SeekBar) findViewById(R.id.seekBar);
        final TextView topLeft = (TextView) findViewById(R.id.top_left);
        final TextView bottomLeft = (TextView) findViewById(R.id.bottom_left);
        final TextView topRight = (TextView) findViewById(R.id.top_right);
        final TextView centerRight = (TextView) findViewById(R.id.center_right);
        final TextView bottomRight = (TextView) findViewById(R.id.bottom_right);

        slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                colorCode = progress;
                topLeft.setBackgroundColor(Color.rgb(colorCode,colorCode,243));
                bottomLeft.setBackgroundColor(Color.rgb(244,colorCode,177));
                topRight.setBackgroundColor(Color.rgb(25,colorCode,colorCode));
                centerRight.setBackgroundColor(Color.rgb(238,142,colorCode));
                bottomRight.setBackgroundColor(Color.rgb(156,colorCode,176));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.more_info) {
            createDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyCustomAlertTheme);
        builder.setTitle(getString(R.string.alert_title))
                .setMessage(getString(R.string.alert_msg))
                .setNegativeButton(getString(R.string.visit_moma), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                    }
                })
                .setPositiveButton(getString(R.string.not_now), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }
}