package com.example.sdread;

import org.w3c.dom.Text;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MetaData extends Activity {

	ImageView im;
	TextView name,artist;
	MediaMetadataRetriever metaRetriver;
	byte []art;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meta_data);
		name=(TextView)findViewById(R.id.name);
		artist=(TextView)findViewById(R.id.artist);
		im=(ImageView)findViewById(R.id.image);
		metaRetriver=new MediaMetadataRetriever();
		
		Intent i=getIntent();
		Bundle b=i.getExtras();
		String p=b.getString("path");
		String sname=b.getString("song");
		//Toast.makeText(this, sname,Toast.LENGTH_LONG).show();
		//Toast.makeText(this, p, Toast.LENGTH_LONG ).show();
		getDetails(p,sname);
		
		ActionBar ab=getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setTitle("About");
		ab.show();
	}
	
	public void getDetails(String songpath,String songname)
	{
		String s="";
		metaRetriver.setDataSource(songpath);
		try {
			art = metaRetriver.getEmbeddedPicture();
			Bitmap songImage = BitmapFactory.decodeByteArray(art, 0, art.length);
			im.setImageBitmap(songImage);
			name.setText(metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
			artist.setText(metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
		} catch (Exception e) {
			im.setBackgroundColor(Color.GRAY);
			name.setText("Unknown Album");
			artist.setText("Unknown Artist");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.meta_data, menu);
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
		else if(id==android.R.id.home)
		{
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}
}
