package it.vitalegi.transmissiontelegrambot.telegram.request;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class RequestWrapperFactory {

	public RequestWrapper getInstance(String text) {
		return new RequestWrapperImpl(text);
	}

	public RequestWrapper getInstance(String docName, String docContent) {
		return new RequestWrapperImpl(docName, docContent);
	}

	public RequestWrapper getInstance(DefaultAbsSender absSender, Message message) {
		return new TelegramRequestWrapperImpl(absSender, message);
	}
}
