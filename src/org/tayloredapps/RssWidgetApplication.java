package org.tayloredapps;

import java.util.ArrayList;
import java.util.List;

import com.orm.androrm.DatabaseAdapter;
import com.orm.androrm.Model;

import Models.*;

import android.app.Application;

public class RssWidgetApplication extends Application
{
	public static RssWidgetApplication appContext;
	public static final String DB_NAME = "rss_widget_db";
	
	public void onCreate()
	{
		super.onCreate();
		
		appContext = this;
		initDB();
	
	}
	
	protected void initDB()
	{
		List<Class<? extends Model>> models = new ArrayList<Class<? extends Model>>(2);
		models.add(RssFeed.class);
		models.add(RssArticle.class);
		
		DatabaseAdapter.setDatabaseName(DB_NAME);
		DatabaseAdapter adapter = new DatabaseAdapter( this );
		adapter.setModels(models);
	}
	
	public static void dumpDb()
	{
		List<RssArticle> articles = RssArticle.getAllArticles();
		for(RssArticle article : articles)
			article.delete(appContext);
		
		List<RssFeed> feeds = RssFeed.getAllFeeds();
		for(RssFeed feed : feeds)
			feed.delete(appContext);
	}
}
