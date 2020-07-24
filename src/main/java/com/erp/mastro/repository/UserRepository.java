package com.erp.mastro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.erp.mastro.entities.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Transactional
    @Override
    java.util.Optional<User> findById(Long primaryKey);

    /**
     * Retrieve users by their email address. The finder {@literal User.findByEmail} is declared as annotation at
     * {@code User}.
     *
     * @param email
     * @return the user with the given userName
     */
    User findByEmail(String email);



}
