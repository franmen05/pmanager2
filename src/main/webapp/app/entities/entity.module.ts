import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'patient',
        loadChildren: () => import('./patient/patient.module').then(m => m.PmanagerPatientModule),
      },
      {
        path: 'record',
        loadChildren: () => import('./record/record.module').then(m => m.PmanagerRecordModule),
      },
      {
        path: 'prescription',
        loadChildren: () => import('./prescription/prescription.module').then(m => m.PmanagerPrescriptionModule),
      },
      {
        path: 'medical-history',
        loadChildren: () => import('./medical-history/medical-history.module').then(m => m.PmanagerMedicalHistoryModule),
      },
      {
        path: 'medical-history-config',
        loadChildren: () =>
          import('./medical-history-config/medical-history-config.module').then(m => m.PmanagerMedicalHistoryConfigModule),
      },
      {
        path: 'turn',
        loadChildren: () => import('./turn/turn.module').then(m => m.PmanagerTurnModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class PmanagerEntityModule {}
