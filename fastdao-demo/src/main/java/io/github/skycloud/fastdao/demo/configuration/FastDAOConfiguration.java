/**
 * @(#)FastDAOCOnfig.java, 10月 31, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
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
        FastDaoConfig.setMapUnderscoreToCamelCase(true);
    }
}