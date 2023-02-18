package hello.upload.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 업로드 파일 정보 보관
 */
@Data
@AllArgsConstructor
public class UploadFile {
    private String uploadFileName; // 고객이 업로드한 파일명
    private String storeFileName; // 서버 내부에서 관리하는 파일명
}
