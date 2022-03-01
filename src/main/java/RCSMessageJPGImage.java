import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class RCSMessageJPGImage {

    @SerializedName("RCSMessage")
    @Expose
    private RCSMessage rCSMessage;
    @SerializedName("messageContact")
    @Expose
    private MessageContact messageContact;

    public RCSMessage getRCSMessage() {
        return rCSMessage;
    }

    public void setRCSMessage(RCSMessage rCSMessage) {
        this.rCSMessage = rCSMessage;
    }

    public MessageContact getMessageContact() {
        return messageContact;
    }

    public void setMessageContact(MessageContact messageContact) {
        this.messageContact = messageContact;
    }

}
