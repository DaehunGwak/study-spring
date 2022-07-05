# spring boot + jpa + h2 + test boilerplate

## explicit versions

- spring boot: 2.7.1
- h2database: 2.1.214

## issues

- h2 2 버전 대에서 `user` 테이블은 이미 존재
  - `@Table(name = "users")` 로 대체
