import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMedicalHistoryConfig } from 'app/shared/model/medical-history-config.model';
import { MedicalHistoryConfigService } from './medical-history-config.service';
import { MedicalHistoryConfigDeleteDialogComponent } from './medical-history-config-delete-dialog.component';

@Component({
  selector: 'jhi-medical-history-config',
  templateUrl: './medical-history-config.component.html',
})
export class MedicalHistoryConfigComponent implements OnInit, OnDestroy {
  medicalHistoryConfigs?: IMedicalHistoryConfig[];
  eventSubscriber?: Subscription;

  constructor(
    protected medicalHistoryConfigService: MedicalHistoryConfigService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.medicalHistoryConfigService
      .query()
      .subscribe((res: HttpResponse<IMedicalHistoryConfig[]>) => (this.medicalHistoryConfigs = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMedicalHistoryConfigs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMedicalHistoryConfig): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMedicalHistoryConfigs(): void {
    this.eventSubscriber = this.eventManager.subscribe('medicalHistoryConfigListModification', () => this.loadAll());
  }

  delete(medicalHistoryConfig: IMedicalHistoryConfig): void {
    const modalRef = this.modalService.open(MedicalHistoryConfigDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.medicalHistoryConfig = medicalHistoryConfig;
  }
}
