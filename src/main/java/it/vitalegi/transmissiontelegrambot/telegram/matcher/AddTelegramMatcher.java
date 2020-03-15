package it.vitalegi.transmissiontelegrambot.telegram.matcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vitalegi.transmissiontelegrambot.telegram.request.RequestWrapper;
import it.vitalegi.transmissiontelegrambot.telegram.request.processor.AddTelegramRequestProcessor;
import it.vitalegi.transmissiontelegrambot.telegram.request.processor.TelegramRequestProcessor;
import it.vitalegi.transmissiontelegrambot.telegram.response.processor.ListTelegramResponseProcessor;
import it.vitalegi.transmissiontelegrambot.telegram.response.processor.TelegramResponseProcessor;

@Service
public class AddTelegramMatcher extends AbstractTelegramMatcher {
	public static final String CMD = "add";

	@Autowired
	protected AddTelegramRequestProcessor requestProcessor;
	@Autowired
	protected ListTelegramResponseProcessor responseProcessor;

	@Override
	public boolean match(RequestWrapper request) {
		return request.hasDocument();
	}

	@Override
	public TelegramRequestProcessor getRequestProcessor() {
		return requestProcessor;
	}

	@Override
	public TelegramResponseProcessor getResponseProcessor() {
		return responseProcessor;
	}

}
