import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PmanagerTestModule } from '../../../test.module';
import { RecordItemComponent } from 'app/entities/record-item/record-item.component';
import { RecordItemService } from 'app/entities/record-item/record-item.service';
import { RecordItem } from 'app/shared/model/record-item.model';

describe('Component Tests', () => {
  describe('RecordItem Management Component', () => {
    let comp: RecordItemComponent;
    let fixture: ComponentFixture<RecordItemComponent>;
    let service: RecordItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PmanagerTestModule],
        declarations: [RecordItemComponent],
      })
        .overrideTemplate(RecordItemComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RecordItemComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RecordItemService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new RecordItem(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.recordItems && comp.recordItems[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
