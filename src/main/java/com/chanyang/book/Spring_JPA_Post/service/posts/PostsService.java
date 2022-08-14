package com.chanyang.book.Spring_JPA_Post.service.posts;

import com.chanyang.book.Spring_JPA_Post.domain.posts.Posts;
import com.chanyang.book.Spring_JPA_Post.domain.posts.PostsRepository;
import com.chanyang.book.Spring_JPA_Post.web.dto.PostsListResponseDto;
import com.chanyang.book.Spring_JPA_Post.web.dto.PostsResponseDto;
import com.chanyang.book.Spring_JPA_Post.web.dto.PostsSaveRequestDto;
import com.chanyang.book.Spring_JPA_Post.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = "+id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById (Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = "+id));
        return new PostsResponseDto(entity);
    }

    @Transactional
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findALLDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" +id));

        postsRepository.delete(posts);
    }
}
