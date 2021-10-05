package se.msoto.civilizationdiscordintegration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "civilization-to-discord-username-mapping")
public class UserMapper {

    private static final String DEFAULT_USER_ID = "Unknown";

    private Map<String, String> users;

    public String toDiscordUserId(String civilizationUserId) {
        return users.getOrDefault(civilizationUserId, civilizationUserId);
    }

    public void setUsers(Map<String, String> users) {
        this.users = users;
    }
}
