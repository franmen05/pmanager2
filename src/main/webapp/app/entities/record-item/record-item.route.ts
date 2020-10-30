import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRecordItem, RecordItem } from 'app/shared/model/record-item.model';
import { RecordItemService } from './record-item.service';
import { RecordItemComponent } from './record-item.component';
import { RecordItemDetailComponent } from './record-item-detail.component';
import { RecordItemUpdateComponent } from './record-item-update.component';

@Injectable({ providedIn: 'root' })
export class RecordItemResolve implements Resolve<IRecordItem> {
  constructor(private service: RecordItemService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRecordItem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((recordItem: HttpResponse<RecordItem>) => {
          if (recordItem.body) {
            return of(recordItem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RecordItem());
  }
}

export const recordItemRoute: Routes = [
  {
    path: '',
    component: RecordItemComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'RecordItems',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RecordItemDetailComponent,
    resolve: {
      recordItem: RecordItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'RecordItems',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RecordItemUpdateComponent,
    resolve: {
      recordItem: RecordItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'RecordItems',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RecordItemUpdateComponent,
    resolve: {
      recordItem: RecordItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'RecordItems',
    },
    canActivate: [UserRouteAccessService],
  },
];
