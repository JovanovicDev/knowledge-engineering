import { CaseType } from "./case-type.model";
import { Manufacturer } from "./manufacturer.model";
import { Purpose } from "./purpose.model";

export class Case {
    name: string = '';
    manufacturer: Manufacturer | null = null;
    purpose: Purpose | null = null;
    price: number = 0;
    type: CaseType | null = null;
    pciSlots: number = 0;
    hasPowerSupply: boolean = false;
}
