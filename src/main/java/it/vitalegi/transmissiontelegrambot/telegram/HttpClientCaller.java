package it.vitalegi.transmissiontelegrambot.telegram;

import java.io.IOException;

import org.apache.http.client.methods.HttpPost;

public interface HttpClientCaller {

	HttpResponseWrapper call(HttpPost httpRequest) throws IOException;

}