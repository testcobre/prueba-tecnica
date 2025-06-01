
package com.avivas.domain.ws;

import java.net.URI;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.avivas.domain.dto.NotificationEventWebHookRequest;

@FeignClient(name = "webhookClient", url = "${webhook.client.url}")
public interface WebhookClient {
    @PostMapping
    ResponseEntity<Void> sendWebhookNotification(@RequestBody NotificationEventWebHookRequest request);
}
