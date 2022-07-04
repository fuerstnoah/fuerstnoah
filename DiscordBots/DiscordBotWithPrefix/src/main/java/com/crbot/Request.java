package com.crbot;

import io.github.cdimascio.dotenv.Dotenv;
import java.io.*;
import java.net.URI;
import java.net.http.*;
import java.util.*;
import java.util.logging.*;
import org.json.simple.*;

/**
 * Requests decks from cr api for matching players
 *
 * @author Noah
 */
public class Request{

    //loads Bearer Token from .env File
    static private final Dotenv DOTENV = Dotenv.configure()
                                .directory(new File(".env").getAbsolutePath())
                                .ignoreIfMalformed().ignoreIfMissing().load();
    static private final String BEARER = DOTENV.get("BEARER");
    private static final Logger LOG = Logger.getLogger(Request.class.getName());
    private static final Map<Long, Card> CARDNAMES = new HashMap<>(110);

    /**
     * Creates Request Object
     */
    public Request(){
        setMap();
    }

    /**
     * returns matching players
     *
     * @param name        String
     * @param clan        String
     * @param trophies    long
     * @param currentDeck boolean
     *
     * @return String
     */
    public String getDecks(String name, String clan, long trophies,
            boolean currentDeck){
        LOG.info("received request");
        return parseTags(getTags(name, clan, trophies), currentDeck);
    }

    public String getGTDecks(String name, String clan){
        ArrayList<String> tags = getGTTags(name, clan);
        if(tags.isEmpty()){
            tags = getTags(name, clan, 0);
        }
        return parseTags(tags, false);
    }

    private ArrayList<String> getGTTags(String name, String clan){
        ArrayList<String> tags = new ArrayList<>(20);
        for(Object obj : getGTPlayers()){
            if(obj instanceof JSONObject player){
                if(name != null){
                    if(((String) player.get("name")).toLowerCase().contains(name
                            .toLowerCase())){
                        tags.add((String) player.get("tag"));
                    }
                }
                if(clan != null && player.containsKey("clan")){
                    JSONObject clanObject = (JSONObject) player.get("clan");
                    if(((String) clanObject.get("name")).toLowerCase().contains(
                            clan.toLowerCase())){
                        if(!tags.contains((String) player.get("tag"))){
                            tags.add((String) player.get("tag"));
                        }
                    }
                }
            }
        }
        return tags;
    }

    private String getGTTag(){
        JSONObject obj = (JSONObject) JSONValue.parse(request(
                   "https://proxy.royaleapi.dev/v1/globaltournaments"));
        JSONArray tournaments = (JSONArray) obj.get("items");
        JSONObject lastGT = (JSONObject) tournaments.get(tournaments.size() - 1);
        return (String) lastGT.get("tag");
    }

    private JSONArray getGTPlayers(){
        String tag = getGTTag();
        JSONObject obj = (JSONObject) JSONValue.parse(request(
                   "https://proxy.royaleapi.dev/v1/locations/global/rankings/tournaments/" + tag));
        return (JSONArray) obj.get("items");
    }

    /**
     * fills ArrayList with matching tags
     *
     * @param name     String
     * @param clan     String
     * @param trophies long
     *
     * @return ArrayList<String>
     *
     * @throws IOException
     * @throws InterruptedException
     */
    private ArrayList<String> getTags(String name, String clan, long trophies){
        ArrayList<String> tags = new ArrayList<>(20);
        for(Object object : getPlayers(clan)){
            if(iteratePlayers(object, trophies, clan, name, tags)){
                break;
            }
        }
        return tags;
    }

    /**
     * iterates all given players
     *
     * @param object   Object
     * @param trophies long
     * @param clan     String
     * @param name     String
     * @param tags     ArrayList<String>
     *
     * @return boolean
     */
    private boolean iteratePlayers(Object object, long trophies, String clan,
            String name, ArrayList<String> tags){

        boolean isTrophies = name == null && clan == null && trophies > 0;
        boolean isName = trophies == 0 && clan == null && name != null;
        boolean isClanTrophies = name == null && clan != null && trophies > 0;
        boolean isNameTrophies = clan == null && name != null && trophies > 0;
        boolean isClanName = name != null && clan != null && trophies == 0;
        boolean isAll = name != null && clan != null && trophies > 0;

        return boolSwitch(object, isName, name, tags, isTrophies, trophies,
                isNameTrophies, isClanTrophies, isClanName, isAll);
    }

