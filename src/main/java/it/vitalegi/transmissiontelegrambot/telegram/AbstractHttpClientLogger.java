package it.vitalegi.transmissiontelegrambot.telegram;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.http.HttpEntity;

public abstract class AbstractHttpClientLogger {

	protected String toString(HttpEntity entity) {
		OutputStream os = new ByteArrayOutputStream();
		try {
			entity.writeTo(os);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return os.toString();
	}
}
