package it.vitalegi.transmissiontelegrambot.telegram;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class HttpClientCallerImpl implements HttpClientCaller {

	Logger log = LoggerFactory.getLogger(HttpClientCallerImpl.class);

	@Override
	public HttpResponseWrapper call(HttpPost httpRequest) throws IOException {
		return doCall(httpRequest);
	}

	protected HttpResponseWrapper doCall(HttpPost request) throws IOException {

		String id = UUID.randomUUID().toString();

		try (CloseableHttpClient httpClient = HttpClientBuilder.create()//
				.addInterceptorLast(new HttpClientLoggerRequestInterceptor(id))//
				//.addInterceptorLast(new HttpClientLoggerResponseInterceptor(id))//
				.build()) {
			CloseableHttpResponse response = httpClient.execute(request);

			HttpResponseWrapperImpl responseWrapper = process(response);
			logResponse(id, responseWrapper);
			return responseWrapper;
		}
	}

	protected HttpResponseWrapperImpl process(CloseableHttpResponse response) {
		int status = response.getStatusLine().getStatusCode();
		String reasonPhrase = response.getStatusLine().getReasonPhrase();

		Map<String, List<String>> headers = new HashMap<>();
		for (Header header : response.getAllHeaders()) {
			if (!headers.containsKey(header.getName())) {
				headers.put(header.getName(), new ArrayList<>());
			}
			headers.get(header.getName()).add(header.getValue());
		}
		String body = toString(response.getEntity());
		return new HttpResponseWrapperImpl(status, reasonPhrase, headers, body);
	}

	protected void logResponse(String id, HttpResponseWrapper responseWrapper) {
		log.info("{} RESPONSE-------------------", id);
		log.info("{} Status: {}, {}", id, responseWrapper.getStatus(), responseWrapper.getReasonPhrase());
		responseWrapper.getHeaders().entrySet().forEach(h -> {
			log.info("{} Header: {}={}", id, h.getKey(), h.getValue());
		});
		log.info("{} Body:\n{}", id, responseWrapper.getPayload());
		log.info("{} ---------------------------", id);
	}

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