import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PmanagerTestModule } from '../../../test.module';
import { MedicalHistoryDetailComponent } from 'app/entities/medical-history/medical-history-detail.component';
import { MedicalHistory } from 'app/shared/model/medical-history.model';

describe('Component Tests', () => {
  describe('MedicalHistory Management Detail Component', () => {
    let comp: MedicalHistoryDetailComponent;
    let fixture: ComponentFixture<MedicalHistoryDetailComponent>;
    const route = ({ data: of({ medicalHistory: new MedicalHistory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PmanagerTestModule],
        declarations: [MedicalHistoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(MedicalHistoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MedicalHistoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load medicalHistory on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.medicalHistory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
