package org.tayloredapps.RssWidget.Models;

import java.util.List;

import org.tayloredapps.RssWidget.RssWidgetApplication;

import com.orm.androrm.CharField;
import com.orm.androrm.IntegerField;
import com.orm.androrm.Model;
import com.orm.androrm.QuerySet;

public class RssFeed extends Model
{
	
	protected CharField title;
	protected CharField url;
	protected IntegerField id;

	public RssFeed()
	{
		super(true);
		
		title = new CharField();
		url = new CharField();
		id = new IntegerField();
	}
	
	
	public void setTitle(String title)
	{
		this.title.set(title);
	}
	
	public String getTitle()
	{
		return title.get();
	}
	
	
	public void setUrl(String url)
	{
		this.url.set(url);
	}
	
	public String getUrl()
	{
		return url.get();
	}
	
	public void setId(int id)
	{
		this.id.set(Integer.valueOf(id));
	}
	
	public int getId()
	{
		return this.id.get();
	}
	
	// Methods to make dealing with the Database easier
	
	public void save()
	{
		this.save(RssWidgetApplication.appContext, getId());
	}
	
	public static RssFeed getFeedById(int id)
	{
		return RssFeed.objects(RssWidgetApplication.appContext, RssFeed.class).get(id);
	}
	
	public static List<RssFeed> getAllFeeds()
	{
		QuerySet<RssFeed> set = RssFeed.objects(RssWidgetApplication.appContext, RssFeed.class).all();
		return set.toList();
	}
}
