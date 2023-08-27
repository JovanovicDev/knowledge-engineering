import { HttpClient, HttpParams } from '@angular/common/http';
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
  
  findMotherboards(pciExpressSlots: number, sataSlots: number, ramSlots: number, m2Slots: number): Observable<Motherboard[]> {
    return this.httpClient.get<Motherboard[]>('api/motherboard/find', {
      params: new HttpParams()
        .set('pciExpressSlots', pciExpressSlots)
        .set('sataSlots', sataSlots)
        .set('ramSlots', ramSlots)
        .set('m2Slots', m2Slots)
    });
  }

  findGPUs(memoryInGigabytes: number, memoryBusInBits: number, pciExpressVersion: number, hasDVIInterface: boolean, hasUSBCInterface: boolean, hasHDMIInterface: boolean, hasDisplayPortInterface: boolean, hasVGAInterface: boolean): Observable<Gpu[]> {
    return this.httpClient.get<Gpu[]>('api/gpu/find', {
      params: new HttpParams()
      .set('memoryInGigabytes', memoryInGigabytes)
      .set('memoryBusInBits', memoryBusInBits)
      .set('pciExpressVersion', pciExpressVersion)
      .set('hasDVIInterface', hasDVIInterface)
      .set('hasUSBCInterface', hasUSBCInterface)
      .set('hasHDMIInterface', hasHDMIInterface)
      .set('hasDisplayPortInterface', hasDisplayPortInterface)
      .set('hasVGAInterface', hasVGAInterface)
    });
  }

  // findCPUs(): Observable<CPU[]> {

  // }

  // findPSUs(): Observable<PSU[]> {

  // }

  // findCases(): Observable<Case[]> {

  // } 

  // findRams(): Observable<Ram[]> {

  // }

  // findSsd(): Observable<Ssd[]> {

  // }
}