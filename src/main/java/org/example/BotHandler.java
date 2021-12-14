package org.example;

import org.apache.commons.io.IOUtils;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * This class utilizes a Telegram bot to receive and send messages with the user
 */
public class BotHandler {
    private static String CHAT_ID;
    //jacob's chat id = 1667262030
    private static final String TOKEN = "1875452693:AAG58YPlG8x5ZhZDx-kGAR3-TghXs4Uv7pA";
    //link to message the bot whose toke is above: http://t.me/StonkNotifierbot
    private static String updatesURL = "https://api.telegram.org/bot1875452693:AAG58YPlG8x5ZhZDx-kGAR3-TghXs4Uv7pA/getUpdates";
    //get messages from users at https://api.telegram.org/bot1875452693:AAG58YPlG8x5ZhZDx-kGAR3-TghXs4Uv7pA/getUpdates
    private static String previousUpdates;

    /**
     * constructor that handles pulling the initial json from the getUpdates method site and
     * creates a BotHandler object
     * @throws IOException
     */
    public BotHandler() throws IOException {
        URL gistURL = new URL(updatesURL);
        previousUpdates = IOUtils.toString(gistURL.openStream(),"UTF-8");
    }

    /**
     * used to getting the current user's chat id
     * @return user's chat id
     */
    public String getChatID()
    {
        return CHAT_ID;
    }

    /**
     * used for setting the chat id to something new
     * @param chatID the new id
     */
    public void setChatID(String chatID)
    {
        CHAT_ID = chatID;
    }

    /**
     * a method that uses http2 in order to send a message to a specific chat id that a user has
     * @param str the message to be sent to the user
     * @throws IOException
     * @throws InterruptedException
     */
    public void sendMessage(String str) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .version(HttpClient.Version.HTTP_2)
                .build();

            UriBuilder builder = UriBuilder
                    .fromUri("https://api.telegram.org")
                    .path("/{token}/sendMessage")
                    .queryParam("chat_id", CHAT_ID)
                    .queryParam("text", str);

            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(builder.build("bot" + TOKEN))
                    .timeout(Duration.ofSeconds(5))
                    .build();

            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            //System.out.println(response.statusCode());
           // System.out.println(response.body());
            //Thread.sleep(600000);
    }

    /**
     * used in tandem with a while loop to receive a user's chat id after a message from them.
     * this is to ensure the bot can run for anyone who sends a message first
     * @throws IOException
     * @throws InterruptedException
     */
    public void lookForNewUser() throws IOException, InterruptedException {
        checkURL();
        URL gistURL = new URL(updatesURL);
        String str = IOUtils.toString(gistURL.openStream(),"UTF-8");
        String[] messages;
        if(!str.equalsIgnoreCase(previousUpdates))
        {
            messages = str.split("}}");
            String current = messages[messages.length - 2];
            current.replace("\"","");
            String[] currentSplit = current.split("},");
            String idSection = currentSplit[currentSplit.length - 2];
            String[] next = idSection.split(",");
            String idNarrow = next[0];
            String[] nextSplit = idNarrow.split(":");
            String id = nextSplit[nextSplit.length - 1];
            CHAT_ID = id;
            sendMessage("User Identified");
        }
    }

    /**
     * Looks to see if the user has sent a message matching a specific string
     * for use in if statements and booleans
     * @param match the string that the user must match for the method to return true
     * @return whether or not the user entered the same string as match
     * @throws IOException
     */
    public Boolean lookForSpecificMessage(String match) throws IOException {
        checkURL();
        URL gistURL = new URL(updatesURL);
        String str = IOUtils.toString(gistURL.openStream(),"UTF-8");
        String[] messages = str.split("}}");
        String current = messages[messages.length - 2];
        current.replace("\"","");
        String[] currentSplit = current.split("},");
        String idSection = currentSplit[currentSplit.length - 1];
        String[] next = idSection.split(",");
        String idNarrow = next[1];
        String[] nextSplit = idNarrow.split(":");
        String text = nextSplit[nextSplit.length - 1];
        text = text.substring(1,text.length() - 1);

        //System.out.println(text);
        if(text.equalsIgnoreCase(match))
        {
            return true;
        }
        return false;
    }

    /**
     * Used to return whatever message the user sent to the bot, so that the message can be used in code
     * @return whatever message the user sent to the bot
     * @throws IOException
     */
    public String waitToReceiveNext() throws IOException {
        checkURL();
        URL gistURL = new URL(updatesURL);
        String nextMessage = IOUtils.toString(gistURL.openStream(),"UTF-8");
        String tempMessage = nextMessage;

        while (nextMessage.equals(tempMessage)){
            nextMessage = IOUtils.toString(gistURL.openStream(),"UTF-8");
        }

        String[] messages = nextMessage.split("}}");
        String current = messages[messages.length - 2];
        current.replace("\"","");
        String[] currentSplit = current.split("},");
        String idSection = currentSplit[currentSplit.length - 1];
        String[] next = idSection.split(",");
        String idNarrow = next[1];
        String[] nextSplit = idNarrow.split(":");
        String text = nextSplit[nextSplit.length - 1];
        text = text.substring(1,text.length() - 1);

        return text;
    }

    /**
     * Checks the updates page to make sure the page is not full since the webpage can only handle 100 entries,
     * the offset parameter of the method getUpdates must be used to offset which update_id is shown at the start of the
     * page to refresh what can be seen on the getUpdates page in the future
     * @throws IOException
     */
    public void checkURL() throws IOException {
        URL gistURL = new URL(updatesURL);
        String str = IOUtils.toString(gistURL.openStream(),"UTF-8");
        String[] messages = str.split("}}");
        if(messages.length >= 99)
        {
            String[] currentSplit = messages[messages.length - 2].split("},");
            String idSection = currentSplit[0];
            String[] next = idSection.split(",");
            String idNarrow = next[1];
            String[] nextSplit = idNarrow.split(":");
            String text = nextSplit[nextSplit.length - 1];
            updatesURL = updatesURL + "?offset=" + text;
            gistURL = new URL(updatesURL);
            previousUpdates = IOUtils.toString(gistURL.openStream(),"UTF-8");
        }
        else {
            if(!updatesURL.substring(updatesURL.length() - 10, updatesURL.length()).equals("getUpdates")){
                updatesURL = "https://api.telegram.org/bot1875452693:AAG58YPlG8x5ZhZDx-kGAR3-TghXs4Uv7pA/getUpdates";
            }
        }
    }
}
