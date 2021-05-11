package Role;

public class DiscordRole {
    private String roleName;
    private int roleCost;
    private String roleColor;

    public DiscordRole(String roleName, int roleCost, String roleColor) {
        this.roleName = roleName;
        this.roleCost = roleCost;
        this.roleColor = roleColor;
    }

    public String getRoleName() {return roleName;}
    public int getRoleCost() {return roleCost;}
    public String getRoleColor() {return roleColor;}


}
