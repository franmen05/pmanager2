import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMedicalHistoryConfig } from 'app/shared/model/medical-history-config.model';
import { MedicalHistoryConfigService } from './medical-history-config.service';

@Component({
  templateUrl: './medical-history-config-delete-dialog.component.html',
})
export class MedicalHistoryConfigDeleteDialogComponent {
  medicalHistoryConfig?: IMedicalHistoryConfig;

  constructor(
    protected medicalHistoryConfigService: MedicalHistoryConfigService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.medicalHistoryConfigService.delete(id).subscribe(() => {
      this.eventManager.broadcast('medicalHistoryConfigListModification');
      this.activeModal.close();
    });
  }
}