    /**
     * @param object         Object
     * @param isName         boolean
     * @param name           String
     * @param tags           ArrayList<String>
     * @param isTrophies     boolean
     * @param trophies       long
     * @param isNameTrophies boolean
     * @param isClanTrophies boolean
     * @param isClanName     boolean
     *
     * @return
     */
    private boolean boolSwitch(Object object, boolean isName, String name,
            ArrayList<String> tags, boolean isTrophies,
            long trophies, boolean isNameTrophies,
            boolean isClanTrophies, boolean isClanName,
            boolean isAll){

        if(object instanceof JSONObject o){
            if(isName){
                onlyName(name, tags, o);
            }else if(isTrophies){
                if(onlyTrophies(trophies, tags, o)){
                    return true;
                }else if(isNameTrophies){
                    if(nameAndTrophies(trophies, name, tags, o)){
                        return true;
                    }else if(isClanTrophies){
                        if(onlyTrophies(trophies, tags, o)){
                            return true;
                        }else if(isClanName){
                            onlyName(name, tags, o);
                        }else if(isAll){
                            onlyName(name, tags, o);
                        }
                    }
                }
            }
        }
        return false;
    }

//    /**
//     * checks if player matches
//     * @param name String
//     * @param tags ArrayList<String>
//     * @param player JSONObject
//     */
//    private void clanAndName(String name, ArrayList<String> tags, JSONObject player) {
//        String str = (String) player.get("name");
//        if (str.toLowerCase().contains(name.toLowerCase())) tags.add((String) player.get("tag"));
//    }
    /**
     * checks if player matches
     *
     * @param trophies long
     * @param name     String
     * @param tags     ArrayList<String>
     * @param player   JSONObject
     *
     * @return boolean
     */
    private boolean nameAndTrophies(long trophies, String name,
            ArrayList<String> tags, JSONObject player){
        if((long) player.get("trophies") == trophies && ((String) player.get(
                "name")).toLowerCase()
                .contains(name.toLowerCase())){
            tags.add((String) player.get("tag"));
        }else if((long) player.get("trophies") < trophies){
            return true;
        }
        return false;
    }

    /**
     * checks if player matches
     *
     * @param trophies long
     * @param tags     ArrayList<String>
     * @param player   JSONObject
     *
     * @return boolean
     */
    private boolean onlyTrophies(long trophies, ArrayList<String> tags,
            JSONObject player){
        if((long) player.get("trophies") == trophies){
            tags.add((String) player.get("tag"));
        }else if((long) player.get("trophies") < trophies){
            return true;
        }
        return false;
    }

    /**
     * checks if player matches
     *
     * @param name    String
     * @param tags    ArrayList<String>
     * @param Jplayer SONObject
     */
    private void onlyName(String name, ArrayList<String> tags, JSONObject player){
        String str = (String) player.get("name");
        if(str.toLowerCase().contains(name.toLowerCase())){
            tags.add((String) player.get("tag"));
        }
    }

    /**
     * returns list of players from cr api
     *
     * @param clan String
     *
     * @return JSONArray
     *
     * @throws IOException
     * @throws InterruptedException
     */
    private JSONArray getPlayers(String clan){
        JSONObject obj;
        if(clan != null){
            obj = getMembers(clan);
            if(obj == null || obj.isEmpty()){
                obj = (JSONObject) JSONValue.parse(request(
                "https://proxy.royaleapi.dev/v1/locations/global/rankings/players"));
            }
        }else{
            obj = (JSONObject) JSONValue.parse(request(
            "https://proxy.royaleapi.dev/v1/locations/global/rankings/players"));
        }
        JSONArray jArray = (JSONArray) obj.get("items");
        return jArray;
    }

    /**
     * returns clanmembers
     *
     * @param clan String
     *
     * @return JSONObject
     *
     * @throws IOException
     * @throws InterruptedException
     */
    private JSONObject getMembers(String clan){
        JSONObject obj1 = (JSONObject) JSONValue.parse(request(
                   "https://proxy.royaleapi.dev/v1/clans?name=" + clan + "&minMembers=2&maxMembers=50&minScore=1&limit=1"));
        JSONArray array1 = (JSONArray) obj1.get("items");
        if(array1.isEmpty()){
            return null;
        }
        JSONObject jsclan = (JSONObject) array1.get(0);
        String clanTag = (String) jsclan.get("tag");

        return (JSONObject) JSONValue.parse(request(
                "https://proxy.royaleapi.dev/v1/clans/" + clanTag + "/members"));
    }

    /**
     * returns the current deck from given playertag
     *
     * @param tag    String
     * @param string StringBuilder
     *
     * @return JSONArray
     *
     * @throws IOException
     * @throws InterruptedException
     */
    private JSONArray currentDeck(String tag, StringBuilder string){
        JSONObject obj = (JSONObject) JSONValue.parse(request(
                   "https://proxy.royaleapi.dev/v1/players/" + tag));
        JSONArray arr = (JSONArray) obj.get("currentDeck");
        string.append("\n").append(obj.get("name")).append("\n");
        return arr;
    }

