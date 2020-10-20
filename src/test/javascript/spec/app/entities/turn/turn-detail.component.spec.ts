import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PmanagerTestModule } from '../../../test.module';
import { TurnDetailComponent } from 'app/entities/turn/turn-detail.component';
import { Turn } from 'app/shared/model/turn.model';

describe('Component Tests', () => {
  describe('Turn Management Detail Component', () => {
    let comp: TurnDetailComponent;
    let fixture: ComponentFixture<TurnDetailComponent>;
    const route = ({ data: of({ turn: new Turn(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PmanagerTestModule],
        declarations: [TurnDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TurnDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TurnDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load turn on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.turn).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
