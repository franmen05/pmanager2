import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PmanagerSharedModule } from 'app/shared/shared.module';
import { RecordItemComponent } from './record-item.component';
import { RecordItemDetailComponent } from './record-item-detail.component';
import { RecordItemUpdateComponent } from './record-item-update.component';
import { RecordItemDeleteDialogComponent } from './record-item-delete-dialog.component';
import { recordItemRoute } from './record-item.route';

@NgModule({
  imports: [PmanagerSharedModule, RouterModule.forChild(recordItemRoute)],
  declarations: [RecordItemComponent, RecordItemDetailComponent, RecordItemUpdateComponent, RecordItemDeleteDialogComponent],
  entryComponents: [RecordItemDeleteDialogComponent],
})
export class PmanagerRecordItemModule {}
