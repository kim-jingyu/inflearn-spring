package hello.springmvc.basic.requestmapping;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mapping/users")
public class MappingClassController {

    /**
     * 회원 목록 조회
     * @return
     */
    @GetMapping
    public String users() {
        return "get users";
    }

    /**
     * 회원 등록
     * @return
     */
    @PostMapping
    public String addUser() {
        return "enroll user";
    }

    /**
     * 회원 조회
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public String findUser(@PathVariable("userId") Long userId) {
        return "find userId=" + userId;
    }

    /**
     * 회원 수정
     * @param userId
     * @return
     */
    @PatchMapping("/{userId}")
    public String editUser(@PathVariable("userId") Long userId) {
        return "edit userId=" + userId;
    }

    /**
     * 회원 삭제
     * @param userId
     * @return
     */
    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable("userId") Long userId) {
        return "delete userId=" + userId;
    }
}
