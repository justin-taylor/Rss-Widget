package org.tayloredapps.RssWidget.Activities;

import org.tayloredapps.R;
import org.tayloredapps.RssWidget.Adapters.ArticlesAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ArticleListActivity extends Activity 
{
	ArticlesAdapter adapter;;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ListView lv = (ListView) findViewById(R.id.listView1);
        adapter  = new ArticlesAdapter(this, 1);
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
