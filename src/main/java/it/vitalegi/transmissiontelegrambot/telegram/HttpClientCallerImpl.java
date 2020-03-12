package it.vitalegi.transmissiontelegrambot.telegram;

import java.io.IOException;
import java.util.UUID;

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
				.addInterceptorLast(new HttpClientLoggerResponseInterceptor(id))//
				.build()) {
			CloseableHttpResponse response = httpClient.execute(request);
			return new HttpResponseWrapperImpl(response);
		}
	}
}