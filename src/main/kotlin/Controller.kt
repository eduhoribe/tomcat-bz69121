package com.github.eduhoribe

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

@Controller
class Controller {
    private val logger = LoggerFactory.getLogger(javaClass)

    private val emitters = ConcurrentHashMap<UUID, SseEmitter>()

    val onCompletionCount = AtomicInteger()
    val onErrorCount = AtomicInteger()

    @GetMapping("/ping")
    fun ping(): ResponseEntity<String> = ResponseEntity.ok("pong")

    @GetMapping("/sse")
    fun sse() = SseEmitter().apply {
        val uuid = UUID.randomUUID()

        onCompletion {
            onCompletionCount.incrementAndGet()
            logger.info("onCompletion")
            emitters.remove(uuid)
        }
        onTimeout { logger.error("onTimeout") }
        onError {
            onErrorCount.incrementAndGet()
            logger.error("onError: {}: {}", it.javaClass.simpleName, it.message)
            emitters.remove(uuid)
        }

        send(SseEmitter.event().id(uuid.toString()).data("created"))
        emitters[uuid] = this
    }

    @Scheduled(fixedDelay = 1_000)
    fun sseHeartbeat() {
        val toRemove = HashSet<UUID>()
        emitters.forEach { (uuid, emitter) ->
            try {
                emitter.send(SseEmitter.event().id(uuid.toString()).data("heartbeat"))
            } catch (e: IOException) {
                logger.error("IO error to send heartbeat to $uuid")
                toRemove.add(uuid)
            } catch (e: Exception) {
                logger.error("Error to send heartbeat to $uuid", e)
                toRemove.add(uuid)
            }
        }
        toRemove.forEach { emitters.remove(it) }
    }

    @RequestMapping("/err")
    fun err(): Unit = throw Exception("err")
}