import { HttpClient } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { Observable } from 'rxjs';
import { AnalyticsModel } from '../models/analytics-model';

@Service()
export class AnalyticsService {

    private readonly apiUrl = 'http://localhost:8080/api/analytics';

    private http = inject(HttpClient);

    getAnalytics(): Observable<AnalyticsModel> {
        return this.http.get<AnalyticsModel>(this.apiUrl);
    }
}
