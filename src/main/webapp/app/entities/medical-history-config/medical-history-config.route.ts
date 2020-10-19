import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMedicalHistoryConfig, MedicalHistoryConfig } from 'app/shared/model/medical-history-config.model';
import { MedicalHistoryConfigService } from './medical-history-config.service';
import { MedicalHistoryConfigComponent } from './medical-history-config.component';
import { MedicalHistoryConfigDetailComponent } from './medical-history-config-detail.component';
import { MedicalHistoryConfigUpdateComponent } from './medical-history-config-update.component';

@Injectable({ providedIn: 'root' })
export class MedicalHistoryConfigResolve implements Resolve<IMedicalHistoryConfig> {
  constructor(private service: MedicalHistoryConfigService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMedicalHistoryConfig> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((medicalHistoryConfig: HttpResponse<MedicalHistoryConfig>) => {
          if (medicalHistoryConfig.body) {
            return of(medicalHistoryConfig.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MedicalHistoryConfig());
  }
}

export const medicalHistoryConfigRoute: Routes = [
  {
    path: '',
    component: MedicalHistoryConfigComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MedicalHistoryConfigs',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MedicalHistoryConfigDetailComponent,
    resolve: {
      medicalHistoryConfig: MedicalHistoryConfigResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MedicalHistoryConfigs',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MedicalHistoryConfigUpdateComponent,
    resolve: {
      medicalHistoryConfig: MedicalHistoryConfigResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MedicalHistoryConfigs',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MedicalHistoryConfigUpdateComponent,
    resolve: {
      medicalHistoryConfig: MedicalHistoryConfigResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MedicalHistoryConfigs',
    },
    canActivate: [UserRouteAccessService],
  },
];
