package com.example.sdread;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends ListActivity {

	ArrayAdapter<String> ad;
	
	File sdcard;
	String path="";
	int flag=0;
	File f1;
	ListView list;
	int pos;
	static File n;
	String nam="";
	boolean check=true;
	
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getLayoutInflater().inflate(R.layout.activity_music, frameLayout);
        
        songlist.al=new ArrayList<String>();
        songlist.af=new ArrayList<File>();
        
        sdcard=Environment.getExternalStorageDirectory();
        path=sdcard.getPath();
        
        	File files[]=sdcard.listFiles();
        	for(File f:files)
        	{
        		//Toast.makeText(this, "HI", 1000).show();
        		search(f);
        	}
        //Collections.sort(songlist.al);
        songlist.sortasc();
        list=(ListView)findViewById(android.R.id.list);
        ad= new ArrayAdapter<String>(this,R.layout.songlist,R.id.text,songlist.al);
        list.setAdapter(ad);
        registerForContextMenu(list);
        
        getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		SharedPreferences s=PreferenceManager.getDefaultSharedPreferences(this);
		String c=s.getString("sort", "Sort By A-Z");
		if(c.equals("Sort by A-Z"))
			songlist.sortasc();
			//Toast.makeText(this,"Hi", Toast.LENGTH_LONG).show();
		else if(c.equals("Sort by Z-A"))
			songlist.sortdesc();
			//Toast.makeText(this,"Hi", Toast.LENGTH_LONG).show();
		else if(c.equals("Shuffle"))
			songlist.shuffle();
			//Toast.makeText(this,"Hi", Toast.LENGTH_LONG).show();
		else
			songlist.sortasc();
			//Toast.makeText(this,"Hi", Toast.LENGTH_LONG).show();
		
		getActionBar().setTitle("All Songs");
    }

    public void search(File f)
    {
    	if(f.isDirectory() && (!f.isHidden()))
    	{
    		//Toast.makeText(this, String.valueOf(f.isDirectory()), 1000).show();
    		File []files=f.listFiles();
    		for(File fsub:files)
    		{
    			if(fsub.isDirectory() && (!fsub.isHidden()))
    				search(fsub);
    			else
    				checkFile(fsub);
    		}
    	}
    	else
    		checkFile(f);
   }
    
    public void checkFile(File f)
    {
    	if((f.getName()).endsWith(".mp3"))
    	{
    		//Toast.makeText(this, f.getName(), 1000).show();
    		songlist.al.add(f.getName().toString());
    		songlist.af.add(f);
    		flag=1;
    	}
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	// TODO Auto-generated method stub
    	pos=position;
    	n=songlist.af.get(position);
    	nam=songlist.al.get(position);
    	//Toast.makeText(this, nam+" "+n.getAbsolutePath(), 1000).show();
    	
    	Intent i=new Intent(this,Music.class);
    	i.putExtra("pathname", n.toString());
    	i.putExtra("snm",nam );
    	i.putExtra("pos", position);
    	startActivity(i);
    	
    	super.onListItemClick(l, v, position, id);
    	
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
        	Intent sp=new Intent(this,About.class);
			startActivity(sp);
			return true;
        }
        else if(id==R.id.omi4)
        {
        	finish();
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onBackPressed() {
    	// TODO Auto-generated method stub
    	finish();
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
    	// TODO Auto-generated method stub
    	super.onCreateContextMenu(menu, v, menuInfo);
    	if(v.getId()==android.R.id.list)
    	{
    		menu.setHeaderTitle("Options");
    		menu.setHeaderIcon(android.R.drawable.ic_dialog_info);
    		menu.add(0,v.getId(),0,"Add To Favourites");
    		menu.add(0,v.getId(),1,"About");
    	}
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	// TODO Auto-generated method stub
    	 AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
    	 int index=info.position;
    	 nam=item.getTitle().toString();
    	 n=songlist.af.get(index);
    	 String pa=n.toString();
    	if(item.getTitle()=="About")
    		{
    			//Toast.makeText(this, String.valueOf(index), Toast.LENGTH_LONG).show();
    			//Toast.makeText(this, pa, Toast.LENGTH_LONG).show();
    			Intent i=new Intent(this,MetaData.class);
    			i.putExtra("path",pa );
    			i.putExtra("song", nam);
    			startActivity(i);
    		}
    	else
    		return super.onContextItemSelected(item);
    	return true;
    }
}
