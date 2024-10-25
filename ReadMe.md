# 게시판 관리 시스템

이 프로젝트는 게시판과 게시물 관리를 위한 콘솔 기반 애플리케이션입니다. 사용자는 게시판을 생성, 수정, 삭제할 수 있으며, 각 게시판에는 다수의 게시물을 작성할 수 있습니다. 로그인 기능도 지원하여, 작성자와 작성 시간을 기록해 게시물 작성 이력을 관리할 수 있습니다.

## 주요 기능

- **게시판 관리**: 게시판을 생성하고, 수정하거나 삭제할 수 있습니다.
- **게시물 관리**: 각 게시판에 게시물을 작성하고, 수정, 삭제, 조회할 수 있습니다.
- **회원 관리**: 계정 생성, 로그인, 로그아웃을 통해 사용자별로 게시물을 작성하고 수정할 수 있습니다.
- **URL 기반 명령어 처리**: URL 형식을 사용해 다양한 명령어를 직관적으로 입력할 수 있습니다.

## Troubleshooting

### 1. 문제: 게시판 이름을 기반으로 게시물을 추가하는 로직의 구조 문제
- **문제 발생**  
  게시판 이름을 문자열로 받아 게시물 작성 시 해당 이름을 `Post` 객체에 넘겨주는 방식으로 구현했습니다. 그러나 게시물 삭제, 수정, 조회 시 게시판의 고유 정보와 메서드를 이용하려다 보니 게시판 이름(String)만으로는 불편하고 관리가 어려웠습니다. 또한, 여러 곳에서 동일한 `Board` 이름을 사용하는 경우, 같은 이름을 가진 다른 `Board`에 혼동이 생길 우려가 있었습니다.

- **고민**  
  게시물이 속한 `Board` 객체를 참조하도록 하여 게시물과 게시판 간의 참조를 명확히 해야 한다고 판단했습니다. 이를 통해 게시판의 다양한 속성과 메서드를 직접적으로 활용할 수 있을 것으로 기대했습니다.

- **해결방안 모색 및 적용**  
  `Post` 객체가 `String` 대신 `Board` 객체 자체를 저장하도록 수정했습니다. 이후 `findBoardByName()` 메소드를 추가해 게시판 이름에 해당하는 `Board` 객체를 찾아 `Post` 생성 시 이 객체를 전달하도록 수정했습니다. `Board` 객체가 가진 게시물 리스트에도 게시물을 추가하게 하여 게시물과 게시판의 참조 관계를 명확히 구축했습니다.

- **후기**  
  게시판과 게시물 간의 관계가 객체 참조로 연결되어, 게시판 정보와 관련 메서드를 더욱 쉽게 사용할 수 있었습니다. 이로 인해 코드가 간결해졌고, 중복된 게시판 이름을 가진 객체의 혼동 문제도 해결되었습니다.

---

### 2. 문제: 게시물 작성 시 작성자가 로그인 상태인지 확인하는 문제
- **문제 발생**  
  `Post` 객체 생성 시 작성자 이름을 단순히 파라미터로 입력받아 저장하는 방식으로 구현했으나, 작성자가 로그인 상태인지 여부에 따라 다르게 처리해야 했습니다. 비회원 작성 시 "비회원"으로 표기할 수 있어야 했습니다.

- **고민**  
  `loggedInAccount` 변수를 활용하여 작성자를 설정하고, 로그인 상태가 아니라면 작성자 정보를 "비회원"으로 설정하는 방식을 도입할 필요가 있었습니다.

- **해결방안 모색 및 적용**  
  `Post` 객체 생성 시 `loggedInAccount`가 null인지 확인하여, 로그인된 상태에서는 `loggedInAccount.getName()`을, 로그아웃 상태에서는 "비회원" 문자열을 작성자 정보로 설정했습니다. 이를 통해 각 게시물의 작성자가 로그인 여부에 따라 올바르게 저장되도록 수정했습니다.

- **후기**  
  로그인된 계정에 따라 게시물 작성자가 자동으로 설정되어 코드가 간결해졌고, 모든 게시물에 대해 작성자가 명확하게 구분되었습니다. 회원과 비회원 여부를 간단히 구분하면서도 작성자 정보를 일관되게 적용할 수 있어 효과적이었습니다.

---

### 3. 문제: 유효하지 않은 URL 예외 처리 문제
- **문제 발생**  
  명령어를 URL 형식으로 변환하면서, 사용자가 입력한 URL이 유효하지 않거나 예상하지 못한 파라미터 형식을 가진 경우에 대한 처리가 필요했습니다. 유효하지 않은 URL이 입력되었을 때 프로그램이 예기치 않게 종료되는 경우가 발생하여, 올바른 URL 형식을 강제하고 잘못된 입력을 감지할 수 있는 처리가 필요했습니다.

- **고민**  
  유효하지 않은 URL 형식을 감지하고 사용자에게 오류 메시지를 표시하여 올바른 형식으로 입력하도록 안내할 필요가 있었습니다. 또한, 파라미터가 중복되거나 필요 이상의 파라미터가 있을 경우, 마지막 파라미터 값만 유효하게 처리해야 했습니다.

- **해결방안 모색 및 적용**  
  URL 구문을 분석하고 예외를 처리하기 위해 새로운 `InvalidURLException` 예외를 정의했습니다. 이 예외를 통해 유효하지 않은 URL 형식이 입력되었을 때 이를 감지하여 사용자에게 구체적인 오류 메시지를 제공하도록 했습니다. 파라미터 처리 부분에서는 동일한 이름의 파라미터가 존재할 경우 마지막 값을 사용하도록 로직을 수정했습니다.

- **후기**  
  예외 처리를 통해 프로그램이 예상치 못한 입력에도 안정적으로 동작할 수 있었습니다. 사용자에게는 구체적인 오류 메시지가 제공되어 명령어 형식이 더욱 직관적으로 안내되었습니다. 이로 인해 프로그램의 안정성이 향상되었고, URL 형식 오류 발생 시 디버깅이 훨씬 쉬워졌습니다.
