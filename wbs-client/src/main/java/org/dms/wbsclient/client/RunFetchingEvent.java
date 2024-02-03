package org.dms.wbsclient.client;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RunFetchingEvent extends ApplicationEvent {

  private Integer secondsToFetch;

  public RunFetchingEvent(Integer secondsToFetch) {
    super(secondsToFetch);
    this.secondsToFetch = secondsToFetch;
  }
}
