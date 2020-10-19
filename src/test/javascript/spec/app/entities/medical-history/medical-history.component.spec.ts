import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PmanagerTestModule } from '../../../test.module';
import { MedicalHistoryComponent } from 'app/entities/medical-history/medical-history.component';
import { MedicalHistoryService } from 'app/entities/medical-history/medical-history.service';
import { MedicalHistory } from 'app/shared/model/medical-history.model';

describe('Component Tests', () => {
  describe('MedicalHistory Management Component', () => {
    let comp: MedicalHistoryComponent;
    let fixture: ComponentFixture<MedicalHistoryComponent>;
    let service: MedicalHistoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PmanagerTestModule],
        declarations: [MedicalHistoryComponent],
      })
        .overrideTemplate(MedicalHistoryComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MedicalHistoryComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MedicalHistoryService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new MedicalHistory(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.medicalHistories && comp.medicalHistories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
