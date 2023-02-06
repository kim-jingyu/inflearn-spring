## SpringMVC를 사용하여 상품 관리 웹 페이지를 만드는 학습 저장소입니다.

## 요구사항 분석
- 상품 도메인 모델
    - 상품 ID (itemId)
    - 상품명 (itemName)
    - 가격 (itemPrice)
    - 수량 (itemQuantity)
- 상품 관리 기능
    - 상품 목록
    - 상품 상세
    - 상품 등록
    - 상품 수정

## Flow Chart - v1
![상품 관리 기능 플로우 차트](https://user-images.githubusercontent.com/73871256/215332772-819ba5be-c31c-41db-b95f-2ef79f21ff3b.png)

## 검증 요구사항
- 타입 검증
  - 가격, 수량에 문자가 들어가면 검증 오류 처리
- 필드 검증
  - 상품명: 필수, 공백없음
  - 가격: 1,000원 이상, 1,000,000원 이하
  - 수량: 최대 9999개
- 특정 필드의 범위를 넘어서는 검증
  - 가격 * 수량의 합은 10,000원 이상