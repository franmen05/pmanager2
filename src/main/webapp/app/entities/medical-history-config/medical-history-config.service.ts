import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMedicalHistoryConfig } from 'app/shared/model/medical-history-config.model';

type EntityResponseType = HttpResponse<IMedicalHistoryConfig>;
type EntityArrayResponseType = HttpResponse<IMedicalHistoryConfig[]>;

@Injectable({ providedIn: 'root' })
export class MedicalHistoryConfigService {
  public resourceUrl = SERVER_API_URL + 'api/medical-history-configs';

  constructor(protected http: HttpClient) {}

  create(medicalHistoryConfig: IMedicalHistoryConfig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(medicalHistoryConfig);
    return this.http
      .post<IMedicalHistoryConfig>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(medicalHistoryConfig: IMedicalHistoryConfig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(medicalHistoryConfig);
    return this.http
      .put<IMedicalHistoryConfig>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMedicalHistoryConfig>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMedicalHistoryConfig[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(medicalHistoryConfig: IMedicalHistoryConfig): IMedicalHistoryConfig {
    const copy: IMedicalHistoryConfig = Object.assign({}, medicalHistoryConfig, {
      createDate:
        medicalHistoryConfig.createDate && medicalHistoryConfig.createDate.isValid() ? medicalHistoryConfig.createDate.toJSON() : undefined,
      lastUpdateDate:
        medicalHistoryConfig.lastUpdateDate && medicalHistoryConfig.lastUpdateDate.isValid()
          ? medicalHistoryConfig.lastUpdateDate.toJSON()
          : undefined,
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
      res.body.forEach((medicalHistoryConfig: IMedicalHistoryConfig) => {
        medicalHistoryConfig.createDate = medicalHistoryConfig.createDate ? moment(medicalHistoryConfig.createDate) : undefined;
        medicalHistoryConfig.lastUpdateDate = medicalHistoryConfig.lastUpdateDate ? moment(medicalHistoryConfig.lastUpdateDate) : undefined;
      });
    }
    return res;
  }
}
