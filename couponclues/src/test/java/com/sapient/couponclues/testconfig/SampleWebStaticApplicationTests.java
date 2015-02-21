package com.sapient.couponclues.testconfig;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.sapient.couponclues.config.SampleWebStaticApplication;

/**
 * Basic integration tests for demo application.
 * 
 * @author Dave Syer
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SampleWebStaticApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port=8888")
@DirtiesContext
public class SampleWebStaticApplicationTests {

    @Value("${local.server.port}")
    private String port = "0";

    @Test
    public void testCss() throws Exception {

        ResponseEntity<String> entity = new TestRestTemplate().getForEntity("http://localhost:" + this.port + "/webjars/bootstrap/3.0.3/css/bootstrap.min.css", String.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertTrue("Wrong body:\n" + entity.getBody(), entity.getBody().contains("body"));
        assertEquals("Wrong content type:\n" + entity.getHeaders().getContentType(), MediaType.valueOf("text/css;charset=UTF-8"), entity.getHeaders().getContentType());
    }

    @Test
    public void testHome() throws Exception {

        ResponseEntity<String> entity = new TestRestTemplate().getForEntity("http://localhost:" + this.port, String.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertTrue("Wrong body (title doesn't match):\n" + entity.getBody(), entity.getBody().contains("<title>Static"));
    }

}