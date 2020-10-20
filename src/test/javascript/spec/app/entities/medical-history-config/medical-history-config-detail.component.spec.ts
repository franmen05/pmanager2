import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PmanagerTestModule } from '../../../test.module';
import { MedicalHistoryConfigDetailComponent } from 'app/entities/medical-history-config/medical-history-config-detail.component';
import { MedicalHistoryConfig } from 'app/shared/model/medical-history-config.model';

describe('Component Tests', () => {
  describe('MedicalHistoryConfig Management Detail Component', () => {
    let comp: MedicalHistoryConfigDetailComponent;
    let fixture: ComponentFixture<MedicalHistoryConfigDetailComponent>;
    const route = ({ data: of({ medicalHistoryConfig: new MedicalHistoryConfig(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PmanagerTestModule],
        declarations: [MedicalHistoryConfigDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(MedicalHistoryConfigDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MedicalHistoryConfigDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load medicalHistoryConfig on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.medicalHistoryConfig).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
