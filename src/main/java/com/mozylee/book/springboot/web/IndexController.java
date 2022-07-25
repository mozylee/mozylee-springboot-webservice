package com.mozylee.book.springboot.web;

import com.mozylee.book.springboot.domain.posts.Posts;
import com.mozylee.book.springboot.domain.posts.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsRepository postsService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        Posts posts = postsService.findById(id).orElseThrow(()->new IllegalArgumentException("글이 존재하지 않습니다."));

        model.addAttribute("post", posts);
        return "posts-update";
    }
}
