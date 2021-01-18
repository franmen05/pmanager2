import { Moment } from 'moment';
import { IRecordItem } from 'app/shared/model/record-item.model';
import { IMedicalHistory } from 'app/shared/model/medical-history.model';
import { IPatient } from 'app/shared/model/patient.model';

export interface IRecord {
  id?: number;
  description?: string;
  createDate?: Moment;
  lastUpdateDate?: Moment;
  items?: IRecordItem[];
  medicalHistories?: IMedicalHistory[];
  patient?: IPatient;
}

export class Record implements IRecord {
  constructor(
    public id?: number,
    public description?: string,
    public createDate?: Moment,
    public lastUpdateDate?: Moment,
    public items?: IRecordItem[],
    public medicalHistories?: IMedicalHistory[],
    public patient?: IPatient
  ) {}
}
