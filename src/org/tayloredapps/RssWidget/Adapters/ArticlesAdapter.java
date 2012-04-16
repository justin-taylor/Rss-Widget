package org.tayloredapps.RssWidget.Adapters;

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
	
	public ArticlesAdapter(Context context, int textViewResourceId)
	{
		super(context, textViewResourceId);
		articles = RssArticle.getAllArticles();
	}
	
	public int getCount()
	{
		return articles.size();
	}
	
	public RssArticle getItem(int position)
	{
		return articles.get(position);
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		if (convertView == null)
		{	
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.widget_article_row, parent, false);
		}

		RssArticle item = articles.get(position);
		
		TextView textView = (TextView) convertView.findViewById(R.id.title);
		textView.setText(item.getTitle());
		
		textView = (TextView) convertView.findViewById(R.id.summary);
		textView.setText(item.getSummary());
		
		return convertView;
	}

}
