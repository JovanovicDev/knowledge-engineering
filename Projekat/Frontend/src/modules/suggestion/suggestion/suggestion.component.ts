import { Component, OnInit } from '@angular/core';
import { Case } from 'src/models/case.model';
import { Cpu } from 'src/models/cpu.model';
import { Gpu } from 'src/models/gpu.model';
import { Motherboard } from 'src/models/motherboard.model';
import { Psu } from 'src/models/psu.model';
import { Ram } from 'src/models/ram.model';
import { Ssd } from 'src/models/ssd.model';
import { SuggestionService } from 'src/services/suggestion.service';
import { FormGroup, FormControl } from '@angular/forms';
import { GpuMemoryType } from 'src/models/gpu-memory-type.model';
import { Manufacturer } from 'src/models/manufacturer.model';

@Component({
  selector: 'app-suggestion',
  templateUrl: './suggestion.component.html',
  styleUrls: ['./suggestion.component.css']
})
export class SuggestionComponent implements OnInit {
  public motherboards: Motherboard[] = [];
  public gpus: Gpu[] = [];
  public cpus: Cpu[] = [];
  public psus: Psu[] = [];
  public cases: Case[] = [];
  public rams: Ram[] = [];
  public ssds: Ssd[] = [];

  public searchedMotherboards: Motherboard[] = [];
  public searchedGpus: Gpu[] = [];
  public searchedCpus: Cpu[] = [];
  public searchedPsus: Psu[] = [];
  public searchedCases: Case[] = [];
  public searchedRams: Ram[] = [];
  public searchedSsds: Ssd[] = [];

  gpuSpecsForm: FormGroup = new FormGroup({
    memoryInGigabytes: new FormControl<number>(0),
    memoryBusInBits: new FormControl<number>(0),
    pciExpressVersion: new FormControl<number>(0),
    hasDVIInterface: new FormControl<boolean>(false),
    hasUSBCInterface: new FormControl<boolean>(false),
    hasHDMIInterface: new FormControl<boolean>(false),
    hasDisplayPortInterface: new FormControl<boolean>(false),
    hasVGAInterface: new FormControl<boolean>(false),
  });

  constructor(public suggestionService: SuggestionService){}

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

  findGpus() {
    console.log(this.gpuSpecsForm.value)
  }
}
