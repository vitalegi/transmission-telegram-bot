package it.vitalegi.transmissiontelegrambot.action;

import org.json.JSONObject;

public class Action {

	ActionMethod method;
	JSONObject arguments;

	public JSONObject getArguments() {
		return arguments;
	}

	public ActionMethod getMethod() {
		return method;
	}

	public void setArguments(JSONObject arguments) {
		this.arguments = arguments;
	}

	public void setMethod(ActionMethod method) {
		this.method = method;
	}
}
