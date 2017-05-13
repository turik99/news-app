package zaba.constituent;

/**
 * Created by ericz on 5/12/2017.
 */

public class NewsArticle implements Comparable<NewsArticle>
{
    private String title;
    private String url;
    private String imageURL;
    private String description;
    private String datePublished;
    private String source;
    private String upvotes;
    public NewsArticle()
    {

    }

    public void setTitle(String titleArg)
    {
        this.title = titleArg;
    }

    public void setUrl(String urlArg)
    {
        this.url = urlArg;
    }

    public void setImageURL(String imageUrlArg)
    {
        this.imageURL = imageUrlArg;
    }

    public void setDescription(String descriptionArg)
    {
        this.description = descriptionArg;
    }
    public void setDatePublished(String datePublishedArg)
    {
        this.datePublished = datePublishedArg;
    }
    public void setSource(String sourceArg)
    {
        this.source = sourceArg;
    }


    public String getSource()
    {
        return source;
    }
    public String getTitle()
    {
        return title;
    }

    public String getUrl()
    {
        return url;
    }
    public String getImageURL()
    {
        return imageURL;
    }
    public String getDescription()
    {
        return description;
    }
    public String getDatePublished()
    {
        return datePublished;
    }

    public void setUpvotes(String upvotesArg)
    {
        this.upvotes = upvotesArg;
    }

    public String getUpvotes()
    {
        return this.upvotes;
    }

    @Override
    public int compareTo(NewsArticle two) {
        return getDatePublished().compareTo(two.getDatePublished());
    }




//2017-04-15T12:47:00Z
}