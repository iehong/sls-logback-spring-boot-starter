# Aliyun SLS Logback Spring Boot Starter

[![Java CI](https://github.com/iehong/sls-logback-spring-boot-starter/actions/workflows/ci.yml/badge.svg)](https://github.com/iehong/sls-logback-spring-boot-starter/actions/workflows/ci.yml)
[![JitPack](https://jitpack.io/v/iehong/sls-logback-spring-boot-starter.svg)](https://jitpack.io/#iehong/sls-logback-spring-boot-starter)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

A Spring Boot Starter for Aliyun SLS (Log Service) Logback Appender with zero XML configuration.

**English** | [中文](README.md)

## Features

- ✅ **Zero XML Configuration** - All settings via `application.yml`
- ✅ **Auto-configuration** - Works automatically when dependency is added
- ✅ **IDE Friendly** - Configuration hints supported via `spring-boot-configuration-processor`
- ✅ **Fully Configurable** - Supports all SLS Appender parameters
- ✅ **Spring Boot 3.x Compatible** - Java 17+

## Quick Start

### 1. Add JitPack Repository

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

### 2. Add Dependency

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

### 3. Configure application.yml

```yaml
aliyun:
  sls:
    enabled: true
    project: my-project
    logstore: app-logs
    endpoint: cn-hangzhou.log.aliyuncs.com
    access-key-id: ${SLS_ACCESS_KEY_ID}
    access-key-secret: ${SLS_ACCESS_KEY_SECRET}
    # optional
    topic: ${spring.application.name}-${spring.profiles.active}
```

### 4. Delete logback-spring.xml

No more `logback-spring.xml` needed! The Starter auto-configures SLS Appender.

## Configuration Properties

| Property | Required | Default | Description |
|----------|----------|---------|-------------|
| `aliyun.sls.enabled` | No | `true` | Enable SLS logging |
| `aliyun.sls.project` | ✅ | - | SLS project name |
| `aliyun.sls.logstore` | ✅ | - | Logstore name |
| `aliyun.sls.endpoint` | ✅ | - | SLS endpoint |
| `aliyun.sls.access-key-id` | ✅ | - | AccessKey ID |
| `aliyun.sls.access-key-secret` | ✅ | - | AccessKey Secret |
| `aliyun.sls.topic` | No | `${app.name}-${profile}` | Log topic |
| `aliyun.sls.source` | No | empty | Log source |
| `aliyun.sls.io-thread-count` | No | `2` | IO thread count |
| `aliyun.sls.batch-count-threshold` | No | `4096` | Max logs per batch |
| `aliyun.sls.batch-size-threshold-in-bytes` | No | `524288` | Max bytes per batch (512KB) |
| `aliyun.sls.linger-ms` | No | `2000` | Send wait time (ms) |
| `aliyun.sls.retries` | No | `10` | Retry count |
| `aliyun.sls.total-size-in-bytes` | No | `104857600` | Max cache size (100MB) |
| `aliyun.sls.max-block-ms` | No | `0` | Max block time (ms) |
| `aliyun.sls.base-retry-backoff-ms` | No | `100` | First retry wait (ms) |
| `aliyun.sls.max-retry-backoff-ms` | No | `50000` | Max retry wait (ms) |

## Disable SLS Logging

For dev/test environments:

```yaml
aliyun:
  sls:
    enabled: false
```

Or via environment variable:

```bash
export ALIYUN_SLS_ENABLED=false
```

## Notes

1. **Log Pattern**: Uses `logging.pattern.console` by default
2. **Environment Isolation**: Use different `topic` for different environments
3. **Sensitive Data**: Store AccessKey in environment variables
4. **Spring Boot Version**: Supports Spring Boot 3.x, Java 17+

## License

[MIT License](LICENSE)