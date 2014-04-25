package com.example.camerasv;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.io.FileOutputStream;

import android.hardware.Camera.PictureCallback;
import android.hardware.Camera;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.util.Log;
import android.widget.Toast;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity {

	Button openb;
	
	  private static final String TAG = "CallCamera";
	  private static final int CAPTURE_IMAGE_ACTIVITY_REQ = 0;
	  Uri fileUri = null;
	  ImageView photoImage = null;

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openb = (Button) findViewById(R.id.button1);
        
        //Listen for button push 
        openb.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            //Code goes here to be triggered by button 
            	Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = getOutputPhotoFile();
                fileUri = Uri.fromFile(getOutputPhotoFile());
                i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(i, CAPTURE_IMAGE_ACTIVITY_REQ );
                
            }
         });
    }
   
    
    
    private File getOutputPhotoFile() {
    	  File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), getPackageName());
    	  if (!directory.exists()) {
    	    if (!directory.mkdirs()) {
    	      Log.e(TAG, "Failed to create storage directory.");
    	      return null;
    	    }
    	  }
    	  String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss", Locale.UK).format(new Date());
    	  return new File(directory.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
    	}
    
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	  if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQ) {
    	    if (resultCode == RESULT_OK) {
    	      Uri photoUri = null;
    	      if (data == null) {
    	        // A known bug here! The image should have saved in fileUri
    	        Toast.makeText(this, "Image saved successfully", Toast.LENGTH_LONG).show();
    	        photoUri = fileUri;
    	      } else {
    	        photoUri = data.getData();
    	        Toast.makeText(this, "Image saved successfully in: " + data.getData(), Toast.LENGTH_LONG).show();
    	      }
    	      // showPhoto(photoUri);
    	    } else if (resultCode == RESULT_CANCELED) {
    	      Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
    	    } else {
    	      Toast.makeText(this, "Callout for image capture failed!", Toast.LENGTH_LONG).show();
    	    }
    	  }
    	}
    
    
    
    //-----------Don't touch stuff below here----------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {   
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
