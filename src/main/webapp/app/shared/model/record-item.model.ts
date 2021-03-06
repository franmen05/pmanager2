import { Moment } from 'moment';
import { IPrescription } from 'app/shared/model/prescription.model';
import { IRecord } from 'app/shared/model/record.model';

export interface IRecordItem {
  id?: number;
  description?: string;
  createDate?: Moment;
  lastUpdateDate?: Moment;
  prescriptions?: IPrescription[];
  record?: IRecord;
}

export class RecordItem implements IRecordItem {
  constructor(
    public id?: number,
    public description?: string,
    public createDate?: Moment,
    public lastUpdateDate?: Moment,
    public prescriptions?: IPrescription[],
    public record?: IRecord
  ) {}
}