    /**
     * parses given playertags to decks
     *
     * @param tags        ArrayList<String>
     * @param currentDeck boolean
     *
     * @return String
     */
    private String parseTags(ArrayList<String> tags, boolean currentDeck){
        StringBuilder string = new StringBuilder(200);
        if(tags.isEmpty()){
            return "Keinen Spieler gefunden.";
        }
        iterateTags(tags, currentDeck, string);

        return string.toString();
    }

    /**
     * iterates given playertags
     *
     * @param tags        ArrayList<String>
     * @param currentDeck boolean
     * @param string      StringBuilder
     */
    private void iterateTags(ArrayList<String> tags, boolean currentDeck,
            StringBuilder string){
        tags.forEach((String tag) -> {
            JSONArray arr;
            if(currentDeck){
                arr = currentDeck(tag, string);
            }else{
                arr = lastDeck(tag, string);
            }
            createDeck(arr, string);
        });
    }

    /**
     * adds deck to string
     *
     * @param arr    JSONArray
     * @param string StringBuilder
     */
    @SuppressWarnings("unchecked")
    private void createDeck(JSONArray arr, StringBuilder string){
        Deck deck = new Deck();
        arr.forEach((Object card) -> {
            if(card instanceof JSONObject o){
                deck.addCard(CARDNAMES.get((Long) o.get("id")));
            }
        });
        string.append(deck.getNames());
        string.append("\n\n");
    }

    /**
     * returns the last Deck from given playertag
     *
     * @param tag    String
     * @param string StringBuilder
     *
     * @return JSONArray
     *
     * @throws IOException
     * @throws InterruptedException
     */
    private JSONArray lastDeck(String tag, StringBuilder string){
        JSONArray arr = (JSONArray) JSONValue.parse(request(
                  "https://proxy.royaleapi.dev/v1/players/" + tag + "/battlelog"));
        arr = (JSONArray) ((JSONObject) arr.get(0)).get("team");
        JSONObject obj = (JSONObject) arr.get(0);
        string.append("\n").append(obj.get("name")).append("\n");
        arr = (JSONArray) obj.get("cards");
        return arr;
    }

    /**
     * only for testing
     *
     * @param args String[]
     */
    public static void main(String[] args){
        Request req = new Request();
//        System.out.println(req.getGTTag());
//        System.out.println(req.getGTPlayers().toJSONString());
//        System.out.println(req.getGTTags("Fürst", "Mkerx"));
//        System.out.println(req.getGTDecks("spark", "TRAP"));

        System.out.println("only trophies");
        System.out.println(req.getDecks(null, null, 7755, true));
        System.out.println("only name");
        System.out.println(req.getDecks("morten", null, 0, true));
        LOG.info("clan and trophies");
        LOG.info(req.getDecks(null, "Mkerx", 7368, true));
        LOG.info("clan and name");
        LOG.info(req.getDecks("Fürst", "Mkerx", 0, false));
    }

    /**
     * executes an HttpRequest with given url returns JSON
     *
     * @param url String
     *
     * @return String
     *
     * @throws IOException
     * @throws InterruptedException
     */
    private static String request(String url){
        try{
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url
                        .replace(" ", "%20").replace("#", "%23")))
                        .header("Accept", "application/json").header(
                        "authorization", BEARER)
                        .build();
            LOG.info("sending req");
            HttpResponse<String> response = client.send(request,
                                 HttpResponse.BodyHandlers
                                         .ofString());
            LOG.info("response received");
            return response.body();
        }catch(IOException | InterruptedException ex){
            Logger.getLogger(Request.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * fills HashMap<Long,Card> with card names
     *
     * @throws IOException
     * @throws InterruptedException
     */
    private static void setMap(){
        for(String str : readFile().split("\n")){
            try{
                CARDNAMES.put(Long.parseLong(str.split(":")[0]), new Card(Long
                        .parseLong(str.split(":")[0]), str.split(":")[1],
                        Long
                                .parseLong(
                                        str
                                                .split(":")[2]
                                                .replaceAll(
                                                        "[\\r\\n]+",
                                                        ""))));
            }catch(NumberFormatException e){
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Reads card names from cards.txt
     *
     * @return String
     *
     * @throws IOException
     */
    private static String readFile(){
        StringBuilder builder;
        try( FileReader fr = new FileReader(new File("cards.txt"))){
            builder = new StringBuilder(1100);
            while(fr.ready()){
                builder.append((char) fr.read());
            }
            return builder.toString();
        }catch(IOException ex){
            Logger.getLogger(Request.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
