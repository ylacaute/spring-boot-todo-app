package com.thorpora.module.user;

import com.thorpora.gateway.core.db.DBCleaner;
import com.thorpora.module.todo.TodoResource;
import com.thorpora.module.todo.domain.Todo;
import com.thorpora.module.todo.web.TodoController;
import com.thorpora.module.todo.web.TodoResourceConverter;
import com.thorpora.test.ITContext;
import com.thorpora.test.TestRestHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.util.List;

@SpringBootTest(
        classes = ITContext.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class SampleIT{

    private final static Logger log = LoggerFactory.getLogger(SampleIT.class);

    @Inject
    protected DBCleaner dbCleaner;

    @Inject
    protected TestRestTemplate restTemplate;
/*
    @Rule
    public TestDecorator sampleRule = new TestDecorator();
*/
    @Before
    public void beforeTest() {
        dbCleaner.clear("users");
    }

    @Test
    public void findAllAndSaveOne() {




        log.info("************************ SAMPLE IT ******************************************");
        log.info("************************ SAMPLE IT ******************************************");
        log.info("************************ SAMPLE IT ******************************************");
        log.info("************************ SAMPLE IT ******************************************");
        log.info("************************ SAMPLE IT ******************************************");
        log.info("************************ SAMPLE IT ******************************************");
    }

}