import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Computer } from 'src/models/computer.model';
import { Purpose } from 'src/models/purpose.model';
import { Validities } from 'src/models/validities.model';

@Injectable({
  providedIn: 'root'
})
export class ValidityService {

  constructor(private httpClient: HttpClient) { }

  getPurposeValidity(configuration: Computer, purpose: Purpose): Observable<Validities> {
    console.log('api/validity?purpose=' + purpose)
    return this.httpClient.post<Validities>('api/validity?purpose=' + purpose, configuration);
  }

}