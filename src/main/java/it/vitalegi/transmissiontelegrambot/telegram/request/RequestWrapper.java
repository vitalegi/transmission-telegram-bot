package it.vitalegi.transmissiontelegrambot.telegram.request;

import java.util.List;

public interface RequestWrapper {

	public List<String> tokenizeText();

	public String getText();

	public boolean hasText();

	public String getDocumentContent();

	public String getDocumentName();

	public boolean hasDocument();
}
