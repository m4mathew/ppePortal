import { Moment } from 'moment';

export interface ICUSTSTATUS {
    id?: number;
    custId?: number;
    ppeStatus?: string;
    changeDt?: Moment;
    userId?: string;
    days?:number;
}

export class CUSTSTATUS implements ICUSTSTATUS {
    constructor(public id?: number, public custId?: number, public ppeStatus?: string, public changeDt?: Moment, public userId?: string, days?:number;) {}
}
