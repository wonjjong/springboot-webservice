package com.wonjjong.springboot.service.posts;

import com.wonjjong.springboot.domain.posts.Posts;
import com.wonjjong.springboot.domain.posts.PostsRepository;
import com.wonjjong.springboot.web.dto.PostsListResponseDto;
import com.wonjjong.springboot.web.dto.PostsResponseDto;
import com.wonjjong.springboot.web.dto.PostsSaveReqeustDto;
import com.wonjjong.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/*
1. org.springframework.transaction.annotation.Transactional  --> 옵션을 허용(readonly=true? false) 가능
2. javax.transaction.Transactional --> 옵션을 허용하지 않음..
 */
@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveReqeustDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        postsRepository.delete(posts); // JpaRepository에서 이미 delete 메소드를 지원하고 있으니 이를 활용합니다.
        //존재하는 Posts인지 확인을 위해 엔티티 조회 후 그대로 삭제합니다.

    }


    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new
                IllegalArgumentException("해당 게시글이 없습니다 id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new
                        IllegalArgumentException("해당 게시글이 없습니다 id=" + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

}
