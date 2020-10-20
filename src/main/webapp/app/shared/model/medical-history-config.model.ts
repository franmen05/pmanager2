import { Moment } from 'moment';
import { IMedicalHistory } from 'app/shared/model/medical-history.model';

export interface IMedicalHistoryConfig {
  id?: number;
  key?: string;
  description?: string;
  createDate?: Moment;
  lastUpdateDate?: Moment;
  medicalHistory?: IMedicalHistory;
}

export class MedicalHistoryConfig implements IMedicalHistoryConfig {
  constructor(
    public id?: number,
    public key?: string,
    public description?: string,
    public createDate?: Moment,
    public lastUpdateDate?: Moment,
    public medicalHistory?: IMedicalHistory
  ) {}
}
