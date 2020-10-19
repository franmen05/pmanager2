import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PmanagerSharedModule } from 'app/shared/shared.module';
import { MedicalHistoryConfigComponent } from './medical-history-config.component';
import { MedicalHistoryConfigDetailComponent } from './medical-history-config-detail.component';
import { MedicalHistoryConfigUpdateComponent } from './medical-history-config-update.component';
import { MedicalHistoryConfigDeleteDialogComponent } from './medical-history-config-delete-dialog.component';
import { medicalHistoryConfigRoute } from './medical-history-config.route';

@NgModule({
  imports: [PmanagerSharedModule, RouterModule.forChild(medicalHistoryConfigRoute)],
  declarations: [
    MedicalHistoryConfigComponent,
    MedicalHistoryConfigDetailComponent,
    MedicalHistoryConfigUpdateComponent,
    MedicalHistoryConfigDeleteDialogComponent,
  ],
  entryComponents: [MedicalHistoryConfigDeleteDialogComponent],
})
export class PmanagerMedicalHistoryConfigModule {}
