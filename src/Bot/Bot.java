package Bot;

import Message.MessageListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.io.*;
import java.util.Scanner;

import Core.*;

import javax.security.auth.login.LoginException;

public class Bot {

    public static DiscordBot bot;

    public static void run() throws LoginException, InterruptedException {
        System.out.println("Starting GitHub Stat Tracker v1.0.1");

        boolean noData = false;
        boolean reset = false;

        Gson gson = new Gson();

        File fileName = new File("src/Secrets/DiscordBot.json");
        try (Reader reader = new FileReader(fileName)) {
            bot = gson.fromJson(reader, DiscordBot.class);
            if (bot.getBotID() == null)
                noData = true;
            else
                Config.bot = bot;
        }
        catch (IOException e) {
            noData = true;
        }

        if (noData)
            setupBot();
        else {
            while(true) {
                String resetBotMsg = """
                    Existing bot data already exists on this system.
                    Would you like to load the existing bot data?
                    Existing bot data will be wiped if 'n' is replied.
                    (y / n)
                    """;
                String resetBotInput = getUserInput(resetBotMsg);
                if (resetBotInput.equalsIgnoreCase("y"))
                    break;
                else if (resetBotInput.equalsIgnoreCase("n")) {
                    resetBot();
                    setupBot();
                    noData = true;
                    reset = true;
                    break;
                } else
                    System.out.println("ERROR: Invalid input. Try again!\n");
            }
        }
        startBot(noData, reset);
    }

    private static void resetBot() {
        File allChannelsFile = new File("src/Secrets/AllChannels.json");
        File botLogsFile = new File("src/Secrets/BotLogs.json");
        File botMessageFile = new File("src/Secrets/BotMessage.json");
        File discordBotFile = new File("src/Secrets/DiscordBot.json");
        File membersFile = new File("src/Secrets/Members.json");

        int filesDeleted = 0;
        if (allChannelsFile.exists() && allChannelsFile.delete())
            filesDeleted++;
        if (botLogsFile.exists() && botLogsFile.delete())
            filesDeleted++;
        if (botMessageFile.exists() && botMessageFile.delete())
            filesDeleted++;
        if (discordBotFile.exists() && discordBotFile.delete())
            filesDeleted++;
        if (membersFile.exists() && membersFile.delete())
            filesDeleted++;

        if (filesDeleted == 5) {
            System.out.println("SUCCESS: Bot data has been reset!");
        } else {
            System.out.println("ERROR: an error occurred while trying to reset stored data!");
        }
    }

    private static void setupBot() {                         // user input
        String tokenMsg = "\nInput bot token\n";
        String tokenInput = getUserInput(tokenMsg);

        String botID_Msg = "\nInput bot ID\n";
        String botID_Input = getUserInput(botID_Msg);

        String serverID_Msg = "\nInput server ID\n";
        String serverID_Input = getUserInput(serverID_Msg);

        String apiTokenMsg = "\nInput GitHub API Token\n";
        String apiTokenInput = getUserInput(apiTokenMsg);
        StringBuilder apiString = new StringBuilder();
        apiString.append("token ").append(apiTokenInput);

        bot = new DiscordBot(tokenInput, botID_Input, serverID_Input, apiString.toString());
        Config.bot = bot;

        serializeBotSimple();
    }


    private static void serializeBotSimple() {              // save bot info to json
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        File fileName = new File("src/Secrets/DiscordBot.json");
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(bot, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void startBot(boolean noData, boolean reset) throws LoginException, InterruptedException {
        String TOKEN = bot.getToken();

        Config.builder = JDABuilder.createDefault(TOKEN);
        Config.builder.setAutoReconnect(true);
        Config.builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
        Config.builder.setActivity(Activity.watching("GitHub Repos"));
        for (final GatewayIntent gatewayIntent : GatewayIntent.values()) {
            Config.builder.enableIntents(gatewayIntent, new GatewayIntent[0]);
        }
        Config.builder.addEventListeners(new MessageListener());
        Config.jda = Config.builder.build();
        Config.jda.awaitReady();

        System.out.println("Bot setup successful!");
        Config.guild = Config.jda.getGuildById(bot.getServerID());

        // Delete existing categories and channels in guild if user would like to reset
        if (reset) {
            for (Category category : Config.guild.getCategories()) {
                if (category.getName().equals("STAT TRACKER")) {
                    category.delete().queue();
                    Thread.sleep(1 * 1000L);
                }
            }

            for (TextChannel channel : Config.guild.getTextChannels()) {
                if (channel.getName().equals("members") || channel.getName().equals("leaderboard-shop") ||
                    channel.getName().equals("commits-commands") || channel.getName().equals("graphs")) {
                    channel.delete().queue();
                    Thread.sleep(1 * 1000L);
                }
            }

        }

        // Create / load json data
        if (noData) {
            Thread.sleep(3 * 1000L);
            SetupChannels.setupChannels();
            Thread.sleep(3 * 1000L);
            SetupMessages.setupMessages();

            Config.allChannels.serializeAllChannelsSimple();
            Config.members.serializeMembersSimple();
            Config.botLogs.serializeBotLogsSimple();
            Config.botMsg.serializeBotMessageSimple();
        } else {
            Config.allChannels.deserializeAllChannelsSimple();
            Config.members.deserializeMembersSimple();
            Config.botLogs.deserializeBotLogsSimple();
            Config.botMsg.deserializeBotMessageSimple();
        }
        Config.pLangs.deserializePLangsSimple();
    }

    private static String getUserInput(String msg) {
        System.out.print(msg);
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }
}
