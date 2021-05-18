package Message;

import Core.Config;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.requests.ErrorResponse;


public class EditMessage {
    public static void editMessage(String messageShopID,String channelID) {
        TextChannel channel = Config.guild.getTextChannelById(channelID);
        channel.retrieveMessageById(messageShopID).queue((message) -> {
            message.addReaction("U+1F7EB").queue();
            message.addReaction("U+1F7E8").queue();
            message.addReaction("U+1F7E7").queue();
            message.addReaction("U+1F7E6").queue();
            message.addReaction("U+1F7E9").queue();
            message.addReaction("U+1F7E5").queue();
            message.addReaction("U+1F7EA").queue();
            message.addReaction("U+2B1C").queue();
            message.addReaction("U+2B1B").queue();
        }, (failure) -> {
            if (failure instanceof ErrorResponseException) {
                ErrorResponseException ex = (ErrorResponseException) failure;
                if (ex.getErrorResponse() == ErrorResponse.UNKNOWN_MESSAGE) {
                    channel.sendMessage("That message doesn't exist !").queue();
                }
            }
            failure.printStackTrace();
        });
    }
}
