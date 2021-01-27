import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, EMPTY } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IMedicalHistory, MedicalHistory } from 'app/shared/model/medical-history.model';
import { MedicalHistoryService } from './medical-history.service';
import { IRecord, Record } from 'app/shared/model/record.model';
import { RecordService } from 'app/entities/record/record.service';

@Component({
  selector: 'jhi-medical-history-update',
  templateUrl: './medical-history-update.component.html',
})
export class MedicalHistoryUpdateComponent implements OnInit {
  isSaving = false;
  isFromPatientModule = false;
  records: IRecord[] = [];

  editForm = this.fb.group({
    id: [],
    comment: [null, [Validators.required]],
    createDate: [],
    lastUpdateDate: [null, [Validators.required]],
    record: [],
  });

  constructor(
    protected medicalHistoryService: MedicalHistoryService,
    protected recordService: RecordService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ medicalHistory }) => {
      if (!medicalHistory.id) {
        const today = moment().startOf('day');
        medicalHistory.createDate = today;
        medicalHistory.lastUpdateDate = today;
      }

      const recordId = this.activatedRoute.snapshot.params['idRecord'];
      if (recordId) {
        this.recordService.find(recordId).subscribe((res: HttpResponse<IRecord>) => {
          this.records.push(res.body || new Record(recordId));
          medicalHistory.record = res.body;
          this.isFromPatientModule = true;
          this.updateForm(medicalHistory);
        });
      } else {
        this.recordService.query().subscribe((res: HttpResponse<IRecord[]>) => (this.records = res.body || []));
        this.updateForm(medicalHistory);
      }
    });
  }

  updateForm(medicalHistory: IMedicalHistory): void {
    this.editForm.patchValue({
      id: medicalHistory.id,
      comment: medicalHistory.comment,
      createDate: medicalHistory.createDate ? medicalHistory.createDate.format(DATE_TIME_FORMAT) : null,
      lastUpdateDate: medicalHistory.lastUpdateDate ? medicalHistory.lastUpdateDate.format(DATE_TIME_FORMAT) : null,
      record: medicalHistory.record,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const medicalHistory = this.createFromForm();
    if (medicalHistory.id !== undefined) {
      this.subscribeToSaveResponse(this.medicalHistoryService.update(medicalHistory));
    } else {
      this.subscribeToSaveResponse(this.medicalHistoryService.create(medicalHistory));
    }
  }

  private createFromForm(): IMedicalHistory {
    return {
      ...new MedicalHistory(),
      id: this.editForm.get(['id'])!.value,
      comment: this.editForm.get(['comment'])!.value,
      createDate: this.editForm.get(['createDate'])!.value ? moment(this.editForm.get(['createDate'])!.value, DATE_TIME_FORMAT) : undefined,
      lastUpdateDate: this.editForm.get(['lastUpdateDate'])!.value
        ? moment(this.editForm.get(['lastUpdateDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      record: this.editForm.get(['record'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMedicalHistory>>): void {
    result.subscribe(
      a => this.onSaveSuccess(a),
      () => this.onSaveError()
    );
  }
  protected onSaveSuccess(a: any): void {
    console.debug(a);
    this.isSaving = false;
    this.isFromPatientModule = false;
    // this.previousState();
    this.router.navigate([`/record-item/patient/${a.body.record.patient.id}`]);
  }

  protected onSaveError(): void {
    this.isSaving = false;
    this.isFromPatientModule = false;
  }

  trackById(index: number, item: IRecord): any {
    return item.id;
  }
}
