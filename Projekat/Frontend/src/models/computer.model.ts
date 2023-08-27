import { Case } from "./case.model";
import { Cpu } from "./cpu.model";
import { Gpu } from "./gpu.model";
import { Motherboard } from "./motherboard.model";
import { Psu } from "./psu.model";
import { Purpose } from "./purpose.model";
import { Ram } from "./ram.model";
import { Ssd } from "./ssd.model";

export class Computer {
    name: string = '';
    purpose: Purpose | null = null;
    price: number = 0;
    motherboard: Motherboard | null = null;
    gpu: Gpu | null = null;
    cpu: Cpu | null = null;
    ram: Ram | null = null;
    ssd: Ssd | null = null;
    powerSupply: Psu | null = null;
    boxCase: Case | null = null;
}
