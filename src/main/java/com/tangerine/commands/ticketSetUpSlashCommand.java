package com.tangerine.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.util.Objects;

public class ticketSetUpSlashCommand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String command = event.getName();
        String SubCommand = event.getInteraction().getSubcommandName();
        assert SubCommand != null;
        if (command.equals("ticket")) {
            Category MemberTicketingCategory = Objects.requireNonNull(event.getGuild()).getCategoryById("LongCategoryID");
            assert MemberTicketingCategory != null;
            if (SubCommand.equals("set-channel")) {
                Button create = Button.primary("create", "creates ticket");
                EmbedBuilder setupEmbed = new EmbedBuilder()
                        .setColor(Color.decode("#2F3136"))
                        .setDescription("> Create your ticket");
                Objects.requireNonNull(event.getJDA().getTextChannelById("LongChannelID")).sendMessageEmbeds(setupEmbed.build())
                        .setActionRow(create)
                        .queue();
                event.reply("Ticket was set").setEphemeral(true).queue();
            }
        }
    }
}
