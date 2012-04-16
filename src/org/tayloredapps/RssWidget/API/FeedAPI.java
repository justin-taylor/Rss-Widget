package org.tayloredapps.RssWidget.API;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.tayloredapps.RssWidget.RssWidgetApplication;
import org.tayloredapps.RssWidget.Models.RssArticle;
import org.tayloredapps.RssWidget.Models.RssFeed;
import org.tayloredapps.RssWidget.Utils.HttpTool;

import com.orm.androrm.Filter;

import android.os.AsyncTask;
import android.os.Looper;
import android.util.Log;

public class FeedAPI
{

	// SERVER METHODS AND URLS
	private static final String SERVER = "http://107.20.88.55";
	private static final String GET_ARTICLES = SERVER+"/get/articles";
	private static final String PUT_FEED 	= SERVER+"/put/feed";
	
	//JSON TAGS
	private static final String ARTICLES_ARRAY_TAG 	= "articles";
	private static final String ARTICLE_DATE_TAG 	= "date";
	private static final String ARTICLE_ID_TAG 		= "pk";
	private static final String ARTICLE_SUMMARY_TAG = "summary";
	private static final String ARTICLE_LINK_TAG 	= "link";
	private static final String ARTICLE_TITLE_TAG 	= "title";
	private static final String ARTICLE_FEED_TAG 	= "feed";
	
	private static final String FEEDS_ARRAY_TAG = "feeds";
	private static final String FEED_ID_TAG 	= "pk";
	private static final String FEED_TITLE_TAG 	= "title";
	
	
	
	//Listeners
	private ArrayList<OnFetchArticlesListener> articleListeners;
	
	
	
	/***********************************************************************************
	 * 
	 * 		Fetch Article Methods
	 * 
	 ***********************************************************************************/
	
	public void addOnFetchArticlesListener(OnFetchArticlesListener listener)
	{
		if(articleListeners == null)
		{
			articleListeners = new ArrayList<OnFetchArticlesListener>(2);
		}
		
		if(!articleListeners.contains(listener))
			articleListeners.add(listener);
	}
	
	public void removeOnFetchArticlesListener(OnFetchArticlesListener listener)
	{
		if(articleListeners != null && articleListeners.contains(listener))
		{
			articleListeners.remove(listener);
		}
	}
	
	private class FetchArticlesTask extends AsyncTask<String, Integer, String>
	{

		@Override
		protected String doInBackground(String... params)
		{	
			String retVal = null;
			
			try
			{
				String body = HttpTool.request(GET_ARTICLES);
				Log.e("GOT ARTICLES","boyd: "+body);
				JSONObject root = new JSONObject(body);
				
				//parse the feeds first
				JSONArray items = root.getJSONArray(FEEDS_ARRAY_TAG);
				List<Integer> feedIds = new ArrayList<Integer>( items.length() );
				for(int i = 0; i < items.length(); i++)
				{					
					JSONObject item = items.getJSONObject(i);
					
					int id = -1;
					if(item.has(FEED_ID_TAG))
						id = item.getInt(FEED_ID_TAG);
					
					RssFeed feed = RssFeed.getFeedById(id);
					
					if(feed == null)
					{
						feed = new RssFeed();
						feed.setId(id);
					}
					
					if(item.has(FEED_TITLE_TAG))
					{
						feed.setTitle(item.getString(FEED_TITLE_TAG));
					}
					
					feed.save();
					feedIds.add(id);
				}
				
				// parse the articles next
				items = root.getJSONArray(ARTICLES_ARRAY_TAG);
				List<Integer> articleIds = new ArrayList<Integer>( items.length() );
				for(int i = 0; i < items.length(); i++)
				{
					
					JSONObject item = items.getJSONObject(i);
					int id = -1;
					if(item.has(ARTICLE_ID_TAG))
						id = Integer.parseInt( item.getString(ARTICLE_ID_TAG) );
					
					RssArticle article = RssArticle.getArticleById(id);
					
					if(article == null)
					{
						article = new RssArticle();
						article.setId(id);
					}
					
					if(item.has(ARTICLE_DATE_TAG))
					{
						article.setDate( item.getString(ARTICLE_DATE_TAG) );
					}
					
					if(item.has(ARTICLE_FEED_TAG))
					{
						RssFeed feed = RssFeed.getFeedById( item.getInt(ARTICLE_FEED_TAG) );
						if(feed != null)
							article.setFeed(feed);
					}
					
					if(item.has(ARTICLE_LINK_TAG))
					{
						article.setLink( item.getString(ARTICLE_LINK_TAG) );
					}
					
					if(item.has(ARTICLE_SUMMARY_TAG))
					{
						article.setSummary( item.getString(ARTICLE_SUMMARY_TAG) );
					}
					
					if(item.has(ARTICLE_TITLE_TAG))
					{
						article.setTitle( item.getString(ARTICLE_TITLE_TAG) );
					}
					
					article.save();
					articleIds.add(id);
				}
				
				
				//delete stale feeds and articles
				List<RssFeed> feeds = RssFeed.getAllFeeds();
				for(RssFeed feed : feeds)
				{
					if( !feedIds.contains( feed.getId() ))
					{
						feed.delete(RssWidgetApplication.appContext);
					}
				}
				
				List<RssArticle> articles = RssArticle.getAllArticles();
				for(RssArticle article : articles)
				{
					if( !articleIds.contains(article.getId()) )
					{
						article.delete(RssWidgetApplication.appContext);
					}
				}
			}
			
			catch (Exception e)
			{
				Log.e("FetchArticlesTask", ""+e.getLocalizedMessage());
				retVal = e.getLocalizedMessage();
			}
			
			
			
			return retVal;
		}
		
		@Override
		protected void onPostExecute(String result)
		{
			fetchArticlesDidSucceed();
		}
		
	}
	
	public void fetchArticles()
	{
		new FetchArticlesTask().execute("");
	}
	
	private void fetchArticlesDidSucceed()
	{
		if(articleListeners != null)
		{
			for(OnFetchArticlesListener listener : articleListeners)
			{
				listener.fetchArticlesDidSucceed();
			}
		}
	}
}
