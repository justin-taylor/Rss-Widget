package org.tayloredapps.RssWidget.Widget;

import org.tayloredapps.R;
import org.tayloredapps.R.id;
import org.tayloredapps.R.layout;
import org.tayloredapps.RssWidget.Activities.FeedReaderActivity;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

public class WidgetProvider extends AppWidgetProvider
{
	public static final String OPEN_ARTICLE_ACTION = "org.tayloredapps.FeedReader.OPEN_ARTICLE_ACTION";
	public static final String REFRESH_ARTICLES_ACTION = "org.tayloredapps.FeedReader.REFRESH_ARTICLES_ACTION";
	public static final String OPEN_ARTICLE_URL_EXTRA = "org.tayloredapps.FeedReader.OPEN_ARTICLE_URL_EXTRA";
	
	
	public void onReceive(Context context, Intent intent)
	{
		if(intent.getAction() != null)
		{
			if(intent.getAction().equals(OPEN_ARTICLE_ACTION))
			{
				String url = intent.getStringExtra(OPEN_ARTICLE_URL_EXTRA);
				if(url != null)
				{
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
					browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(browserIntent);
				}
			}
			
			else if(intent.getAction().equals(REFRESH_ARTICLES_ACTION))
			{
				int widgetId = intent.getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID);
				AppWidgetManager.getInstance(context).notifyAppWidgetViewDataChanged(widgetId, R.id.article_list);
			}
		}
		
		super.onReceive(context, intent);
	}
	
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
	{
		// Loop through all widget instances
		for (int i=0; i<appWidgetIds.length; i++)
		{
			//Create intent to launch config activity
			Intent intent = new Intent(context, FeedReaderActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
			
			// Get the layout of the widget and add onclick listener for the buttons
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
			views.setOnClickPendingIntent(R.id.config_button, pendingIntent);
			
			//TODO add onclick for the refresh button
			intent = new Intent(context, WidgetProvider.class);
			intent.setAction(REFRESH_ARTICLES_ACTION);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
			pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
			views.setOnClickPendingIntent(R.id.refresh, pendingIntent);
			
			// Setup the listview and rows for the articles
			intent = new Intent(context, WidgetService.class);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
			intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
			
			views.setRemoteAdapter(appWidgetIds[i], R.id.article_list, intent);
			views.setEmptyView(R.id.article_list, R.id.empty_view);
			
			//Setup onItemClickListener intent
			intent = new Intent(context, WidgetProvider.class);
			intent.setAction(OPEN_ARTICLE_ACTION);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
			intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
			
			pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			views.setPendingIntentTemplate(R.id.article_list, pendingIntent);
			
			appWidgetManager.updateAppWidget(appWidgetIds[i], views);
		}
		
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}
}
