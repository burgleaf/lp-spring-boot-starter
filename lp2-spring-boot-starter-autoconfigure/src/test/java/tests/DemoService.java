package tests;

import me.binf.component.lp.core.Level;
import me.binf.component.lp.core.LogPrint;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoService {

    @LogPrint(level = Level.DEBUG, value = "你好业务", hasParamLog = true, hasResultLog = true, hasThrowingLog = false)
    public void sayHello(String name, List<String> persons) {
        System.out.println(name + "say" + persons);
    }

}
