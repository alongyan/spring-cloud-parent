package com.george.remote;

import com.george.remote.fallback.ProducerRemoteFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 *  使用Feign进行远程调用
 *  name ：调用的服务实例名称，如果是集群则进行轮询负载
 *  fallback ：进行降级操作的类
 * </p>
 *
 * @author GeorgeChan 2019/10/2 13:17
 * @version 1.0
 * @since jdk1.8
 */
@FeignClient(name = "eureka-client-producer", fallback = ProducerRemoteFallback.class)
@Service
public interface ProducerRemote {

    /**
     * 调用 eureka-client-producer 服务中，url为/test1 的接口
     * 接口地址、方法名，参数与远程服务接口一致
     * @param name 参数
     * @return 返回结果
     */
    @GetMapping("/test1")
    String test1(@RequestParam(value = "name") String name);
}
