package com.crbot;

import io.github.cdimascio.dotenv.Dotenv;
import java.io.*;
import java.util.logging.*;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.*;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.*;
import net.dv8tion.jda.api.requests.GatewayIntent;

/**
 * Clash Royale deckscout bot for Discord
 *
 * @author Noah
 */
public class CRBot{

    //sets attributes loads bot Token and channel name from .env
    static private final Request request = new Request();
    static private JDA jda;
    static private final Dotenv DOTENV = Dotenv.configure()
                                .directory(new File(".env").getAbsolutePath())
                                .ignoreIfMalformed().ignoreIfMissing().load();
    static private final String TOKEN = DOTENV.get("TOKEN");
    static private boolean current = true;
    static private final long OWNER = Long.parseLong(DOTENV.get("OWNER"));
    static private final Logger LOG = Logger.getLogger(CRBot.class.getName());

    /**
     * starts bot
     *
     * @param args String[]
     */
    public static void main(String[] args){
        build();
    }

    /**
     * builds the JDA with Listeners sets Guilds
     *
     * @throws LoginException
     * @throws InterruptedException
     */
    private static void build(){
        try{
            setUpJDA();
            addCommands();
            jda.awaitReady();
        }catch(InterruptedException ex){
            Logger.getLogger(CRBot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void addCommands(){
        jda.updateCommands()
                .addCommands(getSetCommands())
                .queue();
    }

    /**
     * creates JDA Object
     *
     * @throws LoginException
     */
    private static void setUpJDA(){
        try{
            jda = JDABuilder.createLight(TOKEN, GatewayIntent.GUILD_MESSAGES)
            .addEventListeners((EventListener) (GenericEvent ge) -> {
                if(ge instanceof ReadyEvent){
                    LOG.info("Ready");
                }else if(ge instanceof SlashCommandEvent event){
                    executeCommand(event);
                }
            })
            .setActivity(Activity.playing("PingPong | +help")).build();
        }catch(LoginException ex){
            Logger.getLogger(CRBot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void executeCommand(SlashCommandEvent event){
        try{
            switch(event.getName()){
                case "ping" ->
                    event.reply("pong").queue();
                case "pong" ->
                    event.reply("ping").queue();
                case "shutdown" ->
                    shutdown(event);
                case "restart" ->
                    restart(event);
                case "help" ->
                    help(event);
                case "commands" ->
                    showCommands(event);
                case "current" ->
                    setCurrent(event);
                case "decks" ->
                    decks(event);
                case "gt" ->
                    gt(event);

                default -> event.reply("Unknown Command").queue();
            }
        }catch(InterruptedException | IOException ex){
            Logger.getLogger(CRBot.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    private static void gt(SlashCommandEvent event){
        String name = null;
        String clan = null;
        if(isOptionNotNull(event, "name")){
            name = event.getOption("name").getAsString();
        }
        if(isOptionNotNull(event, "clan")){
            clan = event.getOption("clan").getAsString();
        }
        event.reply("Searching...").queue();
        event.getChannel().sendMessage(request.getGTDecks(name, clan)).queue();
    }

    private static void setCurrent(SlashCommandEvent event){
        if(event.getOption("current") != null){
            current = event.getOption("current").getAsBoolean();
        }else{
            current = !current;
        }
        event.reply("current: " + current).queue();
    }

    private static String makeRequest(long trophies, String name, String clan) throws IOException, InterruptedException{
        return request.getDecks(name, clan, trophies, current);
    }

    /**
     * displays all commands
     *
     * @param isBotChannel boolean
     * @param guild        Guild
     * @param channel      MessageChannel
     */
    private static void showCommands(SlashCommandEvent event){
        event.reply("todo").queue();
    }

    /**
     * displays help menu
     *
     * @param isBotChannel boolean
     * @param guild        Guild
     * @param channel      MessageChannel
     */
    private static void help(SlashCommandEvent event){
        event.reply("todo").queue();
    }

    /**
     * restarts bot
     *
     * @param isOwner boolean
     * @param message Message
     * @param guild   Guild
     * @param channel MessageChannel
     *
     * @throws InterruptedException
     * @throws LoginException
     */
    private static void restart(SlashCommandEvent event){
        if(isOwner(event.getUser())){
            LOG.info("Shutting down");
//            event.reply("restarting...").queue();
            event.reply("restarting...").complete();
            jda.cancelRequests();
            jda.shutdown();
            build();
        }else{
            LOG.log(Level.WARNING, "unotharized action by: {0} from: {1}", new Object[]{event.getUser().getName(), event.getGuild().getName()});
            event.getChannel().sendMessage("not authorized").queue();
        }
    }

    /**
     * shuts bot down and exits the programm
     *
     * @param isOwner boolean
     * @param message Message
     * @param guild   Guild
     * @param channel Channel
     */
    private static void shutdown(SlashCommandEvent event){
        if(isOwner(event.getUser())){
            event.reply("shutting down...").queue();
            LOG.info("Shutting down");
            jda.shutdown();
            System.exit(0);
        }else{
            LOG.log(Level.WARNING, "unotharized action by: {0} from: {1}", new Object[]{event.getUser().getName(), event.getGuild().getName()});
            event.getChannel().sendMessage("not authorized").queue();
        }
    }

    private static boolean isOwner(User author){
        return author.getIdLong() == OWNER;
    }

    private static void decks(SlashCommandEvent event) throws InterruptedException, IOException{
        String name = null;
        String clan = null;
        long trophies = 0;

        if(isOptionNotNull(event, "name")){
            name = event.getOption("name").getAsString();
        }
        if(isOptionNotNull(event, "clan")){
            clan = event.getOption("clan").getAsString();
        }
        if(isOptionNotNull(event, "trophies")){
            trophies = event.getOption("trophies").getAsLong();
        }

        event.reply("Searching...").queue();
        event.getChannel().sendMessage(makeRequest(trophies, name, clan)).queue();
    }

    private static boolean isOptionNotNull(SlashCommandEvent event, String name){
        return event.getOption(name) != null;
    }

    private static OptionData[] getSetOptions(){
        final OptionData TROPHIES = new OptionData(OptionType.INTEGER, "trophies", "Enter Integer");
        final OptionData NAME = new OptionData(OptionType.STRING, "name", "Enter String");
        final OptionData CLAN = new OptionData(OptionType.STRING, "clan", "Enter String");
        return new OptionData[]{NAME, CLAN, TROPHIES};
    }

    private static OptionData[] getGTOptions(){
        return new OptionData[]{getSetOptions()[0], getSetOptions()[1]};
    }

    private static CommandData[] getSetCommands(){
        final CommandData PING = new CommandData("ping", "pong");
        final CommandData DECKS = new CommandData("decks", "searches for matching players").addOptions(getSetOptions());
        final CommandData GT = new CommandData("gt", "only works in top 1000").addOptions(getGTOptions());
        final CommandData PONG = new CommandData("pong", "ping");
        final CommandData SHUTDOWN = new CommandData("shutdown", "shutsdown bot");
        final CommandData RESTART = new CommandData("restart", "restarts bot");
        final CommandData HELP = new CommandData("help", "displays help");
        final CommandData COMMANDS = new CommandData("commands", "shows all commands");
        final CommandData CURRENT = new CommandData("current", "true: current deck, false: last deck").addOption(OptionType.BOOLEAN, "current", "Enter Boolean");
        return new CommandData[]{PING, DECKS, GT, PONG, SHUTDOWN, RESTART, HELP, COMMANDS, CURRENT};
    }

}
