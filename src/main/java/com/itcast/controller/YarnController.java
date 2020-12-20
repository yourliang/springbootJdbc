package com.itcast.controller;


import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.api.records.ApplicationReport;
import org.apache.hadoop.yarn.api.records.YarnApplicationState;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.exceptions.YarnException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.util.EnumSet;
import java.util.List;

@Controller
@RequestMapping("/yarn")
@Slf4j
public class YarnController {

    @RequestMapping("/yarnStatus")
    @ResponseBody
    public String yarnStatus() throws Exception {
        Configuration conf = new YarnConfiguration();
        YarnClient yarnClient = YarnClient.createYarnClient();
        yarnClient.init(conf);
        yarnClient.start();
        try {
            //查询运行状态所有任务
            List<ApplicationReport> applicationReportList = yarnClient.getApplications(EnumSet.of(YarnApplicationState.FINISHED,YarnApplicationState.FAILED,YarnApplicationState.KILLED));
            for (ApplicationReport applicationReport : applicationReportList) {
                // 获取Name
                String name = applicationReport.getName();
                // 获取ApplicationType
                String applicationType = applicationReport.getApplicationType();
                log.info("-----------applicationId:{}\r\n" +
                        "name:{}\r\n" +
                        "quene:{}\r\n" +
                        "user:{}\r\n" +
                        "type:{}\r\n" +
                        "progress:{}\r\n" +
                        "finishTime:{}\r\n" +
                        "startTime:{}\r\n" +
                        "status:{}\r\n",
                        applicationReport.getApplicationId(),
                        applicationReport.getName(),
                        applicationReport.getQueue(),
                        applicationReport.getUser(),
                        applicationReport.getApplicationType(),
                        applicationReport.getProgress(),
                        applicationReport.getFinishTime(),
                        applicationReport.getStartTime(),
                        applicationReport.getFinalApplicationStatus());

                // 检查是否实时集成任务（Apache Spark）
               /* if (name.equals(applicationName)) {
                    if ("Apache Spark".equalsIgnoreCase(applicationType) || "Spark".equalsIgnoreCase(applicationType)) {
                        // ApplicationId
                        applicationId = applicationReport.getApplicationId();
                        // Kill applicationId
                        //yarnClient.killApplication(applicationId);
                        log.info("==========applicationId:{} is killed!", applicationName);
                        break;
                    } else {
                        log.warn("The app {} is not valid spark job! ", applicationName);
                    }
                }*/
            }
        } catch (YarnException | IOException e) {
            log.error("kill killYarnApplication error:", e);
            throw new Exception("停止Yarn任务失败!");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (yarnClient != null) {
                try {
                    // 停止YarnClient
                    yarnClient.stop();
                    // 关闭YarnClient
                    yarnClient.close();
                } catch (IOException e) {
                    log.error("关闭Yarn Client失败！", e);
                }
            }
        }
        return "yarn";
    }


    @RequestMapping("/yarnLog")
    @ResponseBody
    public String yarnLog() throws Exception {
        Process process2 = null;
        BufferedReader in = null;
        BufferedReader in1 = null;
        try {
            String[] cmds = {"sh  /home/hadoop/test.sh 'application_1608505581941_0001'"};
            process2 = Runtime.getRuntime().exec("/home/hadoop/test.sh 'application_1608505581941_0001'");
            process2.waitFor();
            in = new BufferedReader(new InputStreamReader(process2.getErrorStream()));
            in1 = new BufferedReader(new InputStreamReader(process2.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            while ((line = in1.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("shell执行异常");
        } finally {

            if (process2 != null) {
                process2.destroy();
            }

            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            if (in1 != null) {
                try {
                    in1.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
