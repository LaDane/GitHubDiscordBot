package Bot;


public class DiscordBot {
    private final String TOKEN;
    private final String BOTID;
    private final String SERVERID;
    private final String APITOKEN;

    public DiscordBot(String token, String botID, String serverID, String APITOKEN) {
        this.TOKEN = token;
        this.BOTID = botID;
        this.SERVERID = serverID;
        this.APITOKEN = APITOKEN;
    }

    public String getToken() {return TOKEN;}
    public String getBotID() {return BOTID;}
    public String getServerID() {return SERVERID;}
    public String getApiToken() {return APITOKEN;}
}
