# H2 guide

> 매번 까먹어서 기록

## installations

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
