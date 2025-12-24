package com.example.demo.controller;

import ch.qos.logback.classic.AsyncAppender;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/monitoring")
public class MonitoringController {

    @GetMapping("log-queue")
    public Map<String, Object> getLogQueueStatus() {
        final Map<String, Object> result = new HashMap<>();
        final List<Map<String, Object>> appenders = new ArrayList<>();

        final LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

        for (final Logger logger : loggerContext.getLoggerList()) {
            logger.iteratorForAppenders().forEachRemaining(appender -> {
                if (appender instanceof AsyncAppender asyncAppender) {
                    final Map<String, Object> status = new HashMap<>();

                    status.put("appenderName", appender.getName());
                    status.put("loggerName", logger.getName());
                    status.put("queueSize", asyncAppender.getQueueSize());
                    status.put("currentQueueLength", asyncAppender.getNumberOfElementsInQueue());
                    status.put("remainingCapacity", asyncAppender.getRemainingCapacity());
                    status.put("discardingThreshold", asyncAppender.getDiscardingThreshold());
                    status.put("neverBlock", asyncAppender.isNeverBlock());

                    final int queueSize = asyncAppender.getQueueSize();
                    final int currentLength = asyncAppender.getNumberOfElementsInQueue();
                    final double usagePercent = (currentLength * 100.0) / queueSize;

                    status.put("queueUsagePercent", String.format("%.2f%%", usagePercent));

                    if (usagePercent > 80) {
                        status.put("warning", "Queue is over 80% full!");
                    } else if (usagePercent > 50) {
                        status.put("warning", "Queue is over 50% full");
                    } else {
                        status.put("status", "OK");
                    }

                    appenders.add(status);
                }
            });
        }

        if (appenders.isEmpty()) {
            result.put("error", "No AsyncAppender found");
        } else {
            result.put("asyncAppenders", appenders);
            result.put("totalAppenders", appenders.size());
        }

        return result;
    }
}
