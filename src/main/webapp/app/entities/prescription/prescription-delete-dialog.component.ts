import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPrescription } from 'app/shared/model/prescription.model';
import { PrescriptionService } from './prescription.service';

@Component({
  templateUrl: './prescription-delete-dialog.component.html',
})
export class PrescriptionDeleteDialogComponent {
  prescription?: IPrescription;

  constructor(
    protected prescriptionService: PrescriptionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.prescriptionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('prescriptionListModification');
      this.activeModal.close();
    });
  }
}
