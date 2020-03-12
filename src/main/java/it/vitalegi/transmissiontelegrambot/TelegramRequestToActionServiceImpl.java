package it.vitalegi.transmissiontelegrambot;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vitalegi.transmissiontelegrambot.action.Action;
import it.vitalegi.transmissiontelegrambot.exception.NoSuchRequestProcessorException;
import it.vitalegi.transmissiontelegrambot.telegram.request.RequestWrapper;
import it.vitalegi.transmissiontelegrambot.telegram.request.processor.TelegramRequestProcessor;

@Service
public class TelegramRequestToActionServiceImpl {

	@Autowired
	private List<TelegramRequestProcessor> processors;

	public Action process(RequestWrapper request) {

		TelegramRequestProcessor processor = processors.stream()//
				.filter(p -> p.match(request))//
				.findFirst()//
				.orElseThrow(() -> new NoSuchRequestProcessorException(request));

		return processor.process(request);
	}
}
