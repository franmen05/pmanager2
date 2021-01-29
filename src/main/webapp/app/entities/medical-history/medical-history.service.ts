import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMedicalHistory } from 'app/shared/model/medical-history.model';

type EntityResponseType = HttpResponse<IMedicalHistory>;
type EntityArrayResponseType = HttpResponse<IMedicalHistory[]>;

@Injectable({ providedIn: 'root' })
export class MedicalHistoryService {
  public resourceUrl = SERVER_API_URL + 'api/medical-histories';

  constructor(protected http: HttpClient) {}

  create(medicalHistory: IMedicalHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(medicalHistory);
    return this.http
      .post<IMedicalHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(medicalHistory: IMedicalHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(medicalHistory);
    return this.http
      .put<IMedicalHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }
  findByRecord(id: number): Observable<EntityResponseType> {
    // const options = createRequestOption(req);
    return this.http
      .get<IMedicalHistory>(`${this.resourceUrl}/record/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMedicalHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMedicalHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(medicalHistory: IMedicalHistory): IMedicalHistory {
    const copy: IMedicalHistory = Object.assign({}, medicalHistory, {
      createDate: medicalHistory.createDate && medicalHistory.createDate.isValid() ? medicalHistory.createDate.toJSON() : undefined,
      lastUpdateDate:
        medicalHistory.lastUpdateDate && medicalHistory.lastUpdateDate.isValid() ? medicalHistory.lastUpdateDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createDate = res.body.createDate ? moment(res.body.createDate) : undefined;
      res.body.lastUpdateDate = res.body.lastUpdateDate ? moment(res.body.lastUpdateDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((medicalHistory: IMedicalHistory) => {
        medicalHistory.createDate = medicalHistory.createDate ? moment(medicalHistory.createDate) : undefined;
        medicalHistory.lastUpdateDate = medicalHistory.lastUpdateDate ? moment(medicalHistory.lastUpdateDate) : undefined;
      });
    }
    return res;
  }
}
