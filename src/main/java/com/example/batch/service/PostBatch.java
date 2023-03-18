package com.example.batch.service;

import com.example.batch.domain.Post;
import com.example.batch.repository.PostRepository;
import lombok.Builder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration // 스프링빈을 등록하기 위한 어노테이션
public class PostBatch {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final PostRepository postRepository;

    public PostBatch(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, PostRepository postRepository) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.postRepository = postRepository;
    }

    public Job excuteJob(){
        return jobBuilderFactory.get("excuteJob").start(firstStep()).build();
    }

//    batch에 모든 step이 정상 완료 되어야 commit이 된다.
//    step 중에 한 곳이라도 예외(에러)가 발생하면 rollback이 된다. (중요!!)
//    왜 따로 서버를 달리해 batch를 띄운 이유
    @Bean
    public Step firstStep(){
        return stepBuilderFactory.get("firstStep")
                .tasklet((contribution, chunkContext) -> {
                    List<Post> posts = postRepository.findByScheduled("checked");
                    for(Post p : posts){
                        if (p.getScheduledTime().isBefore(LocalDateTime.now())){
                            p.setScheduled(null);
                            postRepository.save(p);
                        }
                    }
                    return RepeatStatus.FINISHED;

                }).build();
    }




}
