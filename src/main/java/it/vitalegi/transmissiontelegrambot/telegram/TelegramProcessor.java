package it.vitalegi.transmissiontelegrambot.telegram;

import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import it.vitalegi.transmissiontelegrambot.action.Action;
import it.vitalegi.transmissiontelegrambot.exception.InvalidTelegramRequestException;
import it.vitalegi.transmissiontelegrambot.telegram.request.RequestWrapper;
import it.vitalegi.transmissiontelegrambot.telegram.request.TelegramRequestWrapperImpl;
import it.vitalegi.transmissiontelegrambot.telegram.response.processor.ListTelegramResponseProcessor;
import it.vitalegi.transmissiontelegrambot.transmission.TransmissionCallServiceImpl;

@Service
public class TelegramProcessor {

	Logger log = LoggerFactory.getLogger(TelegramProcessor.class);

	@Autowired
	TelegramDispatcherImpl telegramDispatcher;

	@Autowired
	TransmissionCallServiceImpl telegramCallService;

	@Autowired
	TelegramHandlers telegramHandlers;

	@Value("#{'${telegram.authorized-ids}'.split(',')}")
	List<Integer> authorizedIds;

	@Autowired
	ListTelegramResponseProcessor listTelegramResponseProcessor;

	public void process(Update update) {

		isAuthorized(update);

		Message message = update.getMessage();

		RequestWrapper messageWrapper = new TelegramRequestWrapperImpl(telegramHandlers, message);

		if (messageWrapper.hasText()) {
			log.info("received message from {}: {}", messageWrapper.getChatId(), messageWrapper.getText());
		}

		Action action = telegramDispatcher.processRequest(messageWrapper);

		JSONObject response = telegramCallService.call(action);

		telegramDispatcher.processResponse(messageWrapper, action, response);
	}

	protected void isAuthorized(Update update) {
		Integer userId = update.getMessage().getFrom().getId();
		for (Integer id : authorizedIds) {
			log.info("Match {} vs {}", userId, id);
			if (id.equals(userId)) {
				return;
			}
		}
		User sender = update.getMessage().getFrom();
		throw new InvalidTelegramRequestException(
				"User not authorized: " + sender.getUserName() + " " + sender.getId());
	}
}
