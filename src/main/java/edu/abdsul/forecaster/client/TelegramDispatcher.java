package edu.abdsul.forecaster.client;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.SendResponse;
import edu.abdsul.forecaster.ForecasterController;

import java.io.File;
public class TelegramDispatcher {
    public static void main(String[] args) {
        String BOT_TOKEN = "5115954828:AAG0hpmjRhNCU1KNk_iw58tA_iR18vbx5MM";

        TelegramBot bot = new TelegramBot(BOT_TOKEN);
        ForecasterController forecasterController = new ForecasterController();

        bot.setUpdatesListener(updates -> {
            System.out.println("update has come");

            if (updates.get(0).message() != null) {
                String commandLine = updates.get(0).message().text();
                String forecast = forecasterController.getForecast(commandLine);
                long chatId = updates.get(0).message().chat().id();
                if (commandLine.contains("graph")) {
                    bot.execute(new SendPhoto(chatId, new File("./src/main/resources/img/img1.jpg")));
                } else {
                    bot.execute(new SendMessage(chatId, forecast));
                }
            }

            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });

    }
}
