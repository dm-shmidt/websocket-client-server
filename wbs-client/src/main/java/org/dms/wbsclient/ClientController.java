package org.dms.wbsclient;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dms.wbsclient.client.ClientService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class ClientController {

  private final ClientService clientService;

  @GetMapping("/run/{seconds}")
  public void runFetchingData(
      @PathVariable("seconds") @Min(5) @Max(60) Integer seconds) {
    clientService.runClient(seconds);
    log.info("Client started.");
  }

}
