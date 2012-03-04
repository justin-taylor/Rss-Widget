package Models;


import java.util.Date;
import java.util.List;

import org.tayloredapps.RssWidgetApplication;

import com.orm.androrm.CharField;
import com.orm.androrm.DateField;
import com.orm.androrm.Filter;
import com.orm.androrm.ForeignKeyField;
import com.orm.androrm.IntegerField;
import com.orm.androrm.Model;
import com.orm.androrm.QuerySet;

public class RssArticle extends Model
{
	protected CharField title;
	protected CharField summary;
	protected CharField link;
	protected DateField date;
	protected IntegerField id;
	protected ForeignKeyField<RssFeed> feed;
	
	public RssArticle()
	{
		super(true);
		
		title = new CharField();
		summary = new CharField();
		link = new CharField();
		date = new DateField();
		id = new IntegerField();
		feed = new ForeignKeyField<RssFeed>(RssFeed.class);
	}
	
	public void setTitle(String title)
	{
		this.title.set(title);
	}
	
	public String getTitle()
	{
		return title.get();
	}
	
	public void setSummary(String summary)
	{
		this.summary.set(summary);
	}
	
	public String getSummary()
	{
		return summary.get();
	}
	
	public void setLink(String link)
	{
		this.link.set(link);
	}
	
	public String getLink()
	{
		return link.get();
	}
	
	/**
	 * Takes a date string in the format of
	 * YYY-MM-DD HH:MM:SS
	 * and sets the date
	 * 
	 * @param String
	 */
	public void setDate(String date)
	{
		String split[] = date.split(" ");
		String dateSplit[] = split[0].split("-");
		String time[] = split[1].split(":");
		
		Date newDate = new Date();
		newDate.setYear(Integer.parseInt( dateSplit[0] ));
		newDate.setMonth(Integer.parseInt( dateSplit[1] ));
		newDate.setDate( Integer.parseInt( dateSplit[2] ));
		newDate.setHours(Integer.parseInt( time[0] ));
		newDate.setMinutes(Integer.parseInt( time[1] ));
		newDate.setSeconds(Integer.parseInt( time[2] ));
		
		setDate( newDate );
	}
	
	public void setDate(Date date)
	{
		this.date.set(date);
	}
	
	public Date getDate()
	{
		return date.get();
	}
	
	public void setId(int id)
	{
		this.id.set(Integer.valueOf(id));
	}
	
	public int getId()
	{
		return id.get();
	}
	
	public void setFeed(RssFeed feed)
	{
		this.feed.set(feed);
	}
	
	public RssFeed getFeed()
	{
		return feed.get();	
	}
	
	// Methods to make dealing with the Database easier
	public void save()
	{
		this.save(RssWidgetApplication.appContext, getId());
	}
	
	public static RssArticle getArticleById(int id)
	{
		return RssArticle.objects(RssWidgetApplication.appContext, RssArticle.class).get(id);
	}
	
	public static List<RssArticle> getAllArticles()
	{		
		QuerySet<RssArticle> set = RssArticle.objects(RssWidgetApplication.appContext, RssArticle.class).all().orderBy("-date");
		return set.toList();
	}
}
