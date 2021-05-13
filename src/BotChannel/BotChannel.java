package BotChannel;

public class BotChannel {
    private String channelID;
    private String channelName;

    public BotChannel(String channelID, String channelName) {
        this.channelID = channelID;
        this.channelName = channelName;
    }

    public String getChannelID() {return channelID;}
    public String getChannelName() {return channelName;}
    public void setChannelName(String newName) {this.channelName = newName;}
}
