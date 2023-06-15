package hello.upload.domain;


import lombok.Data;

import java.util.List;

/**
 * 상품 도메인
 */
@Data
public class Item {
    private Long id;
    private String itemName;
    private UploadFile attachFile; // 첨부 파일 1개
    private List<UploadFile> imageFiles; // 이미지 파일 여러 개
}
