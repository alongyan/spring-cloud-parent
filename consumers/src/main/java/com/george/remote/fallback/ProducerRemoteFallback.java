package com.george.remote.fallback;

import com.george.remote.ProducerRemote;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  ProducerRemote降级操作
 * </p>
 *
 * @author GeorgeChan 2019/10/2 16:11
 * @version 1.0
 * @since jdk1.8
 */
@Component
public class ProducerRemoteFallback implements ProducerRemote {
    @Override
    public String test1(String name) {
        return "操作异常，进行降级。。。。";
    }
}
