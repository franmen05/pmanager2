import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IRecordItem, RecordItem } from 'app/shared/model/record-item.model';
import { RecordItemService } from './record-item.service';
import { IRecord } from 'app/shared/model/record.model';
import { RecordService } from 'app/entities/record/record.service';

@Component({
  selector: 'jhi-record-item-update',
  templateUrl: './record-item-update.component.html',
})
export class RecordItemUpdateComponent implements OnInit {
  isSaving = false;
  records: IRecord[] = [];
  recordId?: number;

  editForm = this.fb.group({
    id: [],
    description: [null, [Validators.required]],
    createDate: [],
    lastUpdateDate: [null, [Validators.required]],
    record: [],
  });

  constructor(
    protected recordItemService: RecordItemService,
    protected recordService: RecordService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.recordId = this.activatedRoute.snapshot.params['idRecord'];

    this.activatedRoute.data.subscribe(({ recordItem }) => {
      if (!recordItem.id) {
        const today = moment().startOf('day');
        recordItem.createDate = today;
        recordItem.lastUpdateDate = today;
      }

      this.updateForm(recordItem);
      this.recordService.query().subscribe((res: HttpResponse<IRecord[]>) => (this.records = res.body || []));
    });
  }

  updateForm(recordItem: IRecordItem): void {
    this.editForm.patchValue({
      id: recordItem.id,
      description: recordItem.description,
      createDate: recordItem.createDate ? recordItem.createDate.format(DATE_TIME_FORMAT) : null,
      lastUpdateDate: recordItem.lastUpdateDate ? recordItem.lastUpdateDate.format(DATE_TIME_FORMAT) : null,
      record: recordItem.record,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const recordItem = this.createFromForm();
    if (recordItem.id !== undefined) {
      this.subscribeToSaveResponse(this.recordItemService.update(recordItem));
    } else {
      if (!recordItem.record) {
        const r = new RecordItem();
        r.id = this.recordId;
        recordItem.record = r;
      }
      this.subscribeToSaveResponse(this.recordItemService.create(recordItem));
    }
  }

  private createFromForm(): IRecordItem {
    return {
      ...new RecordItem(),
      id: this.editForm.get(['id'])!.value,
      description: this.editForm.get(['description'])!.value,
      createDate: this.editForm.get(['createDate'])!.value ? moment(this.editForm.get(['createDate'])!.value, DATE_TIME_FORMAT) : undefined,
      lastUpdateDate: this.editForm.get(['lastUpdateDate'])!.value
        ? moment(this.editForm.get(['lastUpdateDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      record: this.editForm.get(['record'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRecordItem>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IRecord): any {
    return item.id;
  }
}
