
package main;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class FileMessage {

    @SerializedName("filename")
    @Expose
    private String filename;
    @SerializedName("fileUrl")
    @Expose
    private String fileUrl;
    @SerializedName("fileMIMEType")
    @Expose
    private String fileMIMEType;
    @SerializedName("fileSize")
    @Expose
    private String fileSize;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileMIMEType() {
        return fileMIMEType;
    }

    public void setFileMIMEType(String fileMIMEType) {
        this.fileMIMEType = fileMIMEType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

}
