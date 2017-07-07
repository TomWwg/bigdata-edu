package com.huizhi.bd.dc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Created by wwg on 2017/5/5 0005.
 */

/**
 * 将睿智传过来的数据存起来
 */
@Service
public class RZDataService {

    private static Logger log = LoggerFactory.getLogger(ExamDataService.class);

    @Value("${RZDataAddress}")
    private String address;

    /**
     * 从kafka得到数据，调用save方法，将数据写入到txt文件中
     * @param str
     */
    @KafkaListener(topics = "oftenDataTopic")
    public void collectData(String str) {
        ExamDataService eds = new ExamDataService();
        if(str != null){
            eds.save(str, address);
        } else {
            log.info("数据为空");
        }
    }

}
