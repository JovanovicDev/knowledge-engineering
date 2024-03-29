import { Component, OnInit } from '@angular/core';
import { Case } from 'src/models/case.model';
import { Computer } from 'src/models/computer.model';
import { Cpu } from 'src/models/cpu.model';
import { Gpu } from 'src/models/gpu.model';
import { Motherboard } from 'src/models/motherboard.model';
import { Psu } from 'src/models/psu.model';
import { Purpose } from 'src/models/purpose.model';
import { Ram } from 'src/models/ram.model';
import { Ssd } from 'src/models/ssd.model';
import { Validity } from 'src/models/validity.model';
import { SuggestionService } from 'src/services/suggestion.service';
import { ValidityService } from 'src/services/validity.service';

@Component({
  selector: 'app-validity',
  templateUrl: './validity.component.html',
  styleUrls: ['./validity.component.css']
})
export class ValidityComponent implements OnInit{
  public motherboards: Motherboard[] = [];
  public gpus: Gpu[] = [];
  public cpus: Cpu[] = [];
  public psus: Psu[] = [];
  public cases: Case[] = [];
  public rams: Ram[] = [];
  public ssds: Ssd[] = [];

  public selectedMotherboard: Motherboard | null = null;
  public selectedGpu: Gpu | null = null;
  public selectedCpu: Cpu | null = null;
  public selectedPsu: Psu | null = null;
  public selectedCase: Case | null = null;
  public selectedRam: Ram | null = null;
  public selectedSsd: Ssd | null = null;
  public selectedPurpose: Purpose | null = null;

  public validity: Validity | null = null;

  constructor(public validityService: ValidityService, public suggestionService: SuggestionService){}

  ngOnInit(): void {
    this.fetchData();
  }

  fetchData() {
    this.fetchMotherboards();
    this.fetchGpus();
    this.fetchCpus();
    this.fetchPsus();
    this.fetchCases();
    this.fetchRams();
    this.fetchSsds();
  }

  fetchMotherboards() {
    this.suggestionService.getAllMotherboards().subscribe((res) => {
      this.motherboards = res;
    });
  }

  fetchGpus() {
    this.suggestionService.getAllGPUs().subscribe((res) => {
      this.gpus = res;
    });
  }

  fetchCpus() {
    this.suggestionService.getAllCPUs().subscribe((res) => {
      this.cpus = res;
    });
  }

  fetchPsus() {
    this.suggestionService.getAllPSUs().subscribe((res) => {
      this.psus = res;
    });
  }

  fetchCases() {
    this.suggestionService.getAllCases().subscribe((res) => {
      this.cases = res;
    })
  }

  fetchRams() {
    this.suggestionService.getAllRAMs().subscribe((res) => {
      this.rams = res;
    })
  }

  fetchSsds() {
    this.suggestionService.getAllSSDs().subscribe((res) => {
      this.ssds = res;
    });
  }

  getPurposeValidity() {
    let computer = new Computer();
    computer.motherboard = this.selectedMotherboard;
    computer.gpu = this.selectedGpu;
    computer.cpu = this.selectedCpu;
    computer.powerSupply = this.selectedPsu;
    computer.boxCase = this.selectedCase;
    computer.ram = this.selectedRam;
    computer.ssd = this.selectedSsd;

    this.validityService.getPurposeValidity(computer, this.selectedPurpose!).subscribe((res) => {
      this.validity = res;
    });
  }
}