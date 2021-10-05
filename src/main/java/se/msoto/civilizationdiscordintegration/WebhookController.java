package se.msoto.civilizationdiscordintegration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import se.msoto.civilizationdiscordintegration.api.CivilizationEvent;
import se.msoto.civilizationdiscordintegration.api.DiscordRequest;
import se.msoto.civilizationdiscordintegration.configuration.DiscordConfiguration;
import se.msoto.civilizationdiscordintegration.logging.RequestResponseLoggingFilter;
import se.msoto.civilizationdiscordintegration.quote.QuoteParser;
import se.msoto.civilizationdiscordintegration.state.Event;
import se.msoto.civilizationdiscordintegration.state.State;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.TimeZone;

@RestController
public class WebhookController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

    private final RestTemplate restTemplate;
    private final UserMapper userMapper;
    private final DiscordConfiguration discordConfiguration;
    private final State state;
    private final QuoteParser quoteParser;

    @Autowired
    public WebhookController(RestTemplate restTemplate, UserMapper userMapper, DiscordConfiguration discordConfiguration, State state, QuoteParser quoteParser) {
        this.restTemplate = restTemplate;
        this.userMapper = userMapper;
        this.discordConfiguration = discordConfiguration;
        this.state = state;
        this.quoteParser = quoteParser;
    }

    @PostMapping("/civ_next_turn_event")
    public void handleEvent(@RequestBody CivilizationEvent event) {
        if (isDuplicate(event, state.getLastEvent())) {
            return;
        }
        updateState(event);
        sendNotification(event);
    }

    private boolean isDuplicate(CivilizationEvent event, Event lastEvent) {
        return event.getGameName().equals(lastEvent.getGameName())
                && event.getNextUser().equals(lastEvent.getNextUser())
                && event.getCurrentTurn().equals(lastEvent.getTurn());
    }

    private void sendNotification(CivilizationEvent event) {
        String nextUser = userMapper.toDiscordUserId(event.getNextUser());
        String currentTurn = event.getCurrentTurn();
        String gameName = event.getGameName();

        DiscordRequest discordRequest = DiscordRequest.builder()
                .withContent(String.format("Game: [%s] Hey, %s! Det är din tur! (Runda %s)", gameName.toUpperCase(), nextUser, currentTurn))
                .withUsername(discordConfiguration.getBotName())
                .withAvatarUrl(discordConfiguration.getAvatarUrl())
                .build();

        restTemplate.postForEntity(discordConfiguration.getUrl(), discordRequest, String.class);
    }

    private void updateState(CivilizationEvent civilizationEvent) {
        Event event = Event.builder()
                .withTimestamp(LocalDateTime.now())
                .withGameName(civilizationEvent.getGameName())
                .withTurn(civilizationEvent.getCurrentTurn())
                .withNextUser(civilizationEvent.getNextUser())
                .build();
        state.addEvent(event);
    }

    @GetMapping(value = "/avatar", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getAvatar() throws IOException {
        BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("avatar/pacachuti.png")));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", bos);
        return bos.toByteArray();
    }

    @GetMapping(value = "/ping", produces = "application/json")
    public void ping() {
        LOGGER.info("PING");
    }

    @GetMapping(value = "/quote", produces = "application/json")
    public void quote() {
        LOGGER.info(quoteParser.getRandomQuote().getQuote());
        Arrays.stream(TimeZone.getAvailableIDs()).forEach(LOGGER::info);
    }


}
