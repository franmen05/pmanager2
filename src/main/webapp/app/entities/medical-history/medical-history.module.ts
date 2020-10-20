import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PmanagerSharedModule } from 'app/shared/shared.module';
import { MedicalHistoryComponent } from './medical-history.component';
import { MedicalHistoryDetailComponent } from './medical-history-detail.component';
import { MedicalHistoryUpdateComponent } from './medical-history-update.component';
import { MedicalHistoryDeleteDialogComponent } from './medical-history-delete-dialog.component';
import { medicalHistoryRoute } from './medical-history.route';

@NgModule({
  imports: [PmanagerSharedModule, RouterModule.forChild(medicalHistoryRoute)],
  declarations: [
    MedicalHistoryComponent,
    MedicalHistoryDetailComponent,
    MedicalHistoryUpdateComponent,
    MedicalHistoryDeleteDialogComponent,
  ],
  entryComponents: [MedicalHistoryDeleteDialogComponent],
})
export class PmanagerMedicalHistoryModule {}
