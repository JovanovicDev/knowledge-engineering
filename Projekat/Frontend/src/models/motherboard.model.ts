import { CaseType } from "./case-type.model";
import { GpuMemoryType } from "./gpu-memory-type.model";
import { Manufacturer } from "./manufacturer.model";
import { MotherboardChipset } from "./motherboard-chipset.model";
import { PsuType } from "./psu-type.model";
import { Purpose } from "./purpose.model";
import { RamType } from "./ram-type.model";
import { SsdType } from "./ssd-type.model";

export class Motherboard {
    name: string = '';
    manufacturer: Manufacturer | null = null;
    purpose: Purpose | null = null;
    price: number = 0;
    chipset: MotherboardChipset | null = null;
    compatibleWithCPUsFrom: Manufacturer | null = null;
    compatibleWithGPUMemoryType: GpuMemoryType | null = null;
    compatibleWithPSUType: PsuType | null = null;
    compatibleWithCaseType: CaseType | null = null;
    compatibleWithRAMType: RamType | null = null;
    compatibleWithSSDType: SsdType | null = null;
    pciExpressSlots: number = 0;
    sataSlots: number = 0;
    ramSlots: number = 0;
    m2Slots: number = 0;
}
