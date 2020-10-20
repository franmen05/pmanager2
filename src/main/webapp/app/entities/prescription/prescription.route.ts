import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPrescription, Prescription } from 'app/shared/model/prescription.model';
import { PrescriptionService } from './prescription.service';
import { PrescriptionComponent } from './prescription.component';
import { PrescriptionDetailComponent } from './prescription-detail.component';
import { PrescriptionUpdateComponent } from './prescription-update.component';

@Injectable({ providedIn: 'root' })
export class PrescriptionResolve implements Resolve<IPrescription> {
  constructor(private service: PrescriptionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPrescription> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((prescription: HttpResponse<Prescription>) => {
          if (prescription.body) {
            return of(prescription.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Prescription());
  }
}

export const prescriptionRoute: Routes = [
  {
    path: '',
    component: PrescriptionComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Prescriptions',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PrescriptionDetailComponent,
    resolve: {
      prescription: PrescriptionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Prescriptions',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PrescriptionUpdateComponent,
    resolve: {
      prescription: PrescriptionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Prescriptions',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PrescriptionUpdateComponent,
    resolve: {
      prescription: PrescriptionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Prescriptions',
    },
    canActivate: [UserRouteAccessService],
  },
];
