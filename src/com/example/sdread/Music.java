package com.example.sdread;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.provider.MediaStore.Audio.Media;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Music extends DrawerLay implements OnSeekBarChangeListener,OnClickListener{

	SeekBar seek;
	Switch t1;
	ImageButton play,pause,stop,ff,rew,next,prev;
	TextView cur,dur,name,artist;
	ImageView im;
	static MediaPlayer player;
	Thread autoseek;
	int flag=0,pos=0,f=0;
	MediaMetadataRetriever metaRetriver;
	byte []art;
	String sname="",spath="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_music);
		LayoutInflater inflater=(LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View contentView=inflater.inflate(R.layout.activity_music, null, false);
		drawerLayout.addView(contentView, 0);
		
		t1=(Switch)findViewById(R.id.switch1);
		play=(ImageButton)findViewById(R.id.play);
		pause=(ImageButton)findViewById(R.id.pause);
		stop=(ImageButton)findViewById(R.id.stop);
		ff=(ImageButton)findViewById(R.id.ff);
		rew=(ImageButton)findViewById(R.id.rew);
		next=(ImageButton)findViewById(R.id.next);
		prev=(ImageButton)findViewById(R.id.prev);
		cur=(TextView)findViewById(R.id.counter);
		dur=(TextView)findViewById(R.id.total);
		name=(TextView)findViewById(R.id.name);
		artist=(TextView)findViewById(R.id.art);
		im=(ImageView)findViewById(R.id.imview);
		seek=(SeekBar)findViewById(R.id.seek);
		seek.setOnSeekBarChangeListener(this);
		metaRetriver=new MediaMetadataRetriever();
		
		play.setOnClickListener(this);
		pause.setOnClickListener(this);
		stop.setOnClickListener(this);
		ff.setOnClickListener(this);
		rew.setOnClickListener(this);
		next.setOnClickListener(this);
		prev.setOnClickListener(this);
		pause.setEnabled(false);
		pause.setVisibility(View.INVISIBLE);
		
		Intent ri=getIntent();
		Bundle b=ri.getExtras();
		sname=b.getString("snm");
		spath=b.getString("pathname");
		pos=b.getInt("pos");
		getData(spath, sname);
		notification(spath,sname);
		
		autoseek=new Thread(){
			public void run()
			{
				while(player.getCurrentPosition()!=player.getDuration())
				{
					seek.setProgress(player.getCurrentPosition());
				}
			}
		};
		
		if(player!=null)
		{
			//player.pause();
			//player.stop();
			//player.release();
			player.reset();
			//player=null;
		}
		player=new MediaPlayer();
		
		try{
			//Toast.makeText(this, spath, 1000).show();
			play.setEnabled(false);
			play.setVisibility(View.INVISIBLE);
			pause.setEnabled(true);
			pause.setVisibility(View.VISIBLE);
			
			
			player.setDataSource(spath);
			player.prepare();
			
			dur.setText(convert(player.getDuration()));
			seek.setMax(player.getDuration());
			//Toast.makeText(this, "hi", Toast.LENGTH_LONG).show();
			
			
			player.start();
			autoseek.start();
			
		}catch(Exception o)
		{}
		
		t1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked)
					{
						player.setLooping(true);
						f=1;
					}
				else
					{
						player.setLooping(false);
						f=0;
					}
			}
		});
		
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("SMP");
	}
	
	public void getData(String songpath,String songname)
	{
		metaRetriver.setDataSource(songpath);
		try {
			art = metaRetriver.getEmbeddedPicture();
			Bitmap songImage = BitmapFactory.decodeByteArray(art, 0, art.length);
			im.setImageBitmap(songImage);
			name.setText(metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
			artist.setText(metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
		} catch (Exception e) {
			im.setImageResource(R.drawable.gr);
			name.setText("Unknown Album");
			artist.setText("Unknown Artist");
		}
	}
	
	/*public void playIt(String songpath,String songname)
	{
		autoseek=new Thread(){
			public void run()
			{
				while(player.getCurrentPosition()!=player.getDuration())
				{
					seek.setProgress(player.getCurrentPosition());
				}
			}
		};
		
		if(player!=null)
		{
			player.pause();
			player.stop();
			player.release();
		}
		try{
			//Toast.makeText(this, spath, 1000).show();
			player.setDataSource(songpath);
			player.prepare();
			
			dur.setText(convert(player.getDuration()));
			seek.setMax(player.getDuration());
			//Toast.makeText(this, "hi", Toast.LENGTH_LONG).show();
			
			player.start();
			autoseek.start();
			
			play.setEnabled(false);
			play.setVisibility(View.INVISIBLE);
			pause.setEnabled(true);
			pause.setVisibility(View.VISIBLE);
			
			
		}catch(Exception o)
		{}
	}*/
	
	String convert(long time)
	{
		long tt=time/1000;
		long tm=tt/60;
		long rt=tt%60;
		String s;
		if(rt>=10)
			s=tm+":"+rt;
		else
			s=tm+":0"+rt;
		return s;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.actionbar, menu);
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
    		return true;
    	}
		else if(id==R.id.omi1)
        {
        	Intent sp=new Intent(this,SettingPage.class);
			startActivity(sp);
			return true;
        }
        else if(id==R.id.omi2)
        {
        	Intent i=new Intent(this,MetaData.class);
			i.putExtra("path",spath );
			i.putExtra("song", sname);
			startActivity(i);
			return true;
        }
        else if(id==R.id.omi4)
        {
        	finish();
        }
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.play)
		{
			if(player.isPlaying()==false)
				player.start();
			play.setEnabled(false);
			play.setVisibility(View.INVISIBLE);
			pause.setEnabled(true);
			pause.setVisibility(View.VISIBLE);
			
		}
		else if(v.getId()==R.id.pause)
		{
			if(player.isPlaying())
				player.pause();
			pause.setEnabled(false);
			pause.setVisibility(View.INVISIBLE);
			play.setEnabled(true);
			play.setVisibility(View.VISIBLE);
		}
		else if(v.getId()==R.id.stop)
		{
			pause.setEnabled(false);
			pause.setVisibility(View.INVISIBLE);
			play.setEnabled(true);
			play.setVisibility(View.VISIBLE);
			player.pause();
			player.seekTo(0);
			flag=1;
		}
		else if(v.getId()==R.id.ff)
		{
			long t=player.getDuration();
			long tt=player.getCurrentPosition()+3000;
			if(tt<=t)
				player.seekTo((int) tt);
			else if(tt>t)
			{
				tt=t;
				player.seekTo((int) tt);
			}
		}
		else if(v.getId()==R.id.rew)
		{
			long t=0;
			long tt=player.getCurrentPosition()-3000;
			if(tt>=t)
				player.seekTo((int) tt);
			else if(tt<t)
			{
				tt=t;
				player.seekTo((int) tt);
			}
		}
		else if(v.getId()==R.id.next)
		{
			//next song
			//player.pause();
			
			pos=(pos+1)%songlist.af.size();
			
			//player.stop();
			//player.release();
			//player=null;
			player.reset();
			spath=songlist.af.get(pos).toString();
			sname=songlist.al.get(pos).toString();
			//autoseek.destroy();
			player=new MediaPlayer();
			getData(spath, sname);
			notification(spath,sname);
			//Toast.makeText(this, spath, Toast.LENGTH_LONG).show();
			try{
				//Toast.makeText(this, spath, 1000).show();
				play.setEnabled(false);
				play.setVisibility(View.INVISIBLE);
				pause.setEnabled(true);
				pause.setVisibility(View.VISIBLE);
				
				player.setDataSource(spath);
				player.prepare();
				
				dur.setText(convert(player.getDuration()));
				seek.setMax(player.getDuration());
				//Toast.makeText(this, "hi", Toast.LENGTH_LONG).show();
				//player.seekTo(0);
				player.start();
				
				new Thread(){
					public void run()
					{
						while(player.getCurrentPosition()!=player.getDuration())
						{
							seek.setProgress(player.getCurrentPosition());
						}
					}
				}.start();

			}catch(Exception o)
			{}
		}
		else if(v.getId()==R.id.prev)
		{
			//previous song
			pos--;
			if(pos<0)
				pos++;
			pos=pos%songlist.af.size();
			
			//player.stop();
			//player.release();
			//player=null;
			player.reset();
			spath=songlist.af.get(pos).toString();
			sname=songlist.al.get(pos).toString();
			player=new MediaPlayer();
			getData(spath, sname);
			notification(spath,sname);
			//Toast.makeText(this, spath, Toast.LENGTH_LONG).show();
			try{
				//Toast.makeText(this, spath, 1000).show();
				play.setEnabled(false);
				play.setVisibility(View.INVISIBLE);
				pause.setEnabled(true);
				pause.setVisibility(View.VISIBLE);
				//player.seekTo(0);
				
				player.setDataSource(spath);
				player.prepare();
				
				dur.setText(convert(player.getDuration()));
				seek.setMax(player.getDuration());
				//Toast.makeText(this, "hi", Toast.LENGTH_LONG).show();
				
				player.start();
				new Thread(){
					public void run()
					{
						while(player.getCurrentPosition()!=player.getDuration())
						{
							seek.setProgress(player.getCurrentPosition());
						}
					}
				}.start();
				
			}catch(Exception o)
			{}
		}
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		// TODO Auto-generated method stub
		if(fromUser)
			player.seekTo(progress);
		seek.setProgress(progress);
		cur.setText(convert(progress));
		
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		String nam=sname;
		String pa=spath;
		int po=pos;
		SharedPreferences pref=getSharedPreferences("songdata", 0);
		Editor editor=pref.edit();
		editor.putString("songn", nam);
		editor.putString("pathn", pa);
		editor.putInt("posi", po);
		editor.commit();
		
		super.onStop();
	}
	

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
	
	public void notification(String songpath,String songname)
	{
		Intent bi=new Intent();
		bi.putExtra("songn", sname);
		bi.putExtra("pathn", spath);
		bi.setAction("com.Music.BROADCAST");
		sendBroadcast(bi);
	}
}
