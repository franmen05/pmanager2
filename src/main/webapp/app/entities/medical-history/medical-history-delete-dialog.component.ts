import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMedicalHistory } from 'app/shared/model/medical-history.model';
import { MedicalHistoryService } from './medical-history.service';

@Component({
  templateUrl: './medical-history-delete-dialog.component.html',
})
export class MedicalHistoryDeleteDialogComponent {
  medicalHistory?: IMedicalHistory;

  constructor(
    protected medicalHistoryService: MedicalHistoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.medicalHistoryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('medicalHistoryListModification');
      this.activeModal.close();
    });
  }
}
