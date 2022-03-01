import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import main.FileMessage;

@Generated("jsonschema2pojo")
public class RCSMessage {

    @SerializedName("expiry")
    @Expose
    private String expiry;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("fileMessage")
    @Expose
    private FileMessage fileMessage;

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public FileMessage getFileMessage() {
        return fileMessage;
    }

    public void setFileMessage(FileMessage fileMessage) {
        this.fileMessage = fileMessage;
    }

}
