package se.msoto.civilizationdiscordintegration.quote;

public class Quote {

    private final String quote;
    private final String author;

    private Quote(Builder builder) {
        quote = builder.quote;
        author = builder.author;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getQuote() {
        return quote;
    }

    public String getAuthor() {
        return author;
    }

    public static final class Builder {
        private String quote;
        private String author;

        private Builder() {
        }

        public Builder withQuote(String quote) {
            this.quote = quote;
            return this;
        }

        public Builder withAuthor(String author) {
            this.author = author;
            return this;
        }

        public Quote build() {
            return new Quote(this);
        }
    }
}
