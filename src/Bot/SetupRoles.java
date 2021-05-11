package Bot;

import Core.Config;
import Role.DiscordRole;
import net.dv8tion.jda.api.entities.Role;

import java.awt.*;
import java.util.ArrayList;

public class SetupRoles {

//    private static DiscordRole role1 = new DiscordRole("Casual", 100, "#00ff00");
//    private static DiscordRole role2 = new DiscordRole("Advanced", 500, "#00ff00");

    private static DiscordRole[] discordRoles = {
        new DiscordRole("Casual", 100, "#00ff00"),
        new DiscordRole("Advanced", 500, "#00ff00")
    };


    public static void setupRoles() {
        for (DiscordRole dr : discordRoles) {
            createNewRole(dr);
        }
    }

    public static void createNewRole(DiscordRole discordRole) {
        Config.guild.createRole().
                setName(discordRole.getRoleName()).
                setColor(Color.decode(discordRole.getRoleColor())).
                queue();

        Config.discordRoles.addToDiscordRoles(discordRole);
        Config.discordRoles.serializeDiscordRolesSimple();
    }
}
