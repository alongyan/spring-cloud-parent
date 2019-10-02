package com.george.remote;

import com.george.remote.fallback.ProducerRemoteFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 *  使用Feign进行远程调用
 * </p>
 *
 * @author GeorgeChan 2019/10/2 13:17
 * @version 1.0
 * @since jdk1.8
 */
@FeignClient(name = "eureka-client-producer", fallback = ProducerRemoteFallback.class)
@Service
public interface ProducerRemote {

    @GetMapping("/test1")
    String test1(@RequestParam(value = "name") String name);
}
