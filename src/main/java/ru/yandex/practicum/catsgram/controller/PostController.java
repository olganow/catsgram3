package ru.yandex.practicum.catsgram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.List;


@RestController
public class PostController {
    // создаём логер
    private static final Logger log = LoggerFactory.getLogger(SimpleController.class);
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    //Дополните метод findAll класса-контроллера PostController параметрами запроса: sort — строка со значениями asc
    // (от англ. ascending, «восходящий» — по возрастанию) или desc (от англ. descending, «нисходящий» — по убыванию),
    // page и size — целочисленные, size — больше нуля.
    @GetMapping("/posts")
    public List<Post> findAll(
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
            @RequestParam(value = "sort", defaultValue = "desc", required = false) String sort) {


        if(!(sort.equals("asc") || sort.equals("desc"))){
            throw new IllegalArgumentException();
        }
        if(page < 0 || size <= 0){
            throw new IllegalArgumentException();
        }

        Integer from = page * size;
        return postService.findAll(size, from, sort);
    }
    @PostMapping(value = "/post")
    public Post create(@RequestBody Post post) {
        return postService.create(post);
    }

    @GetMapping("/post/{postId}")
    public Post findPost(@PathVariable("postId") Integer postId){
        return postService.findPostById(postId);
    }
}