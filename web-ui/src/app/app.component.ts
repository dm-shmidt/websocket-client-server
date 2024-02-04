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
  minutes!: bigint;

  baseUrl = "https://localhost:8001/cpu-usage/";

  constructor(public httpClient: HttpClient) {
  }

  public getData() {
    this.httpClient.get<CpuUsageInfo[]>(this.baseUrl + this.minutes.toString())
    .subscribe((results) => {
      console.log('Data is received - Result - ', results);
      this.data = results;
    });
  }
}
