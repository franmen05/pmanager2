import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITurn } from 'app/shared/model/turn.model';
import { TurnService } from './turn.service';
import { TurnDeleteDialogComponent } from './turn-delete-dialog.component';

@Component({
  selector: 'jhi-turn',
  templateUrl: './turn.component.html',
})
export class TurnHistoryComponent implements OnInit, OnDestroy {
  turns?: ITurn[];
  eventSubscriber?: Subscription;

  constructor(protected turnService: TurnService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.turnService.query().subscribe((res: HttpResponse<ITurn[]>) => (this.turns = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTurns();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITurn): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTurns(): void {
    this.eventSubscriber = this.eventManager.subscribe('turnListModification', () => this.loadAll());
  }

  delete(turn: ITurn): void {
    const modalRef = this.modalService.open(TurnDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.turn = turn;
  }
}
