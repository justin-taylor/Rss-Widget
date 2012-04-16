package org.tayloredapps.RssWidget.Activities;

import org.tayloredapps.R;
import org.tayloredapps.RssWidget.Adapters.ArticlesAdapter;
import org.tayloredapps.RssWidget.Models.RssArticle;
import org.tayloredapps.RssWidget.Models.RssFeed;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ArticleListActivity extends Activity 
{
	ArticlesAdapter adapter;
	public static String FEED_ID_EXTRA = "FEEDID";
	RssFeed feed;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
                
        Bundle extras = getIntent().getExtras();
        int feedId = extras.getInt(FEED_ID_EXTRA);
        feed = RssFeed.getFeedById(feedId);
        
        ListView lv = (ListView) findViewById(R.id.listView1);
        adapter  = new ArticlesAdapter(this, 1, feedId);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(lvListener);
    }
    
    private OnItemClickListener lvListener = new OnItemClickListener(){

		public void onItemClick(AdapterView<?> parent, View v, int position, long id)
		{
			RssArticle article = adapter.getItem(position);
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getLink()));
			browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			ArticleListActivity.this.startActivity(browserIntent);
		}
    };
}
