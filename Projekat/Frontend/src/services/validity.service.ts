import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Computer } from 'src/models/computer.model';
import { Purpose } from 'src/models/purpose.model';
import { Validity } from 'src/models/validity.model';

@Injectable({
  providedIn: 'root'
})
export class ValidityService {

  constructor(private httpClient: HttpClient) { }

  getPurposeValidity(configuration: Computer, purpose: Purpose): Observable<Validity> {
    return this.httpClient.post<Validity>('api/validity?purpose=' + purpose, configuration);
  }

}