import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRecordItem } from 'app/shared/model/record-item.model';
import { RecordItemService } from './record-item.service';

@Component({
  templateUrl: './record-item-delete-dialog.component.html',
})
export class RecordItemDeleteDialogComponent {
  recordItem?: IRecordItem;

  constructor(
    protected recordItemService: RecordItemService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.recordItemService.delete(id).subscribe(() => {
      this.eventManager.broadcast('recordItemListModification');
      this.activeModal.close();
    });
  }
}
