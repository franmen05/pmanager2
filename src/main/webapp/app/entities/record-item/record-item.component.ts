import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRecordItem } from 'app/shared/model/record-item.model';
import { RecordItemService } from './record-item.service';
import { RecordItemDeleteDialogComponent } from './record-item-delete-dialog.component';
import { ActivatedRoute } from '@angular/router';
import { PatientService } from '../patient/patient.service';
import { IPatient } from '../../shared/model/patient.model';

@Component({
  selector: 'jhi-record-item',
  templateUrl: './record-item.component.html',
})
export class RecordItemComponent implements OnInit, OnDestroy {
  recordItems?: IRecordItem[];
  eventSubscriber?: Subscription;
  idPatient?: number;
  idRecord?: number;

  constructor(
    protected recordItemService: RecordItemService,
    protected patientService: PatientService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    private route: ActivatedRoute
  ) {}

  loadAll(): void {
    this.idPatient = this.route.snapshot.params['idPatient'];
    // this.recordItemService.query().subscribe((res: HttpResponse<IRecordItem[]>) => (this.recordItems = res.body || []));
    if (this.idPatient) {
      this.recordItemService
        .findAllByPatient(this.idPatient)
        .subscribe((res: HttpResponse<IRecordItem[]>) => (this.recordItems = res.body || []));
      if (this.recordItems) this.idRecord = this.recordItems.values().next().value.record?.id;
      else
        this.patientService
          .find(this.idPatient)
          .subscribe((res: HttpResponse<IPatient>) => (res.body?.records ? (this.idRecord = res.body.records[0].id) : undefined));
    }
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInRecordItems();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IRecordItem): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInRecordItems(): void {
    this.eventSubscriber = this.eventManager.subscribe('recordItemListModification', () => this.loadAll());
  }

  delete(recordItem: IRecordItem): void {
    const modalRef = this.modalService.open(RecordItemDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.recordItem = recordItem;
  }
}
