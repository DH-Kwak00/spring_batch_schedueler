package com.example.batch.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
//@Setter
//  현업에서는 setter를 거의 사용하지 않는다. 그 대신 생성자 방식을 많이 사용한다.
//  생성자의ㅣ 단점 때문에 생성자와 setter의 장점을 가진 Builder 방식을 가장 많이 쓴다.
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20)
    private String name;
    @Column(length = 50, unique = true)
    private String email;
    private String password;

    private String role;
    private LocalDateTime createDate;

//    @Setter 쓰지 말아야함 간편 방법임
//    @Transient/*컬럼은 만들지 않는 객체 생성*/
//    private int count;

    @OneToMany(mappedBy = "author")
    private List<Post> posts = new ArrayList<>();

////    기본 생성자
//    public Author(){
//
//    }
//    Author  생성자
    @Builder
    public Author(String name, String email, String password, String role, LocalDateTime createDate, int count){
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createDate = LocalDateTime.now();

    }
}
