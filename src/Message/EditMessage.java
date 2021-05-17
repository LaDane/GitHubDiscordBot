package Message;

import Core.Config;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.requests.ErrorResponse;

import java.nio.channels.Channel;

public class EditMessage {

    private static long testMessageID;
    private static String textMessageShopID = "842757052060401664";
    private static String botTestChannelID = "842672561288249344";

    public static void testMessage() {
        Config.guild.getTextChannelById(botTestChannelID).sendMessage("Edit this message").queue((message -> {
            testMessageID = message.getIdLong();
        }));
    }

    public static void editMessage(String messageShopID,String channelID) {
//        System.out.println("THIS IS RUNNING!");
        TextChannel channel = Config.guild.getTextChannelById(channelID);
        channel.retrieveMessageById(messageShopID).queue((message) -> {
            // use the message here, its an async callback
            message.addReaction("U+1F7EB").queue();
            message.addReaction("U+1F7E8").queue();
            message.addReaction("U+1F7E7").queue();
            message.addReaction("U+1F7E6").queue();
            message.addReaction("U+1F7E9").queue();
            message.addReaction("U+1F7E5").queue();
            message.addReaction("U+1F7EA").queue();
            message.addReaction("U+2B1C").queue();
            message.addReaction("U+2B1B").queue();
           // System.out.println("Message Content: " + message.getContentDisplay());
        }, (failure) -> {
            // if the retrieve request failed this will be called (also async)
            if (failure instanceof ErrorResponseException) {
                ErrorResponseException ex = (ErrorResponseException) failure;
                if (ex.getErrorResponse() == ErrorResponse.UNKNOWN_MESSAGE) {
                    // this means the message doesn't exist
                    channel.sendMessage("That message doesn't exist !").queue();
                }
            }
            failure.printStackTrace();
        });
    }
}
