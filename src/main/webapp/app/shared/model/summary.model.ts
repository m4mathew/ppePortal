    export interface ISUMMARY {
    unitId?: string;
    ppeStatus?: string;
    nosCus?: number;
    avgDays?:number;
}

export class SUMMARY implements ISUMMARY {
    constructor(public unitId?: string, public ppeStatus?: string, public nosCus?: number, avgDays?:number;) {}
}