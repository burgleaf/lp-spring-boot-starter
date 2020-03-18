package me.binf.component.lp.config;

import me.binf.component.lp.core.LogProcessor;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 日志自动装配
 */
@Configuration
@ConditionalOnClass({Logger.class})
public class LogAutoConfiguration {



    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "binf.lp",value = "enabled",havingValue = "true")
    public LogProcessor logProcessor() {
        return new LogProcessor();
    }


}
