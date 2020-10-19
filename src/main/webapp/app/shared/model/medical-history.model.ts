import { Moment } from 'moment';
import { IMedicalHistoryConfig } from 'app/shared/model/medical-history-config.model';
import { IRecord } from 'app/shared/model/record.model';

export interface IMedicalHistory {
  id?: number;
  comment?: string;
  createDate?: Moment;
  lastUpdateDate?: Moment;
  medicalHistories?: IMedicalHistoryConfig[];
  record?: IRecord;
}

export class MedicalHistory implements IMedicalHistory {
  constructor(
    public id?: number,
    public comment?: string,
    public createDate?: Moment,
    public lastUpdateDate?: Moment,
    public medicalHistories?: IMedicalHistoryConfig[],
    public record?: IRecord
  ) {}
}
