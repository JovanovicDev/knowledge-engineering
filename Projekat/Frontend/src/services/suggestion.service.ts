import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Case } from 'src/models/case.model';
import { Cpu } from 'src/models/cpu.model';
import { Gpu } from 'src/models/gpu.model';
import { Motherboard } from 'src/models/motherboard.model';
import { Psu } from 'src/models/psu.model';
import { Ram } from 'src/models/ram.model';
import { Ssd } from 'src/models/ssd.model';

@Injectable({
  providedIn: 'root'
})
export class SuggestionService {

  constructor(private httpClient: HttpClient) { }

  getAllMotherboards(): Observable<Motherboard[]> {
    return this.httpClient.get<Motherboard[]>('api/motherboard');
  }

  getAllGPUs(): Observable<Gpu[]> {
    return this.httpClient.get<Gpu[]>('api/gpu');
  }

  getAllCPUs(): Observable<Cpu[]> {
    return this.httpClient.get<Cpu[]>('api/cpu');
  }

  getAllCases(): Observable<Case[]> {
    return this.httpClient.get<Case[]>('api/case')
  }

  getAllPSUs(): Observable<Psu[]> {
    return this.httpClient.get<Psu[]>('api/psu');
  }

  getAllRAMs(): Observable<Ram[]> {
    return this.httpClient.get<Ram[]>('api/ram');
  }

  getAllSSDs(): Observable<Ssd[]> {
    return this.httpClient.get<Ssd[]>('api/ssd');
  }
  
}