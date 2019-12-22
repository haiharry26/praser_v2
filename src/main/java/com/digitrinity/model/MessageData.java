package com.digitrinity.model;

public class MessageData {

	int msgId;
	
	String deviceData;
	
	public MessageData(int msgId, String deviceData) {
		super();
		this.msgId = msgId;
		this.deviceData = deviceData;
	}

	public int getMsgId() {
		return msgId;
	}

	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}

	public String getDeviceData() {
		return deviceData;
	}

	public void setDeviceData(String deviceData) {
		this.deviceData = deviceData;
	}
}
