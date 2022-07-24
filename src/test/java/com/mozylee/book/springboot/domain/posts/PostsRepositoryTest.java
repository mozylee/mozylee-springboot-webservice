package com.mozylee.book.springboot.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @AfterEach
    public void cleanUp() {
        postsRepository.deleteAll();
    }

    @Test
    public void post_save_and_get() {
        //given
        String title = "테스트 제목";
        String content = "테스트 본문";

        // id값이 있다면 update, 없다면 insert 쿼리 실행
        postsRepository.save(
                Posts.builder()
                        .title(title)
                        .content(content)
                        .author("whyalwaysmeyy@gmail.com")
                        .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity() {
        //given
        LocalDateTime now = LocalDateTime.of(2019, 4, 4, 9, 0, 0);
        postsRepository.save(Posts.builder()
                .title("TEST")
                .content("TEST")
                .author("author")
                .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}