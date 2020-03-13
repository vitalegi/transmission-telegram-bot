package it.vitalegi.transmissiontelegrambot.transmission.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;

public abstract class AbstractHttpClientLogger {

	protected String toString(HttpEntity entity) {
		ContentType contentType = ContentType.getOrDefault(entity);
		try {
			EntityUtils.toString(entity, contentType.getCharset());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		OutputStream os = new ByteArrayOutputStream();
		try {
			entity.writeTo(os);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return os.toString();
	}
}
