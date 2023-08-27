import { Manufacturer } from "./manufacturer.model";
import { Purpose } from "./purpose.model";
import { SsdType } from "./ssd-type.model";

export class Ssd {
    name: string = '';
    manufacturer: Manufacturer | null = null;
    purpose: Purpose | null = null;
    price: number = 0;
    type: SsdType | null = null;
    readSpeed: number = 0;
    writeSpeed: number = 0;
    capacity: number = 0;
    thickness: number = 0;
}
