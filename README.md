# Aliyun SLS Logback Spring Boot Starter

[![Java CI](https://github.com/iehong/sls-logback-spring-boot-starter/actions/workflows/ci.yml/badge.svg)](https://github.com/iehong/sls-logback-spring-boot-starter/actions/workflows/ci.yml)
[![JitPack](https://jitpack.io/v/iehong/sls-logback-spring-boot-starter.svg)](https://jitpack.io/#iehong/sls-logback-spring-boot-starter)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

简化阿里云 SLS 日志配置的 Spring Boot Starter。无需繁琐的 `logback-spring.xml`，只需几行 YAML 配置即可。

**中文** | [English](README_EN.md)

## 特性

- ✅ **零 XML 配置** - 所有配置通过 `application.yml` 完成
- ✅ **自动装配** - 引入依赖即自动生效
- ✅ **IDE 友好** - 支持配置提示（通过 `spring-boot-configuration-processor`）
- ✅ **可配置** - 支持所有 SLS Appender 参数
- ✅ **版本兼容** - 支持 Spring Boot 3.x & 4.x，Java 17+

## 版本兼容

| Starter 版本 | Spring Boot | JDK | Jakarta EE |
|-------------|-------------|-----|------------|
| 1.x | 3.0.0 - 4.x | 17+ | 10 / 11 |

> **注意**: Spring Boot 3.x 使用 Jakarta EE 10，Spring Boot 4.x 使用 Jakarta EE 11。本 Starter 对两者均兼容。

## 快速开始

### 1. 添加 JitPack 仓库

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

### 2. 添加依赖

```xml
<dependency>
    <groupId>io.github.iehong</groupId>
    <artifactId>sls-logback-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

**Gradle:**

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'io.github.iehong:sls-logback-spring-boot-starter:1.0.0'
}
```

### 3. 配置 application.yml

```yaml
aliyun:
  sls:
    enabled: true
    project: my-project
    logstore: app-logs
    endpoint: cn-hangzhou.log.aliyuncs.com
    access-key-id: ${SLS_ACCESS_KEY_ID}
    access-key-secret: ${SLS_ACCESS_KEY_SECRET}
    # 可选配置
    topic: ${spring.application.name}-${spring.profiles.active}
```

### 4. 删除 logback-spring.xml

不再需要 `logback-spring.xml` 文件！Starter 会自动配置 SLS Appender。

## 完整配置项

| 配置项 | 必填 | 默认值 | 说明 |
|-------|------|-------|------|
| `aliyun.sls.enabled` | 否 | `true` | 是否启用 SLS 日志 |
| `aliyun.sls.project` | ✅ | - | SLS 项目名称 |
| `aliyun.sls.logstore` | ✅ | - | Logstore 名称 |
| `aliyun.sls.endpoint` | ✅ | - | SLS Endpoint |
| `aliyun.sls.access-key-id` | ✅ | - | AccessKey ID |
| `aliyun.sls.access-key-secret` | ✅ | - | AccessKey Secret |
| `aliyun.sls.topic` | 否 | `${app.name}-${profile}` | 日志主题 |
| `aliyun.sls.source` | 否 | 空 | 日志来源 |
| `aliyun.sls.io-thread-count` | 否 | `2` | 发送线程数 |
| `aliyun.sls.batch-count-threshold` | 否 | `4096` | 单次最大日志条数 |
| `aliyun.sls.batch-size-threshold-in-bytes` | 否 | `524288` | 单次最大字节数 (512KB) |
| `aliyun.sls.linger-ms` | 否 | `2000` | 发送等待时间 (ms) |
| `aliyun.sls.retries` | 否 | `10` | 重试次数 |
| `aliyun.sls.total-size-in-bytes` | 否 | `104857600` | 最大缓存大小 (100MB) |
| `aliyun.sls.max-block-ms` | 否 | `0` | 最大阻塞时间 (ms) |
| `aliyun.sls.base-retry-backoff-ms` | 否 | `100` | 首次重试等待时间 (ms) |
| `aliyun.sls.max-retry-backoff-ms` | 否 | `50000` | 最大重试等待时间 (ms) |

## 工作原理

Starter 在 Spring Boot 启动时通过 `@PostConstruct` 动态创建 `LoghubAppender` 并添加到 Root Logger。

```
Spring Boot 启动
    ↓
SlsLogbackAutoConfiguration 被加载
    ↓
@PostConstruct 方法执行
    ↓
动态创建 LoghubAppender
    ↓
添加到 Root Logger
```

## 禁用 SLS 日志

在开发环境或测试环境，可以通过配置禁用：

```yaml
aliyun:
  sls:
    enabled: false
```

或者通过环境变量：

```bash
export ALIYUN_SLS_ENABLED=false
```

## 注意事项

1. **日志格式**: 默认使用 `logging.pattern.console` 配置的格式
2. **环境隔离**: 建议通过 `topic` 区分不同环境的日志
3. **敏感信息**: 推荐使用环境变量存储 AccessKey
4. **版本要求**: 
   - JDK 17+ (推荐 JDK 21 以获得虚拟线程支持)
   - Spring Boot 3.0.0+ 或 4.x

## 与传统方式对比

### 传统方式 (logback-spring.xml)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <springProperty scope="context" name="SLS_PROJECT" source="aliyun.sls.project"/>
    <springProperty scope="context" name="SLS_LOGSTORE" source="aliyun.sls.logstore"/>
    <springProperty scope="context" name="SLS_ENDPOINT" source="aliyun.sls.endpoint"/>
    <springProperty scope="context" name="SLS_ACCESS_KEY_ID" source="aliyun.sls.access-key-id"/>
    <springProperty scope="context" name="SLS_ACCESS_KEY_SECRET" source="aliyun.sls.access-key-secret"/>
    
    <appender name="SLS" class="com.aliyun.openservices.log.logback.LoghubAppender">
        <project>${SLS_PROJECT}</project>
        <logStore>${SLS_LOGSTORE}</logStore>
        <endpoint>${SLS_ENDPOINT}</endpoint>
        <accessKeyId>${SLS_ACCESS_KEY_ID}</accessKeyId>
        <accessKeySecret>${SLS_ACCESS_KEY_SECRET}</accessKeySecret>
        <!-- 20+ 行配置... -->
    </appender>
    
    <root level="INFO">
        <appender-ref ref="SLS"/>
    </root>
</configuration>
```

### 使用本 Starter

```yaml
aliyun:
  sls:
    project: my-project
    logstore: app-logs
    endpoint: cn-hangzhou.log.aliyuncs.com
    access-key-id: ${SLS_ACCESS_KEY_ID}
    access-key-secret: ${SLS_ACCESS_KEY_SECRET}
```

**代码量减少 90%！**

## 开源协议

[MIT License](LICENSE)

## 贡献

欢迎提交 Issue 和 Pull Request！

## 致谢

- [Aliyun SLS Logback Appender](https://github.com/aliyun/aliyun-log-logback-appender)