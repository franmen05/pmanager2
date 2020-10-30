import { Moment } from 'moment';
import { IRecordItem } from 'app/shared/model/record-item.model';

export interface IPrescription {
  id?: number;
  description?: string;
  createDate?: Moment;
  lastUpdateDate?: Moment;
  recordItem?: IRecordItem;
}

export class Prescription implements IPrescription {
  constructor(
    public id?: number,
    public description?: string,
    public createDate?: Moment,
    public lastUpdateDate?: Moment,
    public recordItem?: IRecordItem
  ) {}
}
