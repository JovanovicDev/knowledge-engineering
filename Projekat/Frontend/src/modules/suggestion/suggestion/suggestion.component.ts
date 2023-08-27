import { Component } from '@angular/core';
import { Case } from 'src/models/case.model';
import { Cpu } from 'src/models/cpu.model';
import { Gpu } from 'src/models/gpu.model';
import { Motherboard } from 'src/models/motherboard.model';
import { Psu } from 'src/models/psu.model';
import { Ram } from 'src/models/ram.model';
import { Ssd } from 'src/models/ssd.model';
import { SuggestionService } from 'src/services/suggestion.service';
import { FormGroup, FormControl } from '@angular/forms';

@Component({
  selector: 'app-suggestion',
  templateUrl: './suggestion.component.html',
  styleUrls: ['./suggestion.component.css']
})
export class SuggestionComponent {
  public searchedMotherboards: Motherboard[] | null = null;
  public searchedGpus: Gpu[] | null = null;
  public searchedCpus: Cpu[] | null = null;
  public searchedPsus: Psu[] | null = null;
  public searchedCases: Case[] | null = null;
  public searchedRams: Ram[] | null = null;
  public searchedSsds: Ssd[] | null = null;

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

  findMotherboards() {
    this.suggestionService.findMotherboards(
      this.motherboardSpecsForm.value.pciExpressSlots,
      this.motherboardSpecsForm.value.sataSlots,
      this.motherboardSpecsForm.value.ramSlots,
      this.motherboardSpecsForm.value.m2Slots,
    ).subscribe((res) => {
      this.searchedMotherboards = res;
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
      this.searchedGpus = res;
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
      this.searchedCpus = res;
    });
  }

  findPsus() {
    this.suggestionService.findPSUs(
      this.psuSpecsForm.value.sataConnectors,
      this.psuSpecsForm.value.molexConnectors,
      this.psuSpecsForm.value.exitPower,
      this.psuSpecsForm.value.fanDiameter,
    ).subscribe((res) => {
      this.searchedPsus = res;
    });
  }

  findCases() {
    this.suggestionService.findCases(
      this.caseSpecsForm.value.pciSlots,
      this.caseSpecsForm.value.hasPowerSupply,
    ).subscribe((res) => {
      this.searchedCases = res;
    });
  }

  findRams() {
    this.suggestionService.findRams(
      this.ramSpecsForm.value.latency,
      this.ramSpecsForm.value.capacity,
      this.ramSpecsForm.value.frequency,
      this.ramSpecsForm.value.voltage,
    ).subscribe((res) => {
      this.searchedRams = res;
    });
  }

  findSsds() {
    this.suggestionService.findSsds(
      this.ssdSpecsForm.value.thickness,
      this.ssdSpecsForm.value.capacity,
      this.ssdSpecsForm.value.readSpeed,
      this.ssdSpecsForm.value.writeSpeed,
    ).subscribe((res) => {
      this.searchedSsds = res;
    });
  }
}
