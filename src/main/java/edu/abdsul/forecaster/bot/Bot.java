package edu.abdsul.forecaster.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import edu.abdsul.forecaster.ForecasterController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Класс Bot
 */
public class Bot {
    private static final Logger logger = LoggerFactory.getLogger(Bot.class);
    private final TelegramBot bot = new TelegramBot("5115954828:AAG0hpmjRhNCU1KNk_iw58tA_iR18vbx5MM");

    public void serve() {

       logger.info("Bot started");

        bot.setUpdatesListener(updates -> {
            updates.forEach(this::process);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });

    }

    private void process(Update update) {
        logger.info("update has come");
        ForecasterController forecasterController = new ForecasterController();

        if (update.message() != null && update.message().text() != null) {
            String commandLine = update.message().text();

            logger.info("CommandLine: " +
                    commandLine + " from " + update.message().from().firstName());

            String forecast = forecasterController.getForecast(commandLine);

            logger.info("Forecast: " + forecast );
            long chatId = update.message().chat().id();

            BaseRequest request;

            File imageFile = new  File(forecast);

            if (imageFile.exists()) {
                request = new SendPhoto(chatId, imageFile);
            } else {
                request = new SendMessage(chatId, forecast);
            }
            bot.execute(request);
        }
    }

}
