# H2 guide

> 매번 까먹어서 기록

## installations

### mac

1. 아래 커맨드 실행

```shell
cd <h2 directory>/bin
chmod 755 h2.sh
./h2.sh
```

2. 팝업뜨는 것에서 url ip 부분을 `localhost` 로 바꿔 접속
3. JDBC URL 부분을 `jdbc:h2:~/querydsl` 로 바꾸어 h2 db 파일 생성
4. `~/querydsl.mv.db` 파일 생성 확인
5. 접속 확인후 뒤로가서, JDBC URL 에 `jdbc:h2:tcp://localhost/~/querydsl` 로 tcp 모드로 접속

### windows

> reference: [인프런 루시님 답변](https://www.inflearn.com/questions/53961)

1. `cd "C:\Program Files (x86)\H2\bin"` 이동
2. `chmod 755 h2.sh` 실행
3. `vim ./h2.sh` 열기
4. 경로 구분자 `:` 를 `;` 로 바꾸기
5. `./h2.sh` 실행
6. `localhost:8082` 접속
7. 나머진 맥의 3번부터 따라하기
