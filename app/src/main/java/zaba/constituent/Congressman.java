package zaba.constituent;

/**
 * Created by ericz on 5/31/2017.
 */

public class Congressman {

    private String name;
    private String party;
    private String state;
    public Congressman(String name, String party, String state)
    {
        this.name = name;
        this.party = party;
        this.state = state;
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



}
