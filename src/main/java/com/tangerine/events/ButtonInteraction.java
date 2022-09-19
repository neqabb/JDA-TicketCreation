package com.tangerine.events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import  net.dv8tion.jda.api.interactions.components.buttons.Button;
import java.awt.*;
import java.util.EnumSet;
import java.util.Objects;

public class ButtonInteraction extends ListenerAdapter {
    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        if (Objects.equals(event.getButton().getId(), "create")) {
            Category TicketingCategory = Objects.requireNonNull(event.getGuild()).getCategoryById("LongCategoryID");
            assert TicketingCategory != null;
            long member = Objects.requireNonNull(event.getMember()).getIdLong();
            EnumSet<Permission> allow = EnumSet.of(Permission.MESSAGE_SEND, Permission.VIEW_CHANNEL);
            EnumSet<Permission> deny = EnumSet.of(Permission.MESSAGE_MANAGE);
            TextChannel TicketingTextChannel = TicketingCategory.createTextChannel(Objects.requireNonNull(event.getMember()).getEffectiveName())
                    .addMemberPermissionOverride(member, allow, deny)
                    .complete();
            EmbedBuilder TextChannelCreatedEmbed = new EmbedBuilder()
                    .setColor(Color.decode("#2F3136"))
                    .setDescription("> " + TicketingTextChannel.getAsMention() + "was created");
            event.replyEmbeds(TextChannelCreatedEmbed.build()).setEphemeral(true).queue();
            EmbedBuilder CloseButtonEmbed = new EmbedBuilder()
                    .setColor(Color.decode("#2F3136"))
                    .setDescription("> if you're done with this ticket please **CLOSE** it!");
            Button Close = Button.danger("Close", "Closes the Ticket");
            TicketingTextChannel.sendMessageEmbeds(CloseButtonEmbed.build())
                    .setActionRow(Close)
                    .queue();
        }
        else if (Objects.equals(event.getButton().getId(), "Close")) {
            event.getChannel().delete().queue();
        }
    }
}
