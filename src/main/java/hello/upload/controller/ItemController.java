package hello.upload.controller;

import hello.upload.domain.Item;
import hello.upload.domain.ItemRepository;
import hello.upload.domain.UploadFile;
import hello.upload.file.FileStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;
    private final FileStore fileStore;

    // 상품 등록 폼
    @GetMapping("/item/new")
    public String newForm() {
        return "item-form";
    }

    // 상품 등록 진행
    @PostMapping("/item/new")
    public String saveItem(@ModelAttribute ItemDto form, RedirectAttributes redirectAttributes) throws IOException {

        Item item = new Item();
        item.setItemName(form.getItemName());

        //첨부 파일
        UploadFile attachFile = fileStore.storeFile(form.getAttachFile());
        item.setAttachFile(attachFile);

        //이미지 파일들
        List<UploadFile> imageFiles = fileStore.storeFiles(form.getImageFiles());
        item.setImageFiles(imageFiles);

        // 데이터베이스에 아이템 저장
        itemRepository.save(item);

        redirectAttributes.addAttribute("itemId", item.getId());

        return "redirect:/item/{itemId}";
    }

    // 상품 조회 폼
    @GetMapping("/item/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "item-view";
    }

    /**
     * 첨부파일 다운로드
     * 파일 다운로드 시 권한 체크같은 복잡한 상황까지 가정한다고 생각한다.
     * 이미지 id 를 요청하도록 한다.
      */
    @GetMapping("/attach/{itemId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long itemId) throws MalformedURLException {
        Item item = itemRepository.findById(itemId);
        String uploadFileName = item.getAttachFile().getUploadFileName();
        String storeFileName = item.getAttachFile().getStoreFileName();

        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(storeFileName));

        // 파일 다운로드 시에는 고객이 업로드한 파일 이름으로 다운로드 하는게 좋다.
        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);

        // UTF-8 로 인코딩된 업로드 파일 명을 Content-Disposition 헤더에 attachment; filename="업로드 파일명" 으로 값을 준다.
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }

    // 이미지 소스 보여주기
    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource showImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }
}
