package it.vitalegi.transmissiontelegrambot.telegram.request;

import org.springframework.stereotype.Service;

@Service
public class RequestWrapperFactory {

	public RequestWrapper getInstance(String text) {
		return new RequestWrapperImpl(text);
	}

	public RequestWrapper getInstance(String docName, byte[] docContent) {
		return new RequestWrapperImpl(docName, docContent);
	}
}
