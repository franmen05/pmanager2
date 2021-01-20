import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPatient, Patient } from 'app/shared/model/patient.model';
import { PatientService } from './patient.service';

@Component({
  selector: 'jhi-patient-update',
  templateUrl: './patient-update.component.html',
})
export class PatientUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    firstName: [null, [Validators.required]],
    lastName: [null, [Validators.required]],
    email: [null, []],
    reEnrollment: [],
    phoneNumber: [],
    whatsapp: [],
    cellNumber: [],
    emergencyNumber: [],
    address: [null, [Validators.required]],
    birthDate: [],
    createDate: [null, [Validators.required]],
  });

  constructor(
    protected patientService: PatientService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected router: Router
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ patient }) => {
      if (!patient.id) {
        const today = moment().startOf('day');
        patient.birthDate = today;
        patient.createDate = today;
      }

      this.updateForm(patient);
    });
  }

  updateForm(patient: IPatient): void {
    this.editForm.patchValue({
      id: patient.id,
      firstName: patient.firstName,
      lastName: patient.lastName,
      email: patient.email,
      reEnrollment: patient.reEnrollment,
      phoneNumber: patient.phoneNumber,
      whatsapp: patient.whatsapp,
      cellNumber: patient.cellNumber,
      emergencyNumber: patient.emergencyNumber,
      address: patient.address,
      birthDate: patient.birthDate ? patient.birthDate.format(DATE_TIME_FORMAT) : null,
      createDate: patient.createDate ? patient.createDate.format(DATE_TIME_FORMAT) : null,
      fullName: patient.lastName + ', ' + patient.firstName,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const patient = this.createFromForm();
    if (patient.id !== undefined) {
      this.subscribeToSaveResponse(this.patientService.update(patient));
    } else {
      this.subscribeToSaveResponse(this.patientService.create(patient));
    }
  }

  private createFromForm(): IPatient {
    return {
      ...new Patient(),
      id: this.editForm.get(['id'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      email: this.editForm.get(['email'])!.value,
      reEnrollment: this.editForm.get(['reEnrollment'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      whatsapp: this.editForm.get(['whatsapp'])!.value,
      cellNumber: this.editForm.get(['cellNumber'])!.value,
      emergencyNumber: this.editForm.get(['emergencyNumber'])!.value,
      address: this.editForm.get(['address'])!.value,
      birthDate: this.editForm.get(['birthDate'])!.value ? moment(this.editForm.get(['birthDate'])!.value, DATE_TIME_FORMAT) : undefined,
      createDate: this.editForm.get(['createDate'])!.value ? moment(this.editForm.get(['createDate'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPatient>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    // this.previousState();
    this.router.navigate(['/medical-history/new']);
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
