package it.vitalegi.transmissiontelegrambot.telegram;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vitalegi.transmissiontelegrambot.action.Action;
import it.vitalegi.transmissiontelegrambot.exception.NoSuchRequestProcessorException;
import it.vitalegi.transmissiontelegrambot.telegram.matcher.TelegramMatcher;
import it.vitalegi.transmissiontelegrambot.telegram.request.RequestWrapper;
import it.vitalegi.transmissiontelegrambot.util.logging.LogExecutionTime;

@Service
public class TelegramDispatcherImpl {

	@Autowired
	private List<TelegramMatcher> matchers;

	@LogExecutionTime
	public Action processRequest(RequestWrapper request) {

		TelegramMatcher matcher = getMatcher(request);

		return matcher.getRequestProcessor().process(request);
	}

	@LogExecutionTime
	public void processResponse(RequestWrapper request, Action action, JSONObject response) {

		TelegramMatcher matcher = getMatcher(request);

		matcher.getResponseProcessor().process(request, action, response);
	}

	protected TelegramMatcher getMatcher(RequestWrapper request) {

		TelegramMatcher matcher = matchers.stream()//
				.filter(p -> p.match(request))//
				.findFirst()//
				.orElseThrow(() -> new NoSuchRequestProcessorException(request));

		return matcher;
	}
}
