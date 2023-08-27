import { GpuMemoryType } from "./gpu-memory-type.model";
import { Manufacturer } from "./manufacturer.model";
import { Purpose } from "./purpose.model";

export class Gpu {
    name: string = '';
    manufacturer: Manufacturer | null = null;
    purpose: Purpose | null = null;
    price: number = 0;
    memoryType: GpuMemoryType | null = null;
    chipManufacturer: Manufacturer | null = null;
    memoryInGigabytes: number = 0;
    memoryBusInBits: number = 0;
    pciExpressVersion: number = 0;
    hasDVIInterface: boolean = false;
    hasUSBCInterface: boolean = false;
    hasHDMIInterface: boolean = false;
    hasDisplayPortInterface: boolean = false;
    hasVGAInterface: boolean = false;
}
