package it.vitalegi.transmissiontelegrambot.telegram.matcher;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vitalegi.transmissiontelegrambot.telegram.request.processor.RemoveTelegramRequestProcessor;
import it.vitalegi.transmissiontelegrambot.telegram.request.processor.TelegramRequestProcessor;
import it.vitalegi.transmissiontelegrambot.telegram.response.processor.ListTelegramResponseProcessor;
import it.vitalegi.transmissiontelegrambot.telegram.response.processor.TelegramResponseProcessor;

@Service
public class RemoveTelegramMatcher extends AbstractTelegramMatcher {
	public static final String CMD = "remove";

	@Autowired
	protected RemoveTelegramRequestProcessor requestProcessor;
	@Autowired
	protected ListTelegramResponseProcessor responseProcessor;

	@Override
	protected List<String> commands() {
		return Arrays.asList(CMD);
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
