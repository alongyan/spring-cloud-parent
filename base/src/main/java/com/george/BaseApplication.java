package com.george;

import com.george.util.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * <p>
 *  启动类
 * </p>
 *
 * @author GeorgeChan 2019/9/28 15:44
 * @version 1.0
 * @since jdk1.8
 */
@SpringBootApplication
public class BaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class);
    }

    /**
     * 注入id生成器
     * @return 实例对象
     */
    @Bean
    public IdWorker idWorker() {
        return new IdWorker(1,1);
    }
}
