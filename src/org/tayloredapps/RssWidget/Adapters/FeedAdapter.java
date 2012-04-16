package org.tayloredapps.RssWidget.Adapters;

import java.util.List;

import org.tayloredapps.R;
import org.tayloredapps.RssWidget.Models.RssFeed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FeedAdapter extends ArrayAdapter<RssFeed>
{
	List<RssFeed> feeds;
	
	public FeedAdapter(Context context, int textViewResourceId)
	{
		super(context, textViewResourceId);
		feeds = RssFeed.getAllFeeds();
	}

	public int getCount()
	{
		return feeds.size();
	}
	
	public RssFeed getItem(int position)
	{
		return feeds.get(position);
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		if (convertView == null)
		{	
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.row, parent, false);
		}
		
		TextView textView = (TextView) convertView.findViewById(R.id.label);
		RssFeed feed = feeds.get(position);
		textView.setText(feed.getTitle());
		return convertView;
	}
}
