package zaba.constituent;

/**
 * Created by ericz on 5/31/2017.
 */

public class Congressman {

    private String name;
    private String party;
    private String state;
    private String twitter;
    private String facebook;
    private String apiThing;
    private String profileImage;
    public Congressman(String name, String party,
                       String state,
                       String twitter,
                       String profileImage,
                       String facebook,
                       String apiThing)
    {
        this.name = name;
        this.party = party;
        this.state = state;
        this.twitter = twitter;
        this.facebook = facebook;
        this.apiThing = apiThing;
        this.profileImage = profileImage;
    }

    public String getName()
    {
        return name;
    }
    public String getParty()
    {
        return party;
    }
    public String getState()
    {
        return state;
    }
    public String getTwitter()
    {
        return this.twitter;
    }
    public String getFacebook()
    {
        return this.facebook;
    }
    public String getProfileImage()
    {
        return this.profileImage;
    }
    public String getApiThing()
    {
        return this.apiThing;
    }
    public void setProfileImage(String profileURL)
    {
        this.profileImage = profileURL;
    }




}
