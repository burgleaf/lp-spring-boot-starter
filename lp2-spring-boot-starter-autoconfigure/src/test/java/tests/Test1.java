package tests;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfiguration.class})
@Slf4j
public class Test1 {

    @Autowired
    private DemoService demoService;

    @Test
    public void test() {
        List<String> list = new ArrayList<>();
        list.add("sss");
        list.add("ddd");
        list.add("ccc");
        demoService.sayHello("wang", list);
    }
}