package zaba.constituent;

/**
 * Created by ericz on 6/3/2017.
 */

public class Bill {

    private String name;
    private String description;
    private String question;
    private String vote;
    public Bill (String name, String description, String question, String vote)
    {
        this.name = name;
        this.description = description;
        this.question = question;
        this.vote = vote;

    }

    public String getName()
    {
        return this.name;
    }
    public String getDescription()
    {
        return this.description;
    }

    public String getQuestion()
    {
        return this.question;
    }
    public String getVote()
    {
        return this.vote;
    }



}
