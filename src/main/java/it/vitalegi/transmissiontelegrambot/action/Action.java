package it.vitalegi.transmissiontelegrambot.action;

import org.json.JSONObject;

public class Action {

	ActionMethod method;
	JSONObject arguments;

	public ActionMethod getMethod() {
		return method;
	}

	public void setMethod(ActionMethod method) {
		this.method = method;
	}

	public JSONObject getArguments() {
		return arguments;
	}

	public void setArguments(JSONObject arguments) {
		this.arguments = arguments;
	}
}
