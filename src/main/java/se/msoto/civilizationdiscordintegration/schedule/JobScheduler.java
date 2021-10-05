package se.msoto.civilizationdiscordintegration.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import se.msoto.civilizationdiscordintegration.UserMapper;
import se.msoto.civilizationdiscordintegration.api.DiscordRequest;
import se.msoto.civilizationdiscordintegration.configuration.DiscordConfiguration;
import se.msoto.civilizationdiscordintegration.quote.Quote;
import se.msoto.civilizationdiscordintegration.quote.QuoteParser;
import se.msoto.civilizationdiscordintegration.state.State;

@Component
public class JobScheduler {

    private static final String EVERY_MORNING_AT_8 = "0 0 8 * * *";
    private static final String EVERY_MORNING_AT_10 = "0 0 10 * * *";

    private final State state;
    private final RestTemplate restTemplate;
    private final QuoteParser parser;
    private final DiscordConfiguration discordConfiguration;
    private final UserMapper userMapper;

    @Autowired
    public JobScheduler(State state,
                        RestTemplate restTemplate,
                        QuoteParser parser,
                        DiscordConfiguration discordConfiguration,
                        UserMapper userMapper) {
        this.state = state;
        this.restTemplate = restTemplate;
        this.parser = parser;
        this.discordConfiguration = discordConfiguration;
        this.userMapper = userMapper;
    }

    @Scheduled(cron = EVERY_MORNING_AT_8, zone = "Europe/Stockholm")
    public void morningMessage() {

        String content = String.format("Good morning! Last day we played %s turns. %s, it is still your turn!",
                state.getNumberOfEventsPastDay(),
                userMapper.toDiscordUserId(state.getNextUser()));

        DiscordRequest request = DiscordRequest.builder()
                .withUsername("Status update!")
                .withContent(content)
                .build();

        restTemplate.postForEntity(discordConfiguration.getUrl(), request, String.class);

    }

    @Scheduled(cron = EVERY_MORNING_AT_10, zone = "Europe/Stockholm")
    public void quote() {
        Quote quote = parser.getRandomQuote();
        DiscordRequest request = DiscordRequest.builder()
                .withUsername(quote.getAuthor())
                .withContent(quote.getQuote())
                .build();

        restTemplate.postForEntity(discordConfiguration.getUrl(), request, String.class);

    }

}
