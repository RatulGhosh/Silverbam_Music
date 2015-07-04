package com.example.sdread;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.OpenableColumns;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

public class DrawerLay extends Activity implements OnItemClickListener {

	public DrawerLayout drawerLayout;
	public ListView listView;
	public String []list;
	public ActionBarDrawerToggle drawerListener;
	boolean isLaunch=true;
	protected FrameLayout frameLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drawer_lay);
		
		drawerLayout=(DrawerLayout)findViewById(R.id.drawerLayout);
		listView=(ListView)findViewById(R.id.drawerList);
		list=getResources().getStringArray(R.array.Drawerlist);
		listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list));
		listView.setOnItemClickListener(this);
		drawerListener=new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer,
				R.string.drawer_open, R.string.drawer_close){
			
			@Override
			public void onDrawerClosed(View drawerView) {
				// TODO Auto-generated method stub
				super.onDrawerClosed(drawerView);
			}
			
			@Override
					public void onDrawerOpened(View drawerView) {
						// TODO Auto-generated method stub
						super.onDrawerOpened(drawerView);
					}
		};
		drawerListener.setDrawerIndicatorEnabled(true);
		drawerLayout.setDrawerListener(drawerListener);
		
		if(isLaunch)
		{
			isLaunch=false;
			//OpenActivity(1);
			
		}
		
		getActionBar().setTitle("Silverbam");
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
	}
	
	public void OpenActivity(int pos) {
		// TODO Auto-generated method stub
		//Toast.makeText(this, String.valueOf(pos), Toast.LENGTH_LONG).show();
		switch(pos)
		{
		case 0:startActivity(new Intent(this,MainActivity.class));
				break;
		case 1:SharedPreferences pref=getSharedPreferences("songdata", 0);
				String song=pref.getString("songn", songlist.al.get(0));
				String path=pref.getString("pathn", songlist.af.get(0).toString());
				int posi=pref.getInt("posi", 0);
				Intent i=new Intent(this,Music.class);
				i.putExtra("snm", song);
				i.putExtra("pathname", path);
				i.putExtra("pos", posi);
				startActivity(i);
				break;
				
		case 2:break;
		case 3:startActivity(new Intent(this,SettingPage.class));	
				break;
		case 4:startActivity(new Intent(this,About.class));
				break;
			
		case 5:finish();
				break;
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		drawerListener.onConfigurationChanged(newConfig);
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);
		drawerListener.syncState();
		
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
		if(drawerListener.onOptionsItemSelected(item))
			return true;
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
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		selectItem(position);
		OpenActivity(position);
	}

	public void selectItem(int position) {
		// TODO Auto-generated method stub
		listView.setItemChecked(position, true);
		setTitle(list[position]);
	}
	public void setTitle(String title)
	{
		getActionBar().setTitle(title);
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if(drawerLayout.isDrawerOpen(listView))
			drawerLayout.closeDrawer(listView);
		else
			drawerLayout.openDrawer(listView);
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		drawerLayout.closeDrawer(listView);
		super.onStop();
	}

}
