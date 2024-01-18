import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ComputerSimilarityInput } from 'src/models/computer-similarity-input.model';
import { SimilarComputer } from 'src/models/similar-computer.model';

@Injectable({
  providedIn: 'root'
})
export class SimilarityService {

  constructor(private httpClient: HttpClient) { }

  get(input: ComputerSimilarityInput): Observable<SimilarComputer[]> {
    return this.httpClient.post<SimilarComputer[]>('api/similarity', input);
  }

}
