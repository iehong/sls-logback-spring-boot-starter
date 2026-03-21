package fun.iehong.sls.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Aliyun SLS Logback 配置属性
 * <p>
 * 在 application.yml 中配置:
 * <pre>
 * aliyun:
 *   sls:
 *     enabled: true
 *     project: my-project
 *     logstore: app-logs
 *     endpoint: cn-hangzhou.log.aliyuncs.com
 *     access-key-id: ${SLS_ACCESS_KEY_ID}
 *     access-key-secret: ${SLS_ACCESS_KEY_SECRET}
 * </pre>
 *
 * @author iehong
 */
@Data
@ConfigurationProperties(prefix = "aliyun.sls")
public class SlsProperties {

    /**
     * 是否启用 SLS 日志上报
     */
    private boolean enabled = true;

    /**
     * SLS 项目名称
     */
    private String project;

    /**
     * SLS Logstore 名称
     */
    private String logstore;

    /**
     * SLS Endpoint，如 cn-hangzhou.log.aliyuncs.com
     */
    private String endpoint;

    /**
     * 阿里云 AccessKey ID
     */
    private String accessKeyId;

    /**
     * 阿里云 AccessKey Secret
     */
    private String accessKeySecret;

    // ============ 可选配置 ============

    /**
     * 日志主题，默认使用 ${spring.application.name}-${spring.profiles.active}
     */
    private String topic;

    /**
     * 日志来源
     */
    private String source;

    /**
     * 发送线程数，默认 2
     */
    private int ioThreadCount = 2;

    /**
     * 单次请求最大日志条数，默认 4096
     */
    private int batchCountThreshold = 4096;

    /**
     * 单次请求最大字节数，默认 512KB
     */
    private int batchSizeThresholdInBytes = 524288;

    /**
     * 发送等待时间 (ms)，默认 2000
     */
    private int lingerMs = 2000;

    /**
     * 重试次数，默认 10
     */
    private int retries = 10;

    /**
     * 最大缓存大小 (bytes)，默认 100MB
     */
    private int totalSizeInBytes = 104857600;

    /**
     * 最大阻塞时间 (ms)，0 表示不阻塞
     */
    private int maxBlockMs = 0;

    /**
     * 首次重试等待时间 (ms)，默认 100
     */
    private int baseRetryBackoffMs = 100;

    /**
     * 最大重试等待时间 (ms)，默认 50000
     */
    private int maxRetryBackoffMs = 50000;
}