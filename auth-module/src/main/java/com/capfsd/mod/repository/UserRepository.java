package com.capfsd.mod.repository;

import com.capfsd.mod.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update User u set u.status =:status where id =:id")
    Integer updateUserByIdWithStatus(@Param("id") Long id, @Param("status") Integer status);
}
