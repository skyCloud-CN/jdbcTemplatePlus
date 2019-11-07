/**
 * @(#)FastDAOCOnfig.java, 10月 31, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.demo.configuration;

import io.github.skycloud.fastdao.core.FastDaoConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yuntian
 */
@Configuration
public class FastDAOConfiguration {
    @Bean
    public void config(){
        FastDaoConfig.getConfig().setMapUnderscoreToCamelCase(true);
    }
}