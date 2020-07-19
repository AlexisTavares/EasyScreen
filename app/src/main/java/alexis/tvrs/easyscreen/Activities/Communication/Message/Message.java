package alexis.tvrs.easyscreen.Activities.Communication.Message;

import java.io.Serializable;

public class Message implements Serializable {
    private String messageFrom;
    private String messageContent;

    public String getMessageFrom() {
        return messageFrom;
    }

    public void setMessageFrom(String messageFrom) {
        this.messageFrom = messageFrom;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
}
