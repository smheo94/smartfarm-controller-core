package com.kt.smartfarm.service.impl;


import com.kt.smartfarm.service.HouseDiaryService;
import com.kt.smartfarm.service.SweetContentVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.Assert.assertTrue;

@Slf4j
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/config/com/kt/smartfarm/springmvc/dispatcher-servlet.xml",
        "file:src/main/resources/com/kt/smartfarm/spring/*.xml"} )
public class ServiceTest {

    @Autowired
    ApplicationContext ctx;

    @Autowired
    HouseDiaryService service;

    @Test
    public void testGetSweetGraph() {
        Long houseId = 1401000000001L;
        String fromDate = LocalDateTime.now().minus(1, ChronoUnit.YEARS).format(DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00"));
        String toDate = LocalDateTime.now().minus(0, ChronoUnit.YEARS).format(DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00"));
//        Long fromDate = DateSystem.currentTimeMillis() - 86400 * 1000 * 365;
//        Long toDate = System.currentTimeMillis();
        HouseDiaryService service = ctx.getBean("houseDiaryService", HouseDiaryService.class);
        List<SweetContentVO> sweetContentGraphList = service.getSweetContentGraphList(null, houseId, fromDate, toDate);
        log.info("Graph : {}, ", sweetContentGraphList.size()  );
        assertTrue(sweetContentGraphList.size() > 0 ) ;
    }
}