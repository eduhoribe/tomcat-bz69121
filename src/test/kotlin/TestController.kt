package com.github.eduhoribe

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.http.codec.ServerSentEvent
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.returnResult
import reactor.test.StepVerifier
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.seconds

@SpringBootTest(webEnvironment = RANDOM_PORT)
class TestController {

    @Autowired
    lateinit var webClient: WebTestClient

    @Autowired
    lateinit var controller: Controller

    @Autowired
    lateinit var errorController: ErrorController

    @Test
    fun test() = runBlocking {
        webClient.get().uri("/ping").exchange().expectStatus().isOk
        assertEquals(0, errorController.count.get())

        webClient.get().uri("/invalid_path").exchange().expectStatus().isNotFound
        assertEquals(1, errorController.count.get())

        webClient.get().uri("/err").exchange().expectStatus().is5xxServerError
        assertEquals(2, errorController.count.get())

        StepVerifier.create(
            webClient.get().uri("/sse").exchange().returnResult<ServerSentEvent<String>>().responseBody
        ).expectNextMatches { it.data()!! == "created" }
            .expectNextMatches { it.data()!! == "heartbeat" }
            .expectNextMatches { it.data()!! == "heartbeat" }
            .thenCancel()
            .verify()
        delay(5.seconds)
        assertEquals(2, errorController.count.get())
        assertEquals(1, controller.onErrorCount.get())
        assertEquals(1, controller.onCompletionCount.get())
    }
}