import {Component} from '@angular/core';
import {CpuUsageInfo} from "./cpu-usage-info";
import {HttpClient} from "@angular/common/http";
import {timer} from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'web-ui';
  data!: CpuUsageInfo[]
  seconds!: bigint;
  minutes!: bigint;
  enableFetch: boolean = true

  serverBaseUrl = "https://localhost:8001/cpu-usage/";
  clientBaseUrl = "https://localhost:8002/run/";

  constructor(public httpClient: HttpClient) {
  }

  public recordCpuUsage() {
    const timeout = Number(this.seconds);
    const subscribe = timer(0, 1000)
    .subscribe(val => this.seconds = BigInt(timeout - val));
    this.enableFetch = false;
    this.httpClient.get(this.clientBaseUrl + this.seconds.toString())
    .subscribe();
    setTimeout(() => {
      subscribe.unsubscribe();
      this.seconds = BigInt(0);
      this.enableFetch = true;
    }, timeout * 1000);
  }

  public getData() {
    this.httpClient.get<CpuUsageInfo[]>(this.serverBaseUrl + this.minutes.toString())
    .subscribe((results) => {
      this.data = results;
    });
  }
}
