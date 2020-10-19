import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITurn } from 'app/shared/model/turn.model';

type EntityResponseType = HttpResponse<ITurn>;
type EntityArrayResponseType = HttpResponse<ITurn[]>;

@Injectable({ providedIn: 'root' })
export class TurnService {
  public resourceUrl = SERVER_API_URL + 'api/turns';

  constructor(protected http: HttpClient) {}

  create(turn: ITurn): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(turn);
    return this.http
      .post<ITurn>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(turn: ITurn): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(turn);
    return this.http
      .put<ITurn>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITurn>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITurn[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(turn: ITurn): ITurn {
    const copy: ITurn = Object.assign({}, turn, {
      createDate: turn.createDate && turn.createDate.isValid() ? turn.createDate.toJSON() : undefined,
      lastUpdateDate: turn.lastUpdateDate && turn.lastUpdateDate.isValid() ? turn.lastUpdateDate.toJSON() : undefined,
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
      res.body.forEach((turn: ITurn) => {
        turn.createDate = turn.createDate ? moment(turn.createDate) : undefined;
        turn.lastUpdateDate = turn.lastUpdateDate ? moment(turn.lastUpdateDate) : undefined;
      });
    }
    return res;
  }
}
