package org.tayloredapps.RssWidget.Activities;

import org.tayloredapps.R;
import org.tayloredapps.RssWidget.Adapters.FeedAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class FeedReaderActivity extends Activity {
    
	private FeedAdapter adapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ListView lv = (ListView) findViewById(R.id.listView1);
        adapter = new FeedAdapter(this, 1);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(lvListener);
    }
    
    private OnItemClickListener lvListener = new OnItemClickListener(){

		public void onItemClick(AdapterView<?> parent, View v, int position, long id)
		{
			int itemId = adapter.getItem(position).getId();
			Intent intent = new Intent(FeedReaderActivity.this, ArticleListActivity.class);
			intent.putExtra(ArticleListActivity.FEED_ID_EXTRA, itemId);
			startActivity(intent);
		}
    };
    
    
}