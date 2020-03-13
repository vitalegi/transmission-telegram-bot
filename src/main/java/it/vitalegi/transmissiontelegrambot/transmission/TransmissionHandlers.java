package it.vitalegi.transmissiontelegrambot.transmission;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import it.vitalegi.transmissiontelegrambot.TelegramRequestToActionServiceImpl;
import it.vitalegi.transmissiontelegrambot.action.Action;
import it.vitalegi.transmissiontelegrambot.telegram.request.RequestWrapper;
import it.vitalegi.transmissiontelegrambot.telegram.request.TelegramRequestWrapperImpl;

public class TransmissionHandlers extends TelegramLongPollingBot {

	Logger log = LoggerFactory.getLogger(TransmissionHandlers.class);

	String botUsername;

	String botToken;

	@Autowired
	TelegramRequestToActionServiceImpl telegramRequestToActionService;

	@Autowired
	TransmissionCallServiceImpl telegramCallService;

	public TransmissionHandlers(String botUsername, String botToken) {
		super();
		this.botUsername = botUsername;
		this.botToken = botToken;
	}

	@Override
	public String getBotToken() {
		log.info("getBotToken");
		return botToken;
	}

	@Override
	public String getBotUsername() {
		log.info("getBotUsername");
		return botUsername;
	}

	@Override
	public void onUpdateReceived(Update update) {
		log.info("onUpdateReceived {}", update);

		Message message = update.getMessage();

		RequestWrapper messageWrapper = new TelegramRequestWrapperImpl(this, message);

		if (messageWrapper.hasText()) {
			log.info("Received text: {}", messageWrapper.getText());
		}
		if (messageWrapper.hasDocument()) {
			log.info("Received file: {}", messageWrapper.getDocumentName());
		}
		Action action = telegramRequestToActionService.process(messageWrapper);

		JSONObject response = telegramCallService.call(action);

		SendMessage reply = new SendMessage();
		reply.setChatId(message.getChatId());
		reply.setText("Hello " + response.toString());
		try {
			super.execute(reply);
		} catch (TelegramApiException e) {
			log.error(e.getMessage(), e);
		}
	}

}
