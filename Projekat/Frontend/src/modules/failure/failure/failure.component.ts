import { Component } from '@angular/core';
import { FailureSymptom } from 'src/models/failure.model';
import { FailureService } from 'src/services/failure.service';

@Component({
  selector: 'app-failure',
  templateUrl: './failure.component.html',
  styleUrls: ['./failure.component.css']
})
export class FailureComponent{

  selectedFailures: string[] = [];
  symptoms: FailureSymptom[] = [];

  constructor(public failureService: FailureService) {}

  getFailureCauseProbabilities() {
    this.failureService.getFailureCauseProbabilities(this.selectedFailures).subscribe((res) => {
      this.symptoms = res;
    });
  }

}
