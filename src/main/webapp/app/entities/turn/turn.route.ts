import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITurn, Turn } from 'app/shared/model/turn.model';
import { TurnService } from './turn.service';
import { TurnComponent } from './turn.component';
import { TurnDetailComponent } from './turn-detail.component';
import { TurnUpdateComponent } from './turn-update.component';

@Injectable({ providedIn: 'root' })
export class TurnResolve implements Resolve<ITurn> {
  constructor(private service: TurnService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITurn> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((turn: HttpResponse<Turn>) => {
          if (turn.body) {
            return of(turn.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Turn());
  }
}

export const turnRoute: Routes = [
  {
    path: '',
    component: TurnComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Turns',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TurnDetailComponent,
    resolve: {
      turn: TurnResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Turns',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TurnUpdateComponent,
    resolve: {
      turn: TurnResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Turns',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TurnUpdateComponent,
    resolve: {
      turn: TurnResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Turns',
    },
    canActivate: [UserRouteAccessService],
  },
];
