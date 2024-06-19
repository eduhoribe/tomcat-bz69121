# Tomcat BugZilla 69121

A sample Spring Boot project to demonstrate a possible bug on Apache Tomcat 9.0.89 (see [bz 69121](https://bz.apache.org/bugzilla/show_bug.cgi?id=69121))

## Logs

### Tomcat 9.0.88

```log
2024-06-16 13:23:21.084 ERROR 128671 --- [   scheduling-1] com.github.eduhoribe.SseController       : IO error to send heartbeat to 0f098471-5958-4b57-9240-20edbb1dcfd2
2024-06-16 13:23:21.084 ERROR 128671 --- [nio-8080-exec-2] com.github.eduhoribe.SseController       : onError: IOException: Broken pipe
2024-06-16 13:23:21.086  INFO 128671 --- [nio-8080-exec-2] com.github.eduhoribe.SseController       : onCompletion
```

### Tomcat 9.0.89

> [!IMPORTANT]
> Also note that the `onCompletion` log is not present anymore

<details>
<summary>Logs</summary>

```log
2024-06-16 22:36:22.297 ERROR 150523 --- [   scheduling-1] com.github.eduhoribe.SseController       : IO error to send sse heartbeat to 7d85309d-9f62-45d3-b643-85485b9153fa
2024-06-16 22:36:22.298 ERROR 150523 --- [nio-8080-exec-2] com.github.eduhoribe.SseController       : onError: IOException: Broken pipe
2024-06-16 22:36:22.308 ERROR 150523 --- [nio-8080-exec-2] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] threw exception

java.io.IOException: Broken pipe
	at java.base/sun.nio.ch.SocketDispatcher.write0(Native Method) ~[na:na]
	at java.base/sun.nio.ch.SocketDispatcher.write(SocketDispatcher.java:62) ~[na:na]
	at java.base/sun.nio.ch.IOUtil.writeFromNativeBuffer(IOUtil.java:137) ~[na:na]
	at java.base/sun.nio.ch.IOUtil.write(IOUtil.java:102) ~[na:na]
	at java.base/sun.nio.ch.IOUtil.write(IOUtil.java:58) ~[na:na]
	at java.base/sun.nio.ch.SocketChannelImpl.write(SocketChannelImpl.java:544) ~[na:na]
	at org.apache.tomcat.util.net.NioChannel.write(NioChannel.java:140) ~[tomcat-embed-core-9.0.89.jar:9.0.89]
	at org.apache.tomcat.util.net.NioEndpoint$NioSocketWrapper.doWrite(NioEndpoint.java:1428) ~[tomcat-embed-core-9.0.89.jar:9.0.89]
	at org.apache.tomcat.util.net.SocketWrapperBase.doWrite(SocketWrapperBase.java:775) ~[tomcat-embed-core-9.0.89.jar:9.0.89]
	at org.apache.tomcat.util.net.SocketWrapperBase.flushBlocking(SocketWrapperBase.java:739) ~[tomcat-embed-core-9.0.89.jar:9.0.89]
	at org.apache.tomcat.util.net.SocketWrapperBase.flush(SocketWrapperBase.java:723) ~[tomcat-embed-core-9.0.89.jar:9.0.89]
	at org.apache.coyote.http11.Http11OutputBuffer$SocketOutputBuffer.flush(Http11OutputBuffer.java:559) ~[tomcat-embed-core-9.0.89.jar:9.0.89]
	at org.apache.coyote.http11.filters.ChunkedOutputFilter.flush(ChunkedOutputFilter.java:157) ~[tomcat-embed-core-9.0.89.jar:9.0.89]
	at org.apache.coyote.http11.Http11OutputBuffer.flush(Http11OutputBuffer.java:216) ~[tomcat-embed-core-9.0.89.jar:9.0.89]
	at org.apache.coyote.http11.Http11Processor.flush(Http11Processor.java:1243) ~[tomcat-embed-core-9.0.89.jar:9.0.89]
	at org.apache.coyote.AbstractProcessor.action(AbstractProcessor.java:399) ~[tomcat-embed-core-9.0.89.jar:9.0.89]
	at org.apache.coyote.Response.action(Response.java:207) ~[tomcat-embed-core-9.0.89.jar:9.0.89]
	at org.apache.catalina.connector.OutputBuffer.doFlush(OutputBuffer.java:303) ~[tomcat-embed-core-9.0.89.jar:9.0.89]
	at org.apache.catalina.connector.OutputBuffer.flush(OutputBuffer.java:269) ~[tomcat-embed-core-9.0.89.jar:9.0.89]
	at org.apache.catalina.connector.CoyoteOutputStream.flush(CoyoteOutputStream.java:136) ~[tomcat-embed-core-9.0.89.jar:9.0.89]
	at org.springframework.web.context.request.async.StandardServletAsyncWebRequest$LifecycleServletOutputStream.flush(StandardServletAsyncWebRequest.java:389) ~[spring-web-5.3.37.jar:5.3.37]
	at java.base/sun.nio.cs.StreamEncoder.implFlush(StreamEncoder.java:412) ~[na:na]
	at java.base/sun.nio.cs.StreamEncoder.lockedFlush(StreamEncoder.java:214) ~[na:na]
	at java.base/sun.nio.cs.StreamEncoder.flush(StreamEncoder.java:201) ~[na:na]
	at java.base/java.io.OutputStreamWriter.flush(OutputStreamWriter.java:262) ~[na:na]
	at org.springframework.util.StreamUtils.copy(StreamUtils.java:148) ~[spring-core-5.3.37.jar:5.3.37]
	at org.springframework.http.converter.StringHttpMessageConverter.writeInternal(StringHttpMessageConverter.java:125) ~[spring-web-5.3.37.jar:5.3.37]
	at org.springframework.http.converter.StringHttpMessageConverter.writeInternal(StringHttpMessageConverter.java:44) ~[spring-web-5.3.37.jar:5.3.37]
	at org.springframework.http.converter.AbstractHttpMessageConverter.write(AbstractHttpMessageConverter.java:227) ~[spring-web-5.3.37.jar:5.3.37]
	at org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitterReturnValueHandler$HttpMessageConvertingHandler.sendInternal(ResponseBodyEmitterReturnValueHandler.java:212) ~[spring-webmvc-5.3.37.jar:5.3.37]
	at org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitterReturnValueHandler$HttpMessageConvertingHandler.send(ResponseBodyEmitterReturnValueHandler.java:205) ~[spring-webmvc-5.3.37.jar:5.3.37]
	at org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter.sendInternal(ResponseBodyEmitter.java:204) ~[spring-webmvc-5.3.37.jar:5.3.37]
	at org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter.send(ResponseBodyEmitter.java:198) ~[spring-webmvc-5.3.37.jar:5.3.37]
	at org.springframework.web.servlet.mvc.method.annotation.SseEmitter.send(SseEmitter.java:127) ~[spring-webmvc-5.3.37.jar:5.3.37]
	at com.github.eduhoribe.SseController.sseHeartbeat(SseController.kt:58) ~[classes/:na]
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103) ~[na:na]
	at java.base/java.lang.reflect.Method.invoke(Method.java:580) ~[na:na]
	at org.springframework.scheduling.support.ScheduledMethodRunnable.run(ScheduledMethodRunnable.java:84) ~[spring-context-5.3.37.jar:5.3.37]
	at org.springframework.scheduling.support.DelegatingErrorHandlingRunnable.run(DelegatingErrorHandlingRunnable.java:54) ~[spring-context-5.3.37.jar:5.3.37]
	at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:572) ~[na:na]
	at java.base/java.util.concurrent.FutureTask.runAndReset$$$capture(FutureTask.java:358) ~[na:na]
	at java.base/java.util.concurrent.FutureTask.runAndReset(FutureTask.java) ~[na:na]
	at java.base/java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:305) ~[na:na]
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1144) ~[na:na]
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:642) ~[na:na]
	at java.base/java.lang.Thread.run(Thread.java:1583) ~[na:na]

2024-06-16 22:36:22.308 ERROR 150523 --- [nio-8080-exec-2] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception

java.io.IOException: Broken pipe
	at java.base/sun.nio.ch.SocketDispatcher.write0(Native Method) ~[na:na]
	at java.base/sun.nio.ch.SocketDispatcher.write(SocketDispatcher.java:62) ~[na:na]
	at java.base/sun.nio.ch.IOUtil.writeFromNativeBuffer(IOUtil.java:137) ~[na:na]
	at java.base/sun.nio.ch.IOUtil.write(IOUtil.java:102) ~[na:na]
	at java.base/sun.nio.ch.IOUtil.write(IOUtil.java:58) ~[na:na]
	at java.base/sun.nio.ch.SocketChannelImpl.write(SocketChannelImpl.java:544) ~[na:na]
	at org.apache.tomcat.util.net.NioChannel.write(NioChannel.java:140) ~[tomcat-embed-core-9.0.89.jar:9.0.89]
	at org.apache.tomcat.util.net.NioEndpoint$NioSocketWrapper.doWrite(NioEndpoint.java:1428) ~[tomcat-embed-core-9.0.89.jar:9.0.89]
	at org.apache.tomcat.util.net.SocketWrapperBase.doWrite(SocketWrapperBase.java:775) ~[tomcat-embed-core-9.0.89.jar:9.0.89]
	at org.apache.tomcat.util.net.SocketWrapperBase.flushBlocking(SocketWrapperBase.java:739) ~[tomcat-embed-core-9.0.89.jar:9.0.89]
	at org.apache.tomcat.util.net.SocketWrapperBase.flush(SocketWrapperBase.java:723) ~[tomcat-embed-core-9.0.89.jar:9.0.89]
	at org.apache.coyote.http11.Http11OutputBuffer$SocketOutputBuffer.flush(Http11OutputBuffer.java:559) ~[tomcat-embed-core-9.0.89.jar:9.0.89]
	at org.apache.coyote.http11.filters.ChunkedOutputFilter.flush(ChunkedOutputFilter.java:157) ~[tomcat-embed-core-9.0.89.jar:9.0.89]
	at org.apache.coyote.http11.Http11OutputBuffer.flush(Http11OutputBuffer.java:216) ~[tomcat-embed-core-9.0.89.jar:9.0.89]
	at org.apache.coyote.http11.Http11Processor.flush(Http11Processor.java:1243) ~[tomcat-embed-core-9.0.89.jar:9.0.89]
	at org.apache.coyote.AbstractProcessor.action(AbstractProcessor.java:399) ~[tomcat-embed-core-9.0.89.jar:9.0.89]
	at org.apache.coyote.Response.action(Response.java:207) ~[tomcat-embed-core-9.0.89.jar:9.0.89]
	at org.apache.catalina.connector.OutputBuffer.doFlush(OutputBuffer.java:303) ~[tomcat-embed-core-9.0.89.jar:9.0.89]
	at org.apache.catalina.connector.OutputBuffer.flush(OutputBuffer.java:269) ~[tomcat-embed-core-9.0.89.jar:9.0.89]
	at org.apache.catalina.connector.CoyoteOutputStream.flush(CoyoteOutputStream.java:136) ~[tomcat-embed-core-9.0.89.jar:9.0.89]
	at org.springframework.web.context.request.async.StandardServletAsyncWebRequest$LifecycleServletOutputStream.flush(StandardServletAsyncWebRequest.java:389) ~[spring-web-5.3.37.jar:5.3.37]
	at java.base/sun.nio.cs.StreamEncoder.implFlush(StreamEncoder.java:412) ~[na:na]
	at java.base/sun.nio.cs.StreamEncoder.lockedFlush(StreamEncoder.java:214) ~[na:na]
	at java.base/sun.nio.cs.StreamEncoder.flush(StreamEncoder.java:201) ~[na:na]
	at java.base/java.io.OutputStreamWriter.flush(OutputStreamWriter.java:262) ~[na:na]
	at org.springframework.util.StreamUtils.copy(StreamUtils.java:148) ~[spring-core-5.3.37.jar:5.3.37]
	at org.springframework.http.converter.StringHttpMessageConverter.writeInternal(StringHttpMessageConverter.java:125) ~[spring-web-5.3.37.jar:5.3.37]
	at org.springframework.http.converter.StringHttpMessageConverter.writeInternal(StringHttpMessageConverter.java:44) ~[spring-web-5.3.37.jar:5.3.37]
	at org.springframework.http.converter.AbstractHttpMessageConverter.write(AbstractHttpMessageConverter.java:227) ~[spring-web-5.3.37.jar:5.3.37]
	at org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitterReturnValueHandler$HttpMessageConvertingHandler.sendInternal(ResponseBodyEmitterReturnValueHandler.java:212) ~[spring-webmvc-5.3.37.jar:5.3.37]
	at org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitterReturnValueHandler$HttpMessageConvertingHandler.send(ResponseBodyEmitterReturnValueHandler.java:205) ~[spring-webmvc-5.3.37.jar:5.3.37]
	at org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter.sendInternal(ResponseBodyEmitter.java:204) ~[spring-webmvc-5.3.37.jar:5.3.37]
	at org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter.send(ResponseBodyEmitter.java:198) ~[spring-webmvc-5.3.37.jar:5.3.37]
	at org.springframework.web.servlet.mvc.method.annotation.SseEmitter.send(SseEmitter.java:127) ~[spring-webmvc-5.3.37.jar:5.3.37]
	at com.github.eduhoribe.SseController.sseHeartbeat(SseController.kt:58) ~[classes/:na]
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103) ~[na:na]
	at java.base/java.lang.reflect.Method.invoke(Method.java:580) ~[na:na]
	at org.springframework.scheduling.support.ScheduledMethodRunnable.run(ScheduledMethodRunnable.java:84) ~[spring-context-5.3.37.jar:5.3.37]
	at org.springframework.scheduling.support.DelegatingErrorHandlingRunnable.run(DelegatingErrorHandlingRunnable.java:54) ~[spring-context-5.3.37.jar:5.3.37]
	at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:572) ~[na:na]
	at java.base/java.util.concurrent.FutureTask.runAndReset$$$capture(FutureTask.java:358) ~[na:na]
	at java.base/java.util.concurrent.FutureTask.runAndReset(FutureTask.java) ~[na:na]
	at java.base/java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:305) ~[na:na]
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1144) ~[na:na]
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:642) ~[na:na]
	at java.base/java.lang.Thread.run(Thread.java:1583) ~[na:na]
```

</details>
