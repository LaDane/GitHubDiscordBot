package Commands;

import Core.Config;
import Member.Member;
import Message.EditMessage;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class CmdShop {
    public static void cmdShop(String memberID, String channelID) {
        boolean hasAccount = false;
        for (Member d : Config.members.getMembers()) {
            if (d.getMemberDiscordID().equals(memberID)) {
                hasAccount = true;
            }
        }


        if (hasAccount) {
            EmbedBuilder embed = new EmbedBuilder()
                    .setColor(Color.decode("#0099ff"))
                    .setTitle("Welcome to the shop")
                    .addField("You can use the react buttons to buy a theme color", "\u200B", false)
                    .addField(":blue_square:", "50 Points (Noob)", true)
                    .addField(":red_square:", "100 Points (Advanced)", true)
                    .addField(":green_square:", "1000 Points (Pro)", true)
                    .addField(":orange_square:", "100000 Points (Nerd get of the CHAIR!)", true);
            //Config.guild.getTextChannelById(channelID).sendMessage(embed.build()).queue();
            Config.guild.getTextChannelById(channelID).sendMessage(embed.build()).queue((message) -> {
                long messageId = message.getIdLong();
                EditMessage.editMessage(messageId);
                // use messageId here
            });
            embed.clear();
            //EditMessage.editMessage();
        } else {
            Cmd.sendErrorEmbed("User not found", null, channelID);
        }
    }
}
