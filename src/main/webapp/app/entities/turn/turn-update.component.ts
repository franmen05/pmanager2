import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { debounceTime, distinctUntilChanged, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITurn, Turn } from 'app/shared/model/turn.model';
import { TurnService } from './turn.service';
import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient/patient.service';

const states = [
  'Alabama',
  'Alaska',
  'American Samoa',
  'Arizona',
  'Arkansas',
  'California',
  'Colorado',
  'Connecticut',
  'Delaware',
  'District Of Columbia',
  'Federated States Of Micronesia',
  'Florida',
  'Georgia',
  'Guam',
  'Hawaii',
  'Idaho',
  'Illinois',
  'Indiana',
  'Iowa',
  'Kansas',
  'Kentucky',
  'Louisiana',
  'Maine',
  'Marshall Islands',
  'Maryland',
  'Massachusetts',
  'Michigan',
  'Minnesota',
  'Mississippi',
  'Missouri',
  'Montana',
  'Nebraska',
  'Nevada',
  'New Hampshire',
  'New Jersey',
  'New Mexico',
  'New York',
  'North Carolina',
  'North Dakota',
  'Northern Mariana Islands',
  'Ohio',
  'Oklahoma',
  'Oregon',
  'Palau',
  'Pennsylvania',
  'Puerto Rico',
  'Rhode Island',
  'South Carolina',
  'South Dakota',
  'Tennessee',
  'Texas',
  'Utah',
  'Vermont',
  'Virgin Islands',
  'Virginia',
  'Washington',
  'West Virginia',
  'Wisconsin',
  'Wyoming',
];

@Component({
  selector: 'jhi-turn-update',
  templateUrl: './turn-update.component.html',
})
export class TurnUpdateComponent implements OnInit {
  public model: IPatient = {};

  isSaving = false;
  patients: IPatient[] = [];

  editForm = this.fb.group({
    id: [],
    // position: [],
    createDate: [],
    lastUpdateDate: [],
    patient: [null, [Validators.required]],
  });

  constructor(
    protected turnService: TurnService,
    protected patientService: PatientService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  formatter = (state: IPatient) => ` ${state.lastName}, ${state.firstName}`;

  search = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      map(term =>
        this.patients
          .filter((v: IPatient) => {
            if (v.lastName) {
              v.fullName = `${v.lastName}, ${v.firstName}`;
              // TODO:  Mejorar esta implementacion
              return v.lastName.toLowerCase().includes(term.toLowerCase()) || v.firstName === undefined
                ? {}
                : v.firstName.toLowerCase().includes(term.toLowerCase());
            }
            return false;
          })
          .slice(0, 10)
      )
    );

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ turn }) => {
      if (!turn.id) {
        const today = moment().startOf('day');
        turn.createDate = today;
        turn.lastUpdateDate = today;
      }

      this.updateForm(turn);

      this.patientService.query().subscribe((res: HttpResponse<IPatient[]>) => (this.patients = res.body || []));
    });
  }

  updateForm(turn: ITurn): void {
    this.editForm.patchValue({
      id: turn.id,
      position: turn.position,
      createDate: turn.createDate ? turn.createDate.format(DATE_TIME_FORMAT) : null,
      lastUpdateDate: turn.lastUpdateDate ? turn.lastUpdateDate.format(DATE_TIME_FORMAT) : null,
      status: turn.status,
      patient: turn.patient,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const turn = this.createFromForm();
    if (turn.id !== undefined) {
      this.subscribeToSaveResponse(this.turnService.update(turn));
    } else {
      this.subscribeToSaveResponse(this.turnService.create(turn));
    }
  }

  private createFromForm(): ITurn {
    return {
      ...new Turn(),
      id: this.editForm.get(['id'])!.value,
      // position: this.editForm.get(['position'])!.value,
      createDate: this.editForm.get(['createDate'])!.value ? moment(this.editForm.get(['createDate'])!.value, DATE_TIME_FORMAT) : undefined,
      lastUpdateDate: this.editForm.get(['lastUpdateDate'])!.value
        ? moment(this.editForm.get(['lastUpdateDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      patient: this.editForm.get(['patient'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITurn>>): void {
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
