package tests;


import me.binf.component.lp.core.LogProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan("tests")
public class RootConfiguration {

    @Bean
    public LogProcessor logProcessor(){
        return new LogProcessor();
    }



}
