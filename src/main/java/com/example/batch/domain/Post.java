package com.example.batch.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Getter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String title;

    @Column(length = 255)
    private String contents;

    private LocalDateTime createDate;

    @Setter
    private String scheduled;

    private LocalDateTime scheduledTime;

    @Builder
    public Post(Long id, String title, String contents, LocalDateTime createDate, Author author,
                String scheduled, LocalDateTime scheduledTime) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.createDate = LocalDateTime.now();
        this.author = author;
        this.scheduled = scheduled;
        this.scheduledTime = scheduledTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
//    LAZY는 author객체를 사용하는 곳에서만 author객체를 조회해서 가져온다.
//    LAZY를 걸지 않으면 기본타입 eager인데 이건 무조건 참조객체를 조회해서 가져온다.
    @JoinColumn(nullable = false, name = "authorId", referencedColumnName = "id")
    private Author author;
}
