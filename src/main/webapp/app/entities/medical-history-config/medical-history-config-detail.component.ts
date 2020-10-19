import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMedicalHistoryConfig } from 'app/shared/model/medical-history-config.model';

@Component({
  selector: 'jhi-medical-history-config-detail',
  templateUrl: './medical-history-config-detail.component.html',
})
export class MedicalHistoryConfigDetailComponent implements OnInit {
  medicalHistoryConfig: IMedicalHistoryConfig | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ medicalHistoryConfig }) => (this.medicalHistoryConfig = medicalHistoryConfig));
  }

  previousState(): void {
    window.history.back();
  }
}
