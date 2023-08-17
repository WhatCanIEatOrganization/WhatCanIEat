import { Component, OnInit } from '@angular/core';
import { DailyTipService } from './daily-tip.service';

export interface DailyTip {
  title: string,
  text: string,
}

@Component({
  selector: 'app-daily-tip',
  templateUrl: './daily-tip.component.html',
  styleUrls: ['./daily-tip.component.scss']
})
export class DailyTipComponent implements OnInit {
  dailyTip!: DailyTip;

  constructor(
    private dailyTipService: DailyTipService
  ) { }

  ngOnInit(): void {
    this.dailyTip = this.dailyTipService.generateDailyTip();
  }
}
