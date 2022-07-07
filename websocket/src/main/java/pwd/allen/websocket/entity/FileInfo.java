package pwd.allen.websocket.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author pwdan
 * @create 2022-07-07 9:41
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileInfo {
    private String filePath;
    private String name;
    //    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    private Long fileSize;
    private Boolean isFile;
}
