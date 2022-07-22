package com.mozylee.book.springboot.web;

import com.mozylee.book.springboot.domain.posts.Posts;
import com.mozylee.book.springboot.domain.posts.PostsRepository;
import com.mozylee.book.springboot.web.dto.PostsSaveRequestDto;
import com.mozylee.book.springboot.web.dto.PostsUpdateRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    public void tearDown() {
        postsRepository.deleteAll();
    }

    @Test
    void save() {
        //given
        String title = "TITLE";
        String content = "CONTENT";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("AUTHOR")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        Assertions.assertThat(all.get(0).getTitle()).isEqualTo(title);
        Assertions.assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    void update() {
        //given
        String beforeTitle = "TITLE";
        String beforeContent = "CONTENT";
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title(beforeTitle)
                .content(beforeContent)
                .author("AUTHOR")
                .build());

        Long updateId = savedPosts.getId();
        String afterTitle = "TITLE2";
        String afterContent = "CONTENT2";
        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(afterTitle)
                .content(afterContent)
                .build();
        String url = "http://localhost:" + port + "/api/v1/posts" + updateId;
        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        //then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        Assertions.assertThat(all.get(0).getTitle()).isEqualTo(afterTitle);
        Assertions.assertThat(all.get(0).getContent()).isEqualTo(afterContent);

    }

    @Test
    void findById() {
    }
}