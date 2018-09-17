package com.waymaps.data.model;

import com.fasterxml.jackson.annotation.JsonSetter;

public class RemoteTask {
    private String commandId;
    private String trackerId;
    private String phone;
    private String commandText;
    private String isResponseRequired;

    public RemoteTask() {
    }

    public RemoteTask(String commandId, String trackerId, String phone, String commandText, String isResponseRequired) {
        this.commandId = commandId;
        this.trackerId = trackerId;
        this.phone = phone;
        this.commandText = commandText;
        this.isResponseRequired = isResponseRequired;
    }

    public String getCommandId() {
        return commandId;
    }

    @JsonSetter("command_id")
    public void setCommandId(String commandId) {
        this.commandId = commandId;
    }

    public String getTrackerId() {
        return trackerId;
    }

    @JsonSetter("tracker_id")
    public void setTrackerId(String trackerId) {
        this.trackerId = trackerId;
    }

    public String getPhone() {
        return phone;
    }

    @JsonSetter("tracker")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCommandText() {
        return commandText;
    }

    @JsonSetter("command_text")
    public void setCommandText(String commandText) {
        this.commandText = commandText;
    }

    public String getIsResponseRequired() {
        return isResponseRequired;
    }

    @JsonSetter("is_response_required")
    public void setIsResponseRequired(String isResponseRequired) {
        this.isResponseRequired = isResponseRequired;
    }
}
