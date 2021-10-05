package se.msoto.civilizationdiscordintegration.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "discord")
public class DiscordConfiguration {

    private static final String WEBHOOK_ID_PLACEHOLDER = "{webhook.id}";
    private static final String WEBHOOK_TOKEN_PLACEHOLDER = "{webhook.token}";

    private String webhookUrlTemplate;
    private String webhookId;
    private String webhookToken;
    private String avatarUrl;
    private String botName;

    public String getUrl() {
        return webhookUrlTemplate
                .replace(WEBHOOK_ID_PLACEHOLDER, webhookId)
                .replace(WEBHOOK_TOKEN_PLACEHOLDER, webhookToken);
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getBotName() {
        return botName;
    }

    public void setWebhookUrlTemplate(String webhookUrlTemplate) {
        this.webhookUrlTemplate = webhookUrlTemplate;
    }

    public void setWebhookId(String webhookId) {
        this.webhookId = webhookId;
    }

    public void setWebhookToken(String webhookToken) {
        this.webhookToken = webhookToken;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }
}
