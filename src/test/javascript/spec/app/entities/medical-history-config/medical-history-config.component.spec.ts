import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PmanagerTestModule } from '../../../test.module';
import { MedicalHistoryConfigComponent } from 'app/entities/medical-history-config/medical-history-config.component';
import { MedicalHistoryConfigService } from 'app/entities/medical-history-config/medical-history-config.service';
import { MedicalHistoryConfig } from 'app/shared/model/medical-history-config.model';

describe('Component Tests', () => {
  describe('MedicalHistoryConfig Management Component', () => {
    let comp: MedicalHistoryConfigComponent;
    let fixture: ComponentFixture<MedicalHistoryConfigComponent>;
    let service: MedicalHistoryConfigService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PmanagerTestModule],
        declarations: [MedicalHistoryConfigComponent],
      })
        .overrideTemplate(MedicalHistoryConfigComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MedicalHistoryConfigComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MedicalHistoryConfigService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new MedicalHistoryConfig(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.medicalHistoryConfigs && comp.medicalHistoryConfigs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
