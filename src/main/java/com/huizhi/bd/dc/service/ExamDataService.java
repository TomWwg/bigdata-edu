package com.huizhi.bd.dc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wwg on 2017/5/4 0004.
 */

/**
 * 考试静态导入的数据存储
 */
@Service
public class ExamDataService {

    private static Logger log = LoggerFactory.getLogger(ExamDataService.class);

    @Value("${collectAddress}")
    private String address;

    /**
     * 从kafka得到数据，调用save方法，将数据写入到txt文件中
     * @param str
     */
    @KafkaListener(topics = "AnalysisTopic")
    public void collectData(String str) {
        if(str != null){
            save(str, address);
        } else {
            log.info("数据为空");
        }
    }

    /**
     * save方法，将数据写入txt
     * @param str
     */
    public void save(String str, String address){
        try {
            Date currentTime = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
            String dateString = formatter.format(currentTime);
            String content = str;
            File dir = new File(address);
            File file = new File(address + "/RZ" + dateString + ".txt");
            //文件夹不存在
            if(!dir.exists() || !dir .isDirectory()){
                //创建文件夹
                dir.mkdirs();
                if (!file.exists()) {
                    file.createNewFile();
                }
            } else {
                //创建文件
                if (!file.exists()) {
                    file.createNewFile();
                }
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
        } catch (IOException e) {
            log.info(e.toString());
        }
    }

}
