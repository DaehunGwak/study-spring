package io.ordi.boilerplate.bootjpah2test.repository;

import io.ordi.boilerplate.bootjpah2test.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
