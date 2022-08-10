package com.gzl0ng.securitydemo1.mapper;


import com.gzl0ng.securitydemo1.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author: guozhenglong
 * Date:2022/8/10 11:45
 */
@Repository
public interface UserMapper extends JpaRepository<Users,Long> {

    Users findByUsername(String username);
}
