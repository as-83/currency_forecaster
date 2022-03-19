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
 * Класс Bot осуществляет прием запросов и отправку ответов в Telegram
 */
public class Bot {
    private static final Logger logger = LoggerFactory.getLogger(Bot.class);
    private final TelegramBot bot = new TelegramBot(System.getenv("BOT_TOKEN"));

    public void serve() {
        logger.info("Bot started");

        bot.setUpdatesListener(updates -> {
            updates.forEach(this::process);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    private void process(Update update) {
        ForecasterController forecasterController = new ForecasterController();

        if (update.message() != null && update.message().text() != null) {
            String commandLine = update.message().text();

            logger.info("CommandLine: \"" +
                    commandLine + "\". From user: " + update.message().from().firstName());

            String forecast = forecasterController.getForecast(commandLine);

            logger.info("Forecast: {" + forecast + " }");
            long chatId = update.message().chat().id();

            BaseRequest request;
            File imageFile = new File(forecast);

            if (imageFile.exists()) {
                request = new SendPhoto(chatId, imageFile);
                logger.info("Image was sent to user");
            } else {
                request = new SendMessage(chatId, forecast);
                logger.info("Text was sent to user");
            }

            bot.execute(request);
        }
    }

}
