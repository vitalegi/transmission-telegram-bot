package it.vitalegi.transmissiontelegrambot.util;

import org.json.JSONException;
import org.json.JSONObject;

public class Json {
	JSONObject json;

	public Json() {
		json = new JSONObject();
	}

	public static Json init() {
		return new Json();
	}

	public Json put(String name, String value) {
		try {
			json.put(name, value);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
		return this;
	}

	public Json put(String name, JSONObject value) {
		try {
			json.put(name, value);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
		return this;
	}

	public JSONObject build() {
		return json;
	}

	public String buildString() {
		return json.toString();
	}
}
