import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;

public class WorldOfDarknessBOT {
    public static void main(final String[] args) {
        System.out.println(System.getenv("DISCORD_TOKEN"));
        final DiscordClient client = DiscordClient.create(System.getenv("DISCORD_TOKEN"));
        final GatewayDiscordClient gateway = client.login().block();

        gateway.on(MessageCreateEvent.class).subscribe(event -> {
            final Message message = event.getMessage();
            if ("!test".equals(message.getContent())) {
                final MessageChannel channel = message.getChannel().block();
                channel.createMessage("it works!").block();
            }
        });

        gateway.onDisconnect().block();
    }
}
