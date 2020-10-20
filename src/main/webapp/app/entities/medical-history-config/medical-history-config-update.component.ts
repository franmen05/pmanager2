import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IMedicalHistoryConfig, MedicalHistoryConfig } from 'app/shared/model/medical-history-config.model';
import { MedicalHistoryConfigService } from './medical-history-config.service';
import { IMedicalHistory } from 'app/shared/model/medical-history.model';
import { MedicalHistoryService } from 'app/entities/medical-history/medical-history.service';

@Component({
  selector: 'jhi-medical-history-config-update',
  templateUrl: './medical-history-config-update.component.html',
})
export class MedicalHistoryConfigUpdateComponent implements OnInit {
  isSaving = false;
  medicalhistories: IMedicalHistory[] = [];

  editForm = this.fb.group({
    id: [],
    key: [null, [Validators.required]],
    description: [null, [Validators.required]],
    createDate: [],
    lastUpdateDate: [null, [Validators.required]],
    medicalHistory: [],
  });

  constructor(
    protected medicalHistoryConfigService: MedicalHistoryConfigService,
    protected medicalHistoryService: MedicalHistoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ medicalHistoryConfig }) => {
      if (!medicalHistoryConfig.id) {
        const today = moment().startOf('day');
        medicalHistoryConfig.createDate = today;
        medicalHistoryConfig.lastUpdateDate = today;
      }

      this.updateForm(medicalHistoryConfig);

      this.medicalHistoryService.query().subscribe((res: HttpResponse<IMedicalHistory[]>) => (this.medicalhistories = res.body || []));
    });
  }

  updateForm(medicalHistoryConfig: IMedicalHistoryConfig): void {
    this.editForm.patchValue({
      id: medicalHistoryConfig.id,
      key: medicalHistoryConfig.key,
      description: medicalHistoryConfig.description,
      createDate: medicalHistoryConfig.createDate ? medicalHistoryConfig.createDate.format(DATE_TIME_FORMAT) : null,
      lastUpdateDate: medicalHistoryConfig.lastUpdateDate ? medicalHistoryConfig.lastUpdateDate.format(DATE_TIME_FORMAT) : null,
      medicalHistory: medicalHistoryConfig.medicalHistory,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const medicalHistoryConfig = this.createFromForm();
    if (medicalHistoryConfig.id !== undefined) {
      this.subscribeToSaveResponse(this.medicalHistoryConfigService.update(medicalHistoryConfig));
    } else {
      this.subscribeToSaveResponse(this.medicalHistoryConfigService.create(medicalHistoryConfig));
    }
  }

  private createFromForm(): IMedicalHistoryConfig {
    return {
      ...new MedicalHistoryConfig(),
      id: this.editForm.get(['id'])!.value,
      key: this.editForm.get(['key'])!.value,
      description: this.editForm.get(['description'])!.value,
      createDate: this.editForm.get(['createDate'])!.value ? moment(this.editForm.get(['createDate'])!.value, DATE_TIME_FORMAT) : undefined,
      lastUpdateDate: this.editForm.get(['lastUpdateDate'])!.value
        ? moment(this.editForm.get(['lastUpdateDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      medicalHistory: this.editForm.get(['medicalHistory'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMedicalHistoryConfig>>): void {
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

  trackById(index: number, item: IMedicalHistory): any {
    return item.id;
  }
}
