package Bot;

import Message.MessageListener;
import Web.App;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.io.*;

import Core.*;
import BotChannel.*;

import javax.security.auth.login.LoginException;

public class Bot {

    public static DiscordBot bot;

    public static void run() throws LoginException, InterruptedException {
        Gson gson = new Gson();

        boolean noData = false;
        File fileName = new File("src/Secrets/DiscordBot.json");
        try (Reader reader = new FileReader(fileName)) {
            bot = gson.fromJson(reader, DiscordBot.class);
            if (bot.getBotID() == null)
                noData = true;
        }
        catch (IOException e) {
//            e.printStackTrace();
            noData = true;
        }

        if (noData)
            setupBot();
        startBot(noData);
    }

    private static void createJson() {

    }

    private static void setupBot() {                         // user input
        String tokenMsg = "\nInput bot token\n";
        String tokenInput = UI.UserInput.getUserInput(tokenMsg);

        String botID_Msg = "\nInput bot ID\n";
        String botID_Input = UI.UserInput.getUserInput(botID_Msg);

        String serverID_Msg = "\nInput server ID\n";
        String serverID_Input = UI.UserInput.getUserInput(serverID_Msg);

        String apiTokenMsg = "\nInput GitHub API Token\n";
        String apiTokenInput = UI.UserInput.getUserInput(apiTokenMsg);

        bot = new DiscordBot(tokenInput, botID_Input, serverID_Input);
        Config.app = new App(apiTokenInput);

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


    private static void startBot(boolean noData) throws LoginException, InterruptedException {
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

        if (noData) {
            Thread.sleep(3 * 1000L);
            SetupChannels.setupChannels();
            Thread.sleep(3 * 1000L);
            SetupMessages.setupMessages();

            Config.app.serializeAppSimple();
            Config.allChannels.serializeAllChannelsSimple();
            Config.members.serializeMembersSimple();
            Config.botLogs.serializeBotLogsSimple();
            Config.botMsg.serializeBotMessageSimple();
        } else {
            Config.app.deserializeAppSimple();
            Config.allChannels.deserializeAllChannelsSimple();
            Config.members.deserializeMembersSimple();
            Config.botLogs.deserializeBotLogsSimple();
            Config.pLangs.deserializePLangsSimple();
            Config.botMsg.deserializeBotMessageSimple();
        }
    }
}
