import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPrescription } from 'app/shared/model/prescription.model';
import { PrescriptionService } from './prescription.service';
import { PrescriptionDeleteDialogComponent } from './prescription-delete-dialog.component';

@Component({
  selector: 'jhi-prescription',
  templateUrl: './prescription.component.html',
})
export class PrescriptionComponent implements OnInit, OnDestroy {
  prescriptions?: IPrescription[];
  eventSubscriber?: Subscription;

  constructor(
    protected prescriptionService: PrescriptionService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.prescriptionService.query().subscribe((res: HttpResponse<IPrescription[]>) => (this.prescriptions = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPrescriptions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPrescription): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPrescriptions(): void {
    this.eventSubscriber = this.eventManager.subscribe('prescriptionListModification', () => this.loadAll());
  }

  delete(prescription: IPrescription): void {
    const modalRef = this.modalService.open(PrescriptionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.prescription = prescription;
  }
}
