import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { AnalyticsService } from '../../services/analytics-service';
import { AnalyticsModel } from '../../models/analytics-model';
import { DecimalPipe } from '@angular/common';
import { BackButton } from '../../shared/back-button/back-button';
import { Chart, registerables } from 'chart.js';

Chart.register(...registerables);

@Component({
  selector: 'app-analytics',
  imports: [DecimalPipe, BackButton],
  templateUrl: './analytics.html',
  styleUrl: './analytics.css',
})
export class Analytics implements OnInit {

  private readonly analyticsService = inject(AnalyticsService);

  private readonly cdr = inject(ChangeDetectorRef);

  analytics?: AnalyticsModel;

  private uploadChart?: Chart;

  ngOnInit(): void {
    this.loadAnalytics();
  }

  private loadAnalytics(): void {
    this.analyticsService.getAnalytics().subscribe({
      next: (response) => {
        this.analytics = response;
        
        this.cdr.detectChanges();

        setTimeout(() => {
          this.createUploadChart();
        })
      },
      error: (error) => {
        console.error('Failed to lead analytics', error);
      }
    })
  }

  private createUploadChart(): void {
    if(!this.analytics){
      return;
    }

    if(this.uploadChart){
      this.uploadChart.destroy();
    }

    this.uploadChart = new Chart('uploadChart', {
      type: 'line',
      data: {
        labels: this.analytics.uploadTrends.map(t => t.date),
        datasets: [{
          label: 'Documents Uploaded',
          data: this.analytics.uploadTrends.map(t => t.count),
          tension: 0.3,
          fill: false
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            labels: {
              color: 'white'
            }
          }
        },
        scales: {
          x: {
            ticks: {
              color: 'white'
            }
          },
          y: {
            beginAtZero: true,
            ticks: {
              color: 'white'
            }
          }
        }
      }
    })
  } 
}
