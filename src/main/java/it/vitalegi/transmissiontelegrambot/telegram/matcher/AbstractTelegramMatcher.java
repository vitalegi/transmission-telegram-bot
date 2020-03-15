package it.vitalegi.transmissiontelegrambot.telegram.matcher;

import java.util.ArrayList;
import java.util.List;

import it.vitalegi.transmissiontelegrambot.telegram.request.RequestWrapper;

public abstract class AbstractTelegramMatcher implements TelegramMatcher {

	@Override
	public boolean match(RequestWrapper request) {
		for (String cmd : commands()) {
			if (matchFirstTokenCaseInsensitive(request, cmd)) {
				return true;
			}
		}
		return false;
	}

	protected boolean matchFirstTokenCaseInsensitive(RequestWrapper request, String expected) {
		List<String> tokenized = request.tokenizeText();
		if (tokenized.isEmpty()) {
			return false;
		}
		String actual = tokenized.get(0);
		return expected.toUpperCase().equals(actual.toUpperCase());
	}

	protected List<String> commands() {
		return new ArrayList<String>();
	}
}