import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPrescription, Prescription } from 'app/shared/model/prescription.model';
import { PrescriptionService } from './prescription.service';
import { IRecord } from 'app/shared/model/record.model';
import { RecordService } from 'app/entities/record/record.service';

@Component({
  selector: 'jhi-prescription-update',
  templateUrl: './prescription-update.component.html',
})
export class PrescriptionUpdateComponent implements OnInit {
  isSaving = false;
  records: IRecord[] = [];

  editForm = this.fb.group({
    id: [],
    description: [null, [Validators.required]],
    createDate: [],
    lastUpdateDate: [null, [Validators.required]],
    record: [],
  });

  constructor(
    protected prescriptionService: PrescriptionService,
    protected recordService: RecordService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ prescription }) => {
      if (!prescription.id) {
        const today = moment().startOf('day');
        prescription.createDate = today;
        prescription.lastUpdateDate = today;
      }

      this.updateForm(prescription);

      this.recordService.query().subscribe((res: HttpResponse<IRecord[]>) => (this.records = res.body || []));
    });
  }

  updateForm(prescription: IPrescription): void {
    this.editForm.patchValue({
      id: prescription.id,
      description: prescription.description,
      createDate: prescription.createDate ? prescription.createDate.format(DATE_TIME_FORMAT) : null,
      lastUpdateDate: prescription.lastUpdateDate ? prescription.lastUpdateDate.format(DATE_TIME_FORMAT) : null,
      record: prescription.record,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const prescription = this.createFromForm();
    if (prescription.id !== undefined) {
      this.subscribeToSaveResponse(this.prescriptionService.update(prescription));
    } else {
      this.subscribeToSaveResponse(this.prescriptionService.create(prescription));
    }
  }

  private createFromForm(): IPrescription {
    return {
      ...new Prescription(),
      id: this.editForm.get(['id'])!.value,
      description: this.editForm.get(['description'])!.value,
      createDate: this.editForm.get(['createDate'])!.value ? moment(this.editForm.get(['createDate'])!.value, DATE_TIME_FORMAT) : undefined,
      lastUpdateDate: this.editForm.get(['lastUpdateDate'])!.value
        ? moment(this.editForm.get(['lastUpdateDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      record: this.editForm.get(['record'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrescription>>): void {
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
