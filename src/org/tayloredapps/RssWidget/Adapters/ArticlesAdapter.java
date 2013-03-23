package org.tayloredapps.RssWidget.Adapters;

import java.text.SimpleDateFormat;
import java.util.List;

import org.tayloredapps.R;
import org.tayloredapps.RssWidget.Models.RssArticle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ArticlesAdapter extends ArrayAdapter<RssArticle>
{
	List<RssArticle> articles;
	
	public ArticlesAdapter(Context context, int textViewResourceId, int feedId)
	{
		super(context, textViewResourceId);
		articles = RssArticle.getArticlesForFeed(feedId);
	}
	
	public int getCount()
	{
		return articles.size();
	}
	
	public RssArticle getItem(int position)
	{
		return articles.get(position);
	}

	public int getViewTypeCount()
	{
		return 1;
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		if (convertView == null)
		{	
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.article_row, parent, false);
		}

		RssArticle item = articles.get(position);
		
		TextView textView = (TextView) convertView.findViewById(R.id.title);
		textView.setText(item.getTitle());
		
		SimpleDateFormat dateFormat=new SimpleDateFormat("M/dd/20yy");
		String dateString = dateFormat.format(item.getDate());
		
		textView = (TextView) convertView.findViewById(R.id.date);
		textView.setText(dateString);
		
		textView = (TextView) convertView.findViewById(R.id.summary);
		textView.setText(item.getSummary());
		
		return convertView;
	}

}
