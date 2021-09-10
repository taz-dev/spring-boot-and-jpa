package com.tazdev.myhome.controller;

import com.querydsl.core.types.Predicate;
import com.tazdev.myhome.model.Board;
import com.tazdev.myhome.model.User;
import com.tazdev.myhome.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
class UserApiController {

    @Autowired
    private UserRepository repository;

    @GetMapping("/users")
    List<User> all(@RequestParam(required = false) String method, @RequestParam(required = false) String text){
        List<User> users = null;

        if("query".equals(method)){
            users = repository.findByUsernameQuery(text);

        }else if("nativeQuery".equals(method)){
            users = repository.findByUsernameNativeQuery(text);

        }else if("querydsl".equals(method)){
//            QCustomer customer = QCustomer.customer;
//            Predicate predicate = user.firstname.equalsIgnoreCase("dave")
//                    .and(user.lastname.startsWithIgnoreCase("mathews"));
//            repository.findAll(predicate);

        }else{
            users = repository.findAll();
        }
        return users;
    }

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    //게시글 한개 불러오기
    @GetMapping("/users/{id}")
    User one(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {

        return repository.findById(id)
                .map(user -> {
                    //user.setTitle(newUser.getTitle());
                    //user.setContent(newUser.getContent());
                    //user.setBoards(newUser.getBoards());
                    user.getBoards().clear(); //기존데이터 삭제
                    user.getBoards().addAll(newUser.getBoards());
                    for(Board board : user.getBoards()){
                        board.setUser(user);
                    }
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
