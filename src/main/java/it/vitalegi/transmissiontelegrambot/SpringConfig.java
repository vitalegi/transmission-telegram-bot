package it.vitalegi.transmissiontelegrambot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import it.vitalegi.transmissiontelegrambot.telegram.TransmissionHandlers;

@Configuration
@Profile("prod")
public class SpringConfig {

	@Value("${telegram.bot_username}")
	String botUsername;

	@Value("${telegram.bot_token}")
	String botToken;

	@Bean
	public TelegramBotsApi telegramBot() {
		ApiContextInitializer.init();
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		return telegramBotsApi;
	}

	@Bean
	public TransmissionHandlers transmissionHandlers(TelegramBotsApi telegramBotsApi) {
		TransmissionHandlers transmissionHandlers = new TransmissionHandlers(botUsername, botToken);
		try {
			telegramBotsApi.registerBot(transmissionHandlers);
		} catch (TelegramApiException e) {
			throw new RuntimeException("Error configuring transmissionHandlers", e);
		}
		return transmissionHandlers;
	}
}
