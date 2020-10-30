import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRecordItem } from 'app/shared/model/record-item.model';

@Component({
  selector: 'jhi-record-item-detail',
  templateUrl: './record-item-detail.component.html',
})
export class RecordItemDetailComponent implements OnInit {
  recordItem: IRecordItem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ recordItem }) => (this.recordItem = recordItem));
  }

  previousState(): void {
    window.history.back();
  }
}
