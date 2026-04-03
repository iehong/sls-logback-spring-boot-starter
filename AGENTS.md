# PROJECT KNOWLEDGE BASE

**Generated:** 2026-04-03
**Commit:** 0d21ebf
**Branch:** main

## OVERVIEW
Spring Boot Starter for Aliyun SLS Logback Appender. Zero XML config - all via YAML.

## STRUCTURE
```
./
├── pom.xml                           # Maven build (Spring Boot 3.4.0, Java 17+)
├── src/main/java/fun/iehong/sls/
│   ├── autoconfigure/
│   │   ├── SlsLogbackAutoConfiguration.java   # Auto-config via @PostConstruct
│   │   └── SlsProperties.java                 # @ConfigurationProperties
│   └── (root package)
└── src/main/resources/META-INF/
    └── spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
```

## WHERE TO LOOK
| Task | Location | Notes |
|------|----------|-------|
| Auto-config logic | `SlsLogbackAutoConfiguration.java` | Creates LoghubAppender in @PostConstruct |
| Config properties | `SlsProperties.java` | @ConfigurationProperties(prefix="aliyun.sls") |
| Config registration | `AutoConfiguration.imports` | Spring Boot 3.x auto-config entry |

## CONVENTIONS
- Package: `fun.iehong.sls.autoconfigure`
- Lombok for boilerplate (@Data, @Slf4j, @RequiredArgsConstructor)
- Spring Boot 3.x style: @AutoConfiguration + AutoConfiguration.imports
- JDK release mode: `<release>17</release>` (not source/target)

## ANTI-PATTERNS (THIS PROJECT)
- Do NOT add logback-spring.xml - config is YAML-only
- Do NOT use slf4j LoggerFactory directly - use @Slf4j

## BUILD
```bash
mvn clean install -DskipTests    # Build without tests
mvn test                         # Run tests
```

**CI:** GitHub Actions tests Java 17, 21 on Ubuntu

## NOTES
- Lombok version must match JDK: 1.18.30 (JDK 17-21), 1.18.36+ (JDK 22+), 1.18.40 (JDK 25+)
- Maven compiler plugin: fork=true + --enable-native-access=ALL-UNNAMED for native libs
