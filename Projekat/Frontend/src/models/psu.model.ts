import { Manufacturer } from "./manufacturer.model";
import { PsuType } from "./psu-type.model";
import { Purpose } from "./purpose.model";

export class Psu {
    name: string = '';
    manufacturer: Manufacturer | null = null;
    purpose: Purpose | null = null;
    price: number = 0;
    type: PsuType | null = null;
    sataConnectors: number = 0;
    molexConnectors: number = 0;
    exitPower: number = 0;
    fanDiameter: number = 0;
}
