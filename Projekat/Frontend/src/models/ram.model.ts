import { Manufacturer } from "./manufacturer.model";
import { Purpose } from "./purpose.model";
import { RamType } from "./ram-type.model";

export class Ram {
    name: string = '';
    manufacturer: Manufacturer | null = null;
    purpose: Purpose | null = null;
    price: number = 0;
    type: RamType | null = null;
    latency: number = 0;
    capacity: number = 0;
    frequency: number = 0;
    voltage: number = 0;
}
