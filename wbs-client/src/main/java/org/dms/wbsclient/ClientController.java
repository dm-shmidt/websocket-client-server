package org.dms.wbsclient;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.dms.wbsclient.client.RunFetchingEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ClientController {

  private final ApplicationEventPublisher eventPublisher;

  @GetMapping("/run/{seconds}")
  public void runFetchingData(
      @PathVariable("seconds") @Min(5) @Max(60) Integer seconds) {

    eventPublisher.publishEvent(new RunFetchingEvent(seconds));
  }

}
