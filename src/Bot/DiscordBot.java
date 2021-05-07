package Bot;

public class DiscordBot {
    private String TOKEN;
    private String BOTID;
    private String SERVERID;

    public DiscordBot(String token, String botID, String serverID) {
        this.TOKEN = token;
        this.BOTID = botID;
        this.SERVERID = serverID;
    }

    public String getToken() {return TOKEN;}
    public String getBotID() {return BOTID;}
    public String getServerID() {return SERVERID;}
}
