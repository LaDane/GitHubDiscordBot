package BotChannel;

public class BotChannel {
    private String CHANNELID;
    private String CHANNELNAME;

    public BotChannel(String channelID, String channelName) {
        this.CHANNELID = channelID;
        this.CHANNELNAME = channelName;
    }

    public String getChannelID() {return CHANNELID;}
    public String getChannelName() {return CHANNELNAME;}
    public void setChannelName(String newName) {this.CHANNELNAME = newName;}
}
