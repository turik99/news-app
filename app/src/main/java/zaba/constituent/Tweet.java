package zaba.constituent;

import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * Created by ericz on 5/26/2017.
 */

public class Tweet implements Comparable<Tweet> {

    private String username;
    private String tweetText;
    private String profileImage;
    private String datePublished;
    public Tweet (String usernameArg, String tweetTextArg, String profileImageArg, String dateArg)
    {
        this.username = usernameArg;
        this.tweetText = tweetTextArg;
        this.profileImage = profileImageArg;
        this.datePublished = dateArg;

    }

    public String getTweetText()
    {
        return tweetText;
    }
    public String getUsername()
    {
        return username;
    }
    public String getProfileImage()
    {
        return profileImage;
    }
    public String getDatePublished()
    {
        return datePublished;
    }


    public int compareTo(Tweet two) {
        return getDatePublished().compareTo(two.getDatePublished());
    }
}
