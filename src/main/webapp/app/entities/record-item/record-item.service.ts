import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRecordItem } from 'app/shared/model/record-item.model';

type EntityResponseType = HttpResponse<IRecordItem>;
type EntityArrayResponseType = HttpResponse<IRecordItem[]>;

@Injectable({ providedIn: 'root' })
export class RecordItemService {
  public resourceUrl = SERVER_API_URL + 'api/record-items';

  constructor(protected http: HttpClient) {}

  create(recordItem: IRecordItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(recordItem);
    return this.http
      .post<IRecordItem>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(recordItem: IRecordItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(recordItem);
    return this.http
      .put<IRecordItem>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRecordItem>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRecordItem[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(recordItem: IRecordItem): IRecordItem {
    const copy: IRecordItem = Object.assign({}, recordItem, {
      createDate: recordItem.createDate && recordItem.createDate.isValid() ? recordItem.createDate.toJSON() : undefined,
      lastUpdateDate: recordItem.lastUpdateDate && recordItem.lastUpdateDate.isValid() ? recordItem.lastUpdateDate.toJSON() : undefined,
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
      res.body.forEach((recordItem: IRecordItem) => {
        recordItem.createDate = recordItem.createDate ? moment(recordItem.createDate) : undefined;
        recordItem.lastUpdateDate = recordItem.lastUpdateDate ? moment(recordItem.lastUpdateDate) : undefined;
      });
    }
    return res;
  }
}
