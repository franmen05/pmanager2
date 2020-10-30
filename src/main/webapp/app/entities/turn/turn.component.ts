import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITurn } from 'app/shared/model/turn.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TurnService } from './turn.service';
import { TurnDeleteDialogComponent } from './turn-delete-dialog.component';

@Component({
  selector: 'jhi-turn',
  templateUrl: './turn.component.html',
})
export class TurnComponent implements OnInit, OnDestroy {
  turns: ITurn[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected turnService: TurnService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.turns = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.turnService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<ITurn[]>) => this.paginateTurns(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.turns = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
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
    this.eventSubscriber = this.eventManager.subscribe('turnListModification', () => this.reset());
  }

  delete(turn: ITurn): void {
    const modalRef = this.modalService.open(TurnDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.turn = turn;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTurns(data: ITurn[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.turns.push(data[i]);
      }
    }
  }
}
