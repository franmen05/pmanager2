import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRecordItem } from 'app/shared/model/record-item.model';
import { RecordItemService } from './record-item.service';
import { RecordItemDeleteDialogComponent } from './record-item-delete-dialog.component';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'jhi-record-item',
  templateUrl: './record-item.component.html',
})
export class RecordItemComponent implements OnInit, OnDestroy {
  recordItems?: IRecordItem[];
  eventSubscriber?: Subscription;

  constructor(
    protected recordItemService: RecordItemService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    private route: ActivatedRoute
  ) {}

  loadAll(): void {
    const id = this.route.snapshot.params['id'];
    // this.recordItemService.query().subscribe((res: HttpResponse<IRecordItem[]>) => (this.recordItems = res.body || []));
    this.recordItemService.findAllByPatient(id).subscribe((res: HttpResponse<IRecordItem[]>) => (this.recordItems = res.body || []));
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
