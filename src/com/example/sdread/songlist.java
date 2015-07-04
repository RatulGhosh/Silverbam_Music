package com.example.sdread;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class songlist {

	public static ArrayList<String> al;
	public static ArrayList<File> af;
	public static ArrayList<String> fav;
	
	public static void sortasc()
	{
		String s="";
		File f;
		for(int i=0;i<al.size();i++)
		{
			for(int j=i;j<al.size()-1;j++)
			{
				if(al.get(j).compareTo(al.get(j+1))>0)
				{
					Collections.swap(al, j, j+1);
					Collections.swap(af, j, j+1);
				}
			}
		}
	}
	
	public static void sortdesc()
	{
		String s="";
		File f;
		for(int i=0;i<al.size();i++)
		{
			for(int j=i;j<al.size()-1;j++)
			{
				if(al.get(j).compareTo(al.get(j+1))<0)
				{
					Collections.swap(al, j, j+1);
					Collections.swap(af, j, j+1);
				}
			}
		}
	}
	
	public static void shuffle()
	{
		int len=al.size(),k;
		Random random = new Random();
		for(int i=0;i<al.size();i++)
		{
			k=random.nextInt(len-1);
			Collections.swap(al, i, k);
			Collections.swap(af, i, k);
		}
	}
	
}
