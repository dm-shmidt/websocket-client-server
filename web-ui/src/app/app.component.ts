import {Component} from '@angular/core';
import {CpuUsageInfo} from "./cpu-usage-info";
import {HttpClient} from "@angular/common/http";

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
    this.enableFetch = false;
    this.httpClient.get(this.clientBaseUrl + this.seconds.toString())
    .subscribe();
    setTimeout(() => {
      this.enableFetch = true;
    }, Number(this.seconds)*1000);
  }

  public getData() {
    this.httpClient.get<CpuUsageInfo[]>(this.serverBaseUrl + this.minutes.toString())
    .subscribe((results) => {
      this.data = results;
    });
  }
}
