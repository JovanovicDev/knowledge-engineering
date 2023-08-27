import { Component, OnInit } from '@angular/core';
import { Case } from 'src/models/case.model';
import { Computer } from 'src/models/computer.model';
import { Cpu } from 'src/models/cpu.model';
import { Gpu } from 'src/models/gpu.model';
import { Motherboard } from 'src/models/motherboard.model';
import { Psu } from 'src/models/psu.model';
import { Ram } from 'src/models/ram.model';
import { Ssd } from 'src/models/ssd.model';
import { SuggestionService } from 'src/services/suggestion.service';

@Component({
  selector: 'app-upgrade',
  templateUrl: './upgrade.component.html',
  styleUrls: ['./upgrade.component.css']
})
export class UpgradeComponent implements OnInit{
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
  public selectedComponentType: string | null = null;

  public upgradedConfiguration: Computer | null = null;

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

  findUpgrade() {
    let computer = new Computer();
    computer.motherboard = this.selectedMotherboard;
    computer.gpu = this.selectedGpu;
    computer.cpu = this.selectedCpu;
    computer.powerSupply = this.selectedPsu;
    computer.boxCase = this.selectedCase;
    computer.ram = this.selectedRam;
    computer.ssd = this.selectedSsd;
    this.suggestionService.upgrade(computer, this.selectedComponentType!).subscribe((res) => {
      this.upgradedConfiguration = res;
    });
  }
}
