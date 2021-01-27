import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMedicalHistory, MedicalHistory } from 'app/shared/model/medical-history.model';
import { MedicalHistoryService } from './medical-history.service';
import { MedicalHistoryComponent } from './medical-history.component';
import { MedicalHistoryDetailComponent } from './medical-history-detail.component';
import { MedicalHistoryUpdateComponent } from './medical-history-update.component';

@Injectable({ providedIn: 'root' })
export class MedicalHistoryResolve implements Resolve<IMedicalHistory> {
  constructor(private service: MedicalHistoryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMedicalHistory> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((medicalHistory: HttpResponse<MedicalHistory>) => {
          if (medicalHistory.body) {
            return of(medicalHistory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MedicalHistory());
  }
}
@Injectable({ providedIn: 'root' })
export class RecordResolve implements Resolve<IMedicalHistory> {
  constructor(private service: MedicalHistoryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMedicalHistory> | Observable<never> {
    return of(new MedicalHistory());
  }
}

export const medicalHistoryRoute: Routes = [
  {
    path: '',
    component: MedicalHistoryComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MedicalHistories',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MedicalHistoryDetailComponent,
    resolve: {
      medicalHistory: MedicalHistoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MedicalHistories',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MedicalHistoryUpdateComponent,
    resolve: {
      medicalHistory: MedicalHistoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MedicalHistories',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new/record/:idRecord',
    component: MedicalHistoryUpdateComponent,
    resolve: {
      medicalHistory: RecordResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MedicalHistories',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MedicalHistoryUpdateComponent,
    resolve: {
      medicalHistory: MedicalHistoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MedicalHistories',
    },
    canActivate: [UserRouteAccessService],
  },
];
