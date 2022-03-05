package com.wonjjong.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

//보통 MYBATIS에서 Dao라고 불리는 DB Layer 접근자
public interface PostsRepository extends JpaRepository<Posts,Long>{


}
