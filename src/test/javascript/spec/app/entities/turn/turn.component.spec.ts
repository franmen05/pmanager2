import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PmanagerTestModule } from '../../../test.module';
import { TurnComponent } from 'app/entities/turn/turn.component';
import { TurnService } from 'app/entities/turn/turn.service';
import { Turn } from 'app/shared/model/turn.model';

describe('Component Tests', () => {
  describe('Turn Management Component', () => {
    let comp: TurnComponent;
    let fixture: ComponentFixture<TurnComponent>;
    let service: TurnService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PmanagerTestModule],
        declarations: [TurnComponent],
      })
        .overrideTemplate(TurnComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TurnComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TurnService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Turn(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.turns && comp.turns[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
