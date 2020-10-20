import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PmanagerSharedModule } from 'app/shared/shared.module';
import { PrescriptionComponent } from './prescription.component';
import { PrescriptionDetailComponent } from './prescription-detail.component';
import { PrescriptionUpdateComponent } from './prescription-update.component';
import { PrescriptionDeleteDialogComponent } from './prescription-delete-dialog.component';
import { prescriptionRoute } from './prescription.route';

@NgModule({
  imports: [PmanagerSharedModule, RouterModule.forChild(prescriptionRoute)],
  declarations: [PrescriptionComponent, PrescriptionDetailComponent, PrescriptionUpdateComponent, PrescriptionDeleteDialogComponent],
  entryComponents: [PrescriptionDeleteDialogComponent],
})
export class PmanagerPrescriptionModule {}
