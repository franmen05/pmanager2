import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PmanagerSharedModule } from 'app/shared/shared.module';
import { TurnComponent } from './turn.component';
import { TurnDetailComponent } from './turn-detail.component';
import { TurnUpdateComponent } from './turn-update.component';
import { TurnDeleteDialogComponent } from './turn-delete-dialog.component';
import { turnRoute } from './turn.route';

@NgModule({
  imports: [PmanagerSharedModule, RouterModule.forChild(turnRoute)],
  declarations: [TurnComponent, TurnDetailComponent, TurnUpdateComponent, TurnDeleteDialogComponent],
  entryComponents: [TurnDeleteDialogComponent],
})
export class PmanagerTurnModule {}
