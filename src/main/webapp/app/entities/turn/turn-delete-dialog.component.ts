import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITurn } from 'app/shared/model/turn.model';
import { TurnService } from './turn.service';

@Component({
  templateUrl: './turn-delete-dialog.component.html',
})
export class TurnDeleteDialogComponent {
  turn?: ITurn;

  constructor(protected turnService: TurnService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.turnService.delete(id).subscribe(() => {
      this.eventManager.broadcast('turnListModification');
      this.activeModal.close();
    });
  }
}
