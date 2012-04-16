package org.tayloredapps.RssWidget.Activities;

import org.tayloredapps.R;
import org.tayloredapps.RssWidget.Adapters.FeedAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class FeedReaderActivity extends Activity {
    
	private FeedAdapter adapter = new FeedAdapter(this, 1);
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ListView lv = (ListView) findViewById(R.id.listView1);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(lvListener);
    }
    
    private OnItemClickListener lvListener = new OnItemClickListener(){

		public void onItemClick(AdapterView<?> parent, View v, int position, long id)
		{
			//TODO get the article and pass to the next activity
		}
    };
    
    
}