import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PmanagerTestModule } from '../../../test.module';
import { MedicalHistoryConfigUpdateComponent } from 'app/entities/medical-history-config/medical-history-config-update.component';
import { MedicalHistoryConfigService } from 'app/entities/medical-history-config/medical-history-config.service';
import { MedicalHistoryConfig } from 'app/shared/model/medical-history-config.model';

describe('Component Tests', () => {
  describe('MedicalHistoryConfig Management Update Component', () => {
    let comp: MedicalHistoryConfigUpdateComponent;
    let fixture: ComponentFixture<MedicalHistoryConfigUpdateComponent>;
    let service: MedicalHistoryConfigService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PmanagerTestModule],
        declarations: [MedicalHistoryConfigUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(MedicalHistoryConfigUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MedicalHistoryConfigUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MedicalHistoryConfigService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MedicalHistoryConfig(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new MedicalHistoryConfig();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
