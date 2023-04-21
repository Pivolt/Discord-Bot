package pivolt.bot;

import com.google.api.services.sheets.v4.Sheets;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class DiscordBot extends ListenerAdapter {
    public static final String prefix = "!";
    public static Sheets service;

    public static void main(String[] args) throws GeneralSecurityException, IOException {

        service = SheetsQuickstart.getService();

        JDABuilder.createDefault("token")
                .addEventListeners(new DiscordBot())
                .build();
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        String[] wiadomosc = event.getMessage().getContentRaw().split(" ");

        switch (wiadomosc[0]) {
            case prefix + "ping":
                String author = event.getAuthor().getAsMention();
                event.getChannel().sendMessage(author).queue();
                break;
            case prefix + "link":
                event.getChannel().sendMessage("https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit").queue();
                break;
            case prefix + "datarow":
                try {
                    for (int i = 1; i < wiadomosc.length; i++)
                        event.getChannel().sendMessage(SheetsQuickstart.getRow(service, wiadomosc[i])).queue();
                } catch (GeneralSecurityException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case prefix + "datacol":
                try {
                    for (int i = 1; i < wiadomosc.length; i++)
                        event.getChannel().sendMessage(SheetsQuickstart.getCol(service, wiadomosc[i])).queue();
                } catch (GeneralSecurityException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case prefix + "datacell":
                try {
                    for (int i = 1; i < wiadomosc.length; i++)
                        event.getChannel().sendMessage(SheetsQuickstart.getCell(service, wiadomosc[i])).queue();
                } catch (GeneralSecurityException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case prefix + "findrow":
                try {
                    for (int i = 1; i < wiadomosc.length; i++)
                        event.getChannel().sendMessage(SheetsQuickstart.findRow(service, wiadomosc[i])).queue();
                } catch (GeneralSecurityException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                return;
        }
    }
}
