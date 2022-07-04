package com.mycompany.pingbot;

import java.util.logging.*;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.*;
import net.dv8tion.jda.api.events.guild.member.GenericGuildMemberEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
/**
 *
 * @author Noah
 */
public class PingBot{

    private static JDA jda;
    private static final Logger LOG = Logger.getLogger(PingBot.class.getName());
    private static User user1 = User.fromId(""), user2 = User.fromId("");
    private static final String TOKEN = "", GUILD = "";
    private static Guild guild;

    public static void main(String[] args){
        build();
    }

    private static void build(){
        try{
            setUpJDA();
            jda.awaitReady();
            setUpUsers();
        }catch(InterruptedException ex){
            Logger.getLogger(PingBot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void setUpUsers(){
        guild = jda.getGuildById(GUILD);
        user1 = guild.getMember(user1).getUser();
        user2 = guild.getMember(user2).getUser();
    }

    private static void setUpJDA(){
        try{
            jda = JDABuilder.create(TOKEN, GatewayIntent.fromEvents(ReadyEvent.class, GuildVoiceJoinEvent.class, MessageReceivedEvent.class, GenericGuildMemberEvent.class,
            SlashCommandEvent.class))
            .addEventListeners(
                    (EventListener) (GenericEvent ge) ->
                    handleEvents(ge)).setMemberCachePolicy(MemberCachePolicy.ALL).setActivity(Activity
            .playing("Ping Pong")).build();
        }catch(LoginException ex){
            Logger.getLogger(PingBot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void handleEvents(GenericEvent event){
        LOG.log(Level.INFO, "Triggered Event: {0}", event.getClass().getName());
        if(event instanceof ReadyEvent){
            LOG.info("Ready!");
        }else if(event instanceof GuildVoiceJoinEvent joinedEvent && joinedEvent.getGuild().equals(guild)){
            if(joinedEvent.getMember().getUser().equals(user1)){
                sendDM(user2);
            }else if(joinedEvent.getMember().getUser().equals(user2)){
                sendDM(user1);
            }
        }else if(event instanceof MessageReceivedEvent msgEvent){
            if(msgEvent.getMessage().getContentRaw().equals("!shutdown")){
                LOG.log(Level.INFO, "Bot shutting down.");
                jda.shutdown();
                System.exit(0);
            }
        }

    }

    private static void sendDM(User _user){
        _user.openPrivateChannel().queue((channel) -> channel.sendMessage("Huhu").queue());
        LOG.log(Level.INFO, "Msg send to: {0}", _user.getName());
    }

}
