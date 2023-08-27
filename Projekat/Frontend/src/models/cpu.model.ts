import { CpuSocket } from "./cpu-socket.model";
import { Manufacturer } from "./manufacturer.model";
import { Purpose } from "./purpose.model";

export class Cpu {
    name: string = '';
    manufacturer: Manufacturer | null = null;
    purpose: Purpose | null = null;
    price: number = 0;
    socket: CpuSocket | null = null;
    thermicPower: number = 0;
    cores: number = 0;
    threads: number = 0;
    fabricationProcess: number = 0;
    frequency: number = 0;
    canOverclock: boolean = false;
    hasIntegratedGraphics: boolean = false;
}
