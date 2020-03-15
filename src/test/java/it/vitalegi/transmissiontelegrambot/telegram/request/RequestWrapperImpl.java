package it.vitalegi.transmissiontelegrambot.telegram.request;

import java.util.List;

import it.vitalegi.transmissiontelegrambot.util.StringUtil;

public class RequestWrapperImpl implements RequestWrapper {

	protected String text;
	protected String documentName;
	protected byte[] documentContent;

	protected RequestWrapperImpl(String text) {
		super();
		this.text = text;
	}

	public RequestWrapperImpl(String documentName, byte[] documentContent) {
		super();
		this.documentName = documentName;
		this.documentContent = documentContent;
	}

	@Override
	public byte[] getDocumentContent() {
		return documentContent;
	}

	@Override
	public String getDocumentName() {
		return documentName;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public boolean hasDocument() {
		return documentName != null && !"".equals(documentName) && //
				documentContent != null && !"".equals(documentContent);
	}

	@Override
	public boolean hasText() {
		return text != null && !"".equals(text);
	}

	@Override
	public List<String> tokenizeText() {
		return StringUtil.tokenize(text);
	}

	@Override
	public Long getChatId() {
		// TODO Auto-generated method stub
		return null;
	}
}
