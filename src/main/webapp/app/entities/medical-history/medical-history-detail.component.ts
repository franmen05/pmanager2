import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMedicalHistory } from 'app/shared/model/medical-history.model';
import { MedicalHistoryService } from './medical-history.service';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-medical-history-detail',
  templateUrl: './medical-history-detail.component.html',
})
export class MedicalHistoryDetailComponent implements OnInit {
  medicalHistory: IMedicalHistory | null = null;
  medicalHistories: IMedicalHistory[] | null = [];

  constructor(protected activatedRoute: ActivatedRoute, protected medicalHistoryService: MedicalHistoryService) {}

  ngOnInit(): void {
    // this.activatedRoute.data.subscribe(({ medicalHistory }) => (this.medicalHistory = medicalHistory));
    const recordId = this.activatedRoute.snapshot.params['idRecord'];

    this.medicalHistoryService.findAll(recordId).subscribe((res2: HttpResponse<IMedicalHistory[]>) => (this.medicalHistories = res2.body));
  }

  previousState(): void {
    window.history.back();
  }
}
