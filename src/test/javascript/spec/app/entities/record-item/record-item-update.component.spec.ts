import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PmanagerTestModule } from '../../../test.module';
import { RecordItemUpdateComponent } from 'app/entities/record-item/record-item-update.component';
import { RecordItemService } from 'app/entities/record-item/record-item.service';
import { RecordItem } from 'app/shared/model/record-item.model';

describe('Component Tests', () => {
  describe('RecordItem Management Update Component', () => {
    let comp: RecordItemUpdateComponent;
    let fixture: ComponentFixture<RecordItemUpdateComponent>;
    let service: RecordItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PmanagerTestModule],
        declarations: [RecordItemUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RecordItemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RecordItemUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RecordItemService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RecordItem(123);
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
        const entity = new RecordItem();
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
