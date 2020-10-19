import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IRecord, Record } from 'app/shared/model/record.model';
import { RecordService } from './record.service';
import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient/patient.service';

@Component({
  selector: 'jhi-record-update',
  templateUrl: './record-update.component.html',
})
export class RecordUpdateComponent implements OnInit {
  isSaving = false;
  patients: IPatient[] = [];

  editForm = this.fb.group({
    id: [],
    description: [null, [Validators.required]],
    createDate: [],
    lastUpdateDate: [null, [Validators.required]],
    patient: [],
    patient: [],
  });

  constructor(
    protected recordService: RecordService,
    protected patientService: PatientService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ record }) => {
      if (!record.id) {
        const today = moment().startOf('day');
        record.createDate = today;
        record.lastUpdateDate = today;
      }

      this.updateForm(record);

      this.patientService.query().subscribe((res: HttpResponse<IPatient[]>) => (this.patients = res.body || []));
    });
  }

  updateForm(record: IRecord): void {
    this.editForm.patchValue({
      id: record.id,
      description: record.description,
      createDate: record.createDate ? record.createDate.format(DATE_TIME_FORMAT) : null,
      lastUpdateDate: record.lastUpdateDate ? record.lastUpdateDate.format(DATE_TIME_FORMAT) : null,
      patient: record.patient,
      patient: record.patient,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const record = this.createFromForm();
    if (record.id !== undefined) {
      this.subscribeToSaveResponse(this.recordService.update(record));
    } else {
      this.subscribeToSaveResponse(this.recordService.create(record));
    }
  }

  private createFromForm(): IRecord {
    return {
      ...new Record(),
      id: this.editForm.get(['id'])!.value,
      description: this.editForm.get(['description'])!.value,
      createDate: this.editForm.get(['createDate'])!.value ? moment(this.editForm.get(['createDate'])!.value, DATE_TIME_FORMAT) : undefined,
      lastUpdateDate: this.editForm.get(['lastUpdateDate'])!.value
        ? moment(this.editForm.get(['lastUpdateDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      patient: this.editForm.get(['patient'])!.value,
      patient: this.editForm.get(['patient'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRecord>>): void {
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

  trackById(index: number, item: IPatient): any {
    return item.id;
  }
}
