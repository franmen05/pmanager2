import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PmanagerTestModule } from '../../../test.module';
import { MedicalHistoryUpdateComponent } from 'app/entities/medical-history/medical-history-update.component';
import { MedicalHistoryService } from 'app/entities/medical-history/medical-history.service';
import { MedicalHistory } from 'app/shared/model/medical-history.model';

describe('Component Tests', () => {
  describe('MedicalHistory Management Update Component', () => {
    let comp: MedicalHistoryUpdateComponent;
    let fixture: ComponentFixture<MedicalHistoryUpdateComponent>;
    let service: MedicalHistoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PmanagerTestModule],
        declarations: [MedicalHistoryUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(MedicalHistoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MedicalHistoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MedicalHistoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MedicalHistory(123);
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
        const entity = new MedicalHistory();
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
