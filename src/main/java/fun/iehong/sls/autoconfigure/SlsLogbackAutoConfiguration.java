package fun.iehong.sls.autoconfigure;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.aliyun.openservices.log.logback.LoghubAppender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import jakarta.annotation.PostConstruct;

/**
 * Aliyun SLS Logback 自动配置
 * <p>
 * 在 Spring Boot 应用启动时自动配置 LoghubAppender，
 * 无需繁琐的 logback-spring.xml 配置。
 *
 * @author iehong
 */
@Slf4j
@AutoConfiguration
@RequiredArgsConstructor
@ConditionalOnClass({LoggerContext.class, LoghubAppender.class})
@ConditionalOnProperty(prefix = "aliyun.sls", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(SlsProperties.class)
public class SlsLogbackAutoConfiguration {

    private final SlsProperties properties;

    @Value("${spring.application.name:application}")
    private String applicationName;

    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    @Value("${logging.pattern.console:%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n}")
    private String consolePattern;

    /**
     * 配置 SLS LoghubAppender 并添加到 Root Logger
     */
    @PostConstruct
    public void configureSlsAppender() {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

        // 创建 SLS Appender
        LoghubAppender<ILoggingEvent> appender = new LoghubAppender<>();
        appender.setContext(loggerContext);
        appender.setName("SLS");

        // 必填配置
        appender.setProject(properties.getProject());
        appender.setLogStore(properties.getLogstore());
        appender.setEndpoint(properties.getEndpoint());
        appender.setAccessKeyId(properties.getAccessKeyId());
        appender.setAccessKeySecret(properties.getAccessKeySecret());

        // 可选配置
        String topic = properties.getTopic();
        if (topic == null || topic.isBlank()) {
            topic = applicationName + "-" + activeProfile;
        }
        appender.setTopic(topic);
        appender.setSource(properties.getSource() != null ? properties.getSource() : "");

        // 性能配置
        appender.setTotalSizeInBytes(properties.getTotalSizeInBytes());
        appender.setMaxBlockMs(properties.getMaxBlockMs());
        appender.setIoThreadCount(properties.getIoThreadCount());
        appender.setBatchSizeThresholdInBytes(properties.getBatchSizeThresholdInBytes());
        appender.setBatchCountThreshold(properties.getBatchCountThreshold());
        appender.setLingerMs(properties.getLingerMs());
        appender.setRetries(properties.getRetries());
        appender.setBaseRetryBackoffMs(properties.getBaseRetryBackoffMs());
        appender.setMaxRetryBackoffMs(properties.getMaxRetryBackoffMs());

        // 创建 Encoder
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(loggerContext);
        encoder.setPattern(consolePattern);
        encoder.start();
        appender.setEncoder(encoder);

        // 启动 Appender
        appender.start();

        // 添加到 Root Logger
        Logger rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.addAppender(appender);

        log.info("SLS Logback Appender configured: project={}, logstore={}, topic={}",
                properties.getProject(), properties.getLogstore(), topic);
    }
}