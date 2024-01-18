import { Component } from '@angular/core';
import { Case } from 'src/models/case.model';
import { ComputerSimilarityInput } from 'src/models/computer-similarity-input.model';
import { Computer } from 'src/models/computer.model';
import { Cpu } from 'src/models/cpu.model';
import { Gpu } from 'src/models/gpu.model';
import { Motherboard } from 'src/models/motherboard.model';
import { Psu } from 'src/models/psu.model';
import { Ram } from 'src/models/ram.model';
import { SimilarComputer } from 'src/models/similar-computer.model';
import { Ssd } from 'src/models/ssd.model';
import { SimilarityService } from 'src/services/similarity.service';
import { SuggestionService } from 'src/services/suggestion.service';

@Component({
  selector: 'app-similarity',
  templateUrl: './similarity.component.html',
  styleUrls: ['./similarity.component.css']
})
export class SimilarityComponent {
  public motherboards: Motherboard[] = [];
  public gpus: Gpu[] = [];
  public cpus: Cpu[] = [];
  public psus: Psu[] = [];
  public cases: Case[] = [];
  public rams: Ram[] = [];
  public ssds: Ssd[] = [];

  public selectedComputer = new Computer();
  public similarComputers: SimilarComputer[] = [];

  constructor(public similarityService: SimilarityService, public suggestionService: SuggestionService){}

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

  findSimilar() {
    let input = new ComputerSimilarityInput();
    input.cpuFrequency = this.selectedComputer.cpu!.frequency;
    input.cpuNumOfCores = this.selectedComputer.cpu!.cores;
    input.gpuMemory = this.selectedComputer.gpu!.memoryInGigabytes;
    input.gpuMemoryBus = this.selectedComputer.gpu!.memoryBusInBits;
    input.ramCapacity = this.selectedComputer.ram!.capacity;
    input.ramType = this.selectedComputer.ram!.type?.toString()!;
    input.diskCapacity = this.selectedComputer.ssd!.capacity;
    this.similarityService.get(input).subscribe((res) => {
      this.similarComputers = res;
      console.log(res)
    });
  }
}
