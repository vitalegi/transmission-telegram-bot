package it.vitalegi.transmissiontelegrambot.transmission.http;

import java.io.IOException;

import org.apache.http.client.methods.HttpPost;

public interface HttpClientCaller {

	HttpResponseWrapper call(HttpPost httpRequest) throws IOException;

}