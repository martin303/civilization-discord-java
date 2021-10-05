package se.msoto.civilizationdiscordintegration.api;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DiscordRequest {

    private String content;
    private String username;
    private String avatarUrl;

    private DiscordRequest() {
        // no-op
    }

    private DiscordRequest(Builder builder) {
        content = builder.content;
        username = builder.username;
        avatarUrl = builder.avatarUrl;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getContent() {
        return content;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public static final class Builder {
        private String content;
        private String username;
        private String avatarUrl;

        private Builder() {
        }

        public Builder withContent(String content) {
            this.content = content;
            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
            return this;
        }

        public DiscordRequest build() {
            return new DiscordRequest(this);
        }
    }
}
