import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { FailureSymptom } from 'src/models/failure.model';

@Injectable({
  providedIn: 'root'
})
export class FailureService {

  constructor(private httpClient: HttpClient) { }

  getFailureCauseProbabilities(failures: string[]): Observable<FailureSymptom[]> {
    return this.httpClient.post<FailureSymptom[]>('api/failure', failures);
  }

}