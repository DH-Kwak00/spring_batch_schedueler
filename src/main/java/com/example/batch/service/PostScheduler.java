package com.example.batch.service;

import com.example.batch.repository.PostRepository;
//import com.project.board.author.domain.Post;
//import com.project.board.author.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component /*스프링빈(상시적으로 실행되기 위한)으로 등록하는 어노테이션*/
@Slf4j
public class PostScheduler {

//    batch를 호출하기 위한 launcher 클래스
    private final JobLauncher jobLauncher;
    private final PostBatch postBatch;
    private final PostRepository postRepository;

    public PostScheduler(JobLauncher jobLauncher, PostBatch postBatch, PostRepository postRepository){
        this.jobLauncher = jobLauncher;
        this.postBatch = postBatch;
        this.postRepository = postRepository;
    }

    @Scheduled(cron = "0 0/1 * * * *") // 초 분 시 일 월 요일
    public void postSchedule() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

//         현재 시간을 배치로 넘겨, 배치에서 DB update를 하도록 배치를 호출하는 logic
//         LocalDateTime.now() 대신 system.currentTimeMills()를 사용

        Map<String, JobParameter> mp = new HashMap<>();
//        batch한테 "time"이라는 key로 현재 시간을 value로 하여 넘기는 부분
//        time외에 여러가지 키값을 배치 넘길 수 있다.
        mp.put("test", new JobParameter("test"));
        JobParameters jobParameters = new JobParameters(mp);

//        jobLauncher에 매개변수로 배치 프로그램명과 배치에 넘길 파라미터를 주입
        jobLauncher.run(postBatch.excuteJob(), jobParameters);
    }
}
