package se.msoto.civilizationdiscordintegration.state;

import java.time.LocalDateTime;

public class Event {

    private final LocalDateTime timestamp;
    private final String gameName;
    private final String nextUser;
    private final String turn;

    private Event(Builder builder) {
        timestamp = builder.timestamp;
        gameName = builder.gameName;
        nextUser = builder.username;
        turn = builder.turn;
    }

    public static Builder builder() {
        return new Builder();
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getGameName() {
        return gameName;
    }

    public String getNextUser() {
        return nextUser;
    }

    public String getTurn() {
        return turn;
    }

    public static final class Builder {
        private LocalDateTime timestamp;
        private String gameName;
        private String username;
        private String turn;

        private Builder() {
        }

        public Builder withTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder withGameName(String gameName) {
            this.gameName = gameName;
            return this;
        }

        public Builder withNextUser(String nextUser) {
            this.username = nextUser;
            return this;
        }

        public Builder withTurn(String turn) {
            this.turn = turn;
            return this;
        }

        public Event build() {
            return new Event(this);
        }
    }
}
