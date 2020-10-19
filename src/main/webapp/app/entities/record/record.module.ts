import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PmanagerSharedModule } from 'app/shared/shared.module';
import { RecordComponent } from './record.component';
import { RecordDetailComponent } from './record-detail.component';
import { RecordUpdateComponent } from './record-update.component';
import { RecordDeleteDialogComponent } from './record-delete-dialog.component';
import { recordRoute } from './record.route';

@NgModule({
  imports: [PmanagerSharedModule, RouterModule.forChild(recordRoute)],
  declarations: [RecordComponent, RecordDetailComponent, RecordUpdateComponent, RecordDeleteDialogComponent],
  entryComponents: [RecordDeleteDialogComponent],
})
export class PmanagerRecordModule {}
