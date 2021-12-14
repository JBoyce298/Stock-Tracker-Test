package org.example;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * a class used for testing bot commands acorss telegram
 */
public class BotTester {

    /**
     * used to test ensuring a user's chat id was pulled from a telegram message to the bot
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void UserIdFound() throws IOException, InterruptedException {
        BotHandler bot = new BotHandler();

        while(bot.getChatID() == null) {
            bot.lookForNewUser();
        }

        System.out.println(bot.getChatID());
    }

    /**
     * used to test sending a message back to the user after the user send a message to the bot
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void sendMessage() throws IOException, InterruptedException {
        BotHandler bot = new BotHandler();

        while(bot.getChatID() == null) {
            bot.lookForNewUser();
        }

        bot.sendMessage("hello world");
    }

    /**
     * test that looks for a specific message from the user and stops once the message is entered
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void lookForMessage() throws IOException, InterruptedException
    {
        BotHandler bot = new BotHandler();

        while(bot.getChatID() == null) {
            bot.lookForNewUser();
        }

        boolean waiting = true;
        while(waiting)
        {
            if(bot.lookForSpecificMessage("1"))
            {
                System.out.println("Success");
                waiting = false;
            }
        }
    }

    /**
     * a test that waits for the user's next message and asserts it to be "hello" for testing what the message
     * returns when brought to the code
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void waitToReceive() throws IOException, InterruptedException {
        BotHandler bot = new BotHandler();

        while(bot.getChatID() == null) {
            bot.lookForNewUser();
        }

        Assert.assertEquals("hello",bot.waitToReceiveNext());
    }


}
