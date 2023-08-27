import { Manufacturer } from "./manufacturer.model";
import { MotherboardChipset } from "./motherboard-chipset.model";
import { Purpose } from "./purpose.model";

export class Motherboard {
    name: string = '';
    manufacturer: Manufacturer | null = null;
    purpose: Purpose | null = null;
    price: number = 0;
    chipset: MotherboardChipset | null = null;
    compatibleWithCPUsFrom: Manufacturer | null = null;
    pciExpressSlots: number = 0;
    sataSlots: number = 0;
    ramSlots: number = 0;
    m2Slots: number = 0;
}
