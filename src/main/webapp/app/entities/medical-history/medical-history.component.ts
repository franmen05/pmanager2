import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMedicalHistory } from 'app/shared/model/medical-history.model';
import { MedicalHistoryService } from './medical-history.service';
import { MedicalHistoryDeleteDialogComponent } from './medical-history-delete-dialog.component';

@Component({
  selector: 'jhi-medical-history',
  templateUrl: './medical-history.component.html',
})
export class MedicalHistoryComponent implements OnInit, OnDestroy {
  medicalHistories?: IMedicalHistory[];
  eventSubscriber?: Subscription;

  constructor(
    protected medicalHistoryService: MedicalHistoryService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.medicalHistoryService.query().subscribe((res: HttpResponse<IMedicalHistory[]>) => (this.medicalHistories = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMedicalHistories();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMedicalHistory): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMedicalHistories(): void {
    this.eventSubscriber = this.eventManager.subscribe('medicalHistoryListModification', () => this.loadAll());
  }

  delete(medicalHistory: IMedicalHistory): void {
    const modalRef = this.modalService.open(MedicalHistoryDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.medicalHistory = medicalHistory;
  }
}
