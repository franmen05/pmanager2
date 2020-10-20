import { Moment } from 'moment';
import { IRecord } from 'app/shared/model/record.model';

export interface IPrescription {
  id?: number;
  description?: string;
  createDate?: Moment;
  lastUpdateDate?: Moment;
  record?: IRecord;
}

export class Prescription implements IPrescription {
  constructor(
    public id?: number,
    public description?: string,
    public createDate?: Moment,
    public lastUpdateDate?: Moment,
    public record?: IRecord
  ) {}
}
