import { Moment } from 'moment';
import { IPatient } from 'app/shared/model/patient.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface ITurn {
  id?: number;
  position?: number;
  createDate?: Moment;
  lastUpdateDate?: Moment;
  status?: Status;
  patient?: IPatient;
}

export class Turn implements ITurn {
  constructor(
    public id?: number,
    public position?: number,
    public createDate?: Moment,
    public lastUpdateDate?: Moment,
    public status?: Status,
    public patient?: IPatient
  ) {}
}
