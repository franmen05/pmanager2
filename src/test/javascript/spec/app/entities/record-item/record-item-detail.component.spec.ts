import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PmanagerTestModule } from '../../../test.module';
import { RecordItemDetailComponent } from 'app/entities/record-item/record-item-detail.component';
import { RecordItem } from 'app/shared/model/record-item.model';

describe('Component Tests', () => {
  describe('RecordItem Management Detail Component', () => {
    let comp: RecordItemDetailComponent;
    let fixture: ComponentFixture<RecordItemDetailComponent>;
    const route = ({ data: of({ recordItem: new RecordItem(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PmanagerTestModule],
        declarations: [RecordItemDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RecordItemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RecordItemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load recordItem on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.recordItem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
