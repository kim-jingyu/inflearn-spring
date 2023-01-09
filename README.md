# 스프링 MVC 1편

## 🚀 서블릿, JSP, MVC 패턴
- 회원 관리 웹 애플리케이션 요구사항
  - 회원 정보
    1. 이름 : username
    2. 나이 : age
  - 기능 요구사항
    1. 회원 저장
    2. 회원 목록 조회

## V1
![img.png](img.png)
- ControllerV1
  - 서블릿과 비슷한 모양의 컨트롤러 인터페이스를 도입한다.
  - 각 컨트롤러들은 이 인터페이스를 구현하면 된다.
  - 프론트 컨트롤러는 이 인터페이스를 호출해서 구현과 관계없이 로직의 일관성을 가져갈 수 있다.
- Controller 구현체
  - MemberFormControllerV1 - 회원 등록 컨트롤러
  - MemberSaveControllerV1 - 회원 저장 컨트롤러
  - MemberListControllerV1 - 회원 목록 컨트롤러
- FrontControllerServletV1 - 프론트 컨트롤러
  - urlPatterns = "/front-controller/v1/*"
    - /front-controller/v1 을 포함한 하위 모든 요청을 이 서블릿에서 받아들인다.
    - controllerMap
      - key: 매핑 URL
      - value: 호출될 컨트롤러

## V2
![img_1.png](img_1.png)
- 모든 컨트롤러에서 뷰로 이동하는 부분에 중복이 있고, 깔끔하지 않다.
  - 이 부분을 깔끔하게 분리하기 위해 별도로 뷰를 처리하는 객체(MyView)를 만든다.
- 각 컨트롤러가 복잡한 dispatcher.forward()를 직접 생성해서 호출하지 않아도 된다.
  - 단순히 MyView 객체를 생성하고 거기에 뷰 이름만 넣고 반환하면 된다.
- 컨트롤러 interface의 반환 타입은 MyView이다.
  - 따라서 프론트 컨트롤러는 호출 결과로 MyView를 반환받는다.
    - 그리고 view.render()를 호출하면 forward 로직을 수행해서 JSP가 실행된다.