package org.tayloredapps.RssWidget.Widget;

import java.util.List;

import org.tayloredapps.R;
import org.tayloredapps.R.id;
import org.tayloredapps.R.layout;
import org.tayloredapps.RssWidget.API.FeedAPI;
import org.tayloredapps.RssWidget.API.OnFetchArticlesListener;
import org.tayloredapps.RssWidget.Models.RssArticle;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

public class WidgetService extends RemoteViewsService
{

	@Override
	public RemoteViewsFactory onGetViewFactory(Intent intent)
	{
		return new RssRemoteViewsFactory(this.getApplicationContext(), intent);
	}
}



class RssRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory, OnFetchArticlesListener
{

    private Context mContext;
    private int mAppWidgetId;
    FeedAPI api;
    
    List<RssArticle> articles;
    
    Handler threadHandler = new Handler();
    
    boolean blockApiCall = false;
	
	public RssRemoteViewsFactory(Context context, Intent intent)
	{
		mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        articles = RssArticle.getAllArticles();
        
        api = new FeedAPI();
		api.addOnFetchArticlesListener(this);
	}

	public int getCount()
	{
		return articles.size();
	}

	
	public RemoteViews getViewAt(int position)
	{
		RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_article_row);
		
		RssArticle article = articles.get(position);
		
		rv.setTextViewText(R.id.title, article.getTitle());
		
		/*
		String summary;
		int maxLength = 100;
		if(article.getSummary().length() > maxLength)
		{
			summary = article.getSummary().substring(0, maxLength)+"...";
		}
		
		else
		{
			summary = article.getSummary();
		}
		
		rv.setTextViewText(R.id.summary, summary);
		*/
		
		if(article.getFeed() != null)
		{
			rv.setTextViewText(R.id.feed_name, article.getFeed().getTitle());
		}
				
		Bundle extras = new Bundle();
		extras.putString(WidgetProvider.OPEN_ARTICLE_URL_EXTRA, article.getLink());
		Intent intent = new Intent();
		intent.putExtras(extras);
		
		rv.setOnClickFillInIntent(R.id.row, intent);
		
		return rv;
	}

	public void onDataSetChanged()
	{
		if(!blockApiCall)
		{
			blockApiCall = true;
			
			Runnable fetchRunnable = new Runnable()
			{
				public void run()
				{
					api.fetchArticles();
				}
			};
			
			threadHandler.post(fetchRunnable);
		}
	}
	
	public void onCreate()
	{
		
	}
	
	public long getItemId(int position)
	{
		return 0;
	}

	public RemoteViews getLoadingView()
	{
		// returning null uses default loading view
		return null;
	}

	public int getViewTypeCount()
	{
		return 1;
	}

	public boolean hasStableIds()
	{
		return false;
	}

	public void onDestroy()
	{
		
	}

	
	/***********************************************
	 * 
	 * API Fetch Listener Methods
	 * 
	 ***********************************************/
	
	public void fetchArticlesDidSucceed()
	{
		//TODO
		articles = RssArticle.getAllArticles();
		//this.onDataSetChanged();
		blockApiCall = false;
		Runnable notifyRunnable = new Runnable()
		{
			public void run()
			{
				AppWidgetManager.getInstance(mContext).notifyAll();
			}
		};
		//threadHandler.post(notifyRunnable);
	}

}
