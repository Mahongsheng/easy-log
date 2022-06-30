# EasyLog

基于SpringAOP实现的日志打印工具。

## 引入项目

```xml
<dependency>
    <groupId>com.github.hanselma</groupId>
    <artifactId>easy-log</artifactId>
    <version>1.0.0</version>
</dependency>
```

## 自定义收集器

通过集成`LogRecorder`来实现自己的收集器：

```java
@Component
public class MyRecorder implements LogRecorder {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(SLF4JLogger.class);

    @Override
    public void record(RecordData logData) {
        try {
            logger.info(objectMapper.writeValueAsString(logData));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
```

## 通过注解埋点收集

```java
@RestController
public class MyController {

    @EasyLog(operationType = "测试", stackTraceOnErr = true, recorder = MyRecorder.class)
    @GetMapping("/app/sayHello")
    public String sayHello() {
        EasyLogData.step("1. 第一步执行完成");
        EasyLogData.step("2. 第二步执行完成");
        EasyLogData.step("3. service的方法执行完成");
        return "hello";
    }
}
```

## 注解参数

| 选项            | 类型                         | 说明                                                         | 默认                                  |
| --------------- | ---------------------------- | ------------------------------------------------------------ | ------------------------------------- |
| operationType   | String                       | 本次记录的类型                                               | undefined                             |
| recordOnError   | boolean                      | 仅当发生异常时才收集                                         | false                                 |
| stackTraceOnErr | boolean                      | 当目标方法发生异常时,是否追加异常堆栈信息到LogData的content中 | false                                 |
| asyncMode       | boolean                      | 异步方式收集                                                 | true                                  |
| recorder        | Class<? extends LogRecorder> | 指定日志收集器                                               | 默认不调整收集器,使用全局的日志收集器 |

