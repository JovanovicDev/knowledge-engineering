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

  motherboardSpecsForm: FormGroup = new FormGroup({
    pciExpressSlots: new FormControl<number>(0),
    sataSlots: new FormControl<number>(0),
    ramSlots: new FormControl<number>(0),
    m2Slots: new FormControl<number>(0),
  });

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

  cpuSpecsForm: FormGroup = new FormGroup({
    thermicPower: new FormControl<number>(0),
    cores: new FormControl<number>(0),
    threads: new FormControl<number>(0),
    fabricationProcess: new FormControl<number>(0),
    frequency: new FormControl<number>(0),
    canOverclock: new FormControl<boolean>(false),
    hasIntegratedGraphics: new FormControl<boolean>(false),
  });

  psuSpecsForm: FormGroup = new FormGroup({
    sataConnectors: new FormControl<number>(0),
    molexConnectors: new FormControl<number>(0),
    exitPower: new FormControl<number>(0),
    fanDiameter: new FormControl<number>(0),
  });

  caseSpecsForm: FormGroup = new FormGroup({
    pciSlots: new FormControl<number>(0),
    hasPowerSupply: new FormControl<boolean>(false),
  });

  ramSpecsForm: FormGroup = new FormGroup({
    latency: new FormControl<number>(0),
    capacity: new FormControl<number>(0),
    frequency: new FormControl<number>(0),
    voltage: new FormControl<number>(0),
  });

  ssdSpecsForm: FormGroup = new FormGroup({
    thickness: new FormControl<number>(0),
    capacity: new FormControl<number>(0),
    readSpeed: new FormControl<number>(0),
    writeSpeed: new FormControl<number>(0),
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

  findMotherboards() {
    this.suggestionService.findMotherboards(
      this.motherboardSpecsForm.value.pciExpressSlots,
      this.motherboardSpecsForm.value.sataSlots,
      this.motherboardSpecsForm.value.ramSlots,
      this.motherboardSpecsForm.value.m2Slots,
    ).subscribe((res) => {
      console.log(res)
    });
  }

  findGpus() {
    this.suggestionService.findGPUs(
      this.gpuSpecsForm.value.memoryInGigabytes,
      this.gpuSpecsForm.value.memoryBusInBits,
      this.gpuSpecsForm.value.pciExpressVersion,
      this.gpuSpecsForm.value.hasDVIInterface,
      this.gpuSpecsForm.value.hasUSBCInterface,
      this.gpuSpecsForm.value.hasHDMIInterface,
      this.gpuSpecsForm.value.hasDisplayPortInterface,
      this.gpuSpecsForm.value.hasVGAInterface
    ).subscribe((res) => {
      console.log(res)
    });
  }

  findCpus() {
    this.suggestionService.findCPUs(
      this.cpuSpecsForm.value.thermicPower,
      this.cpuSpecsForm.value.cores,
      this.cpuSpecsForm.value.threads,
      this.cpuSpecsForm.value.fabricationProcess,
      this.cpuSpecsForm.value.frequency,
      this.cpuSpecsForm.value.canOverclock,
      this.cpuSpecsForm.value.hasIntegratedGraphics
    ).subscribe((res) => {
      console.log(res)
    });
  }

  findPsus() {
    this.suggestionService.findPSUs(
      this.psuSpecsForm.value.sataConnectors,
      this.psuSpecsForm.value.molexConnectors,
      this.psuSpecsForm.value.exitPower,
      this.psuSpecsForm.value.fanDiameter,
    ).subscribe((res) => {
      console.log(res)
    });
  }

  findCases() {
    console.log(this.caseSpecsForm.value)
  }

  findRams() {
    console.log(this.ramSpecsForm.value)
  }

  findSsds() {
    console.log(this.ssdSpecsForm.value)
  }
}
