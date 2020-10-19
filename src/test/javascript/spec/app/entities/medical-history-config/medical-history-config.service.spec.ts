import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { MedicalHistoryConfigService } from 'app/entities/medical-history-config/medical-history-config.service';
import { IMedicalHistoryConfig, MedicalHistoryConfig } from 'app/shared/model/medical-history-config.model';

describe('Service Tests', () => {
  describe('MedicalHistoryConfig Service', () => {
    let injector: TestBed;
    let service: MedicalHistoryConfigService;
    let httpMock: HttpTestingController;
    let elemDefault: IMedicalHistoryConfig;
    let expectedResult: IMedicalHistoryConfig | IMedicalHistoryConfig[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(MedicalHistoryConfigService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new MedicalHistoryConfig(0, 'AAAAAAA', 'AAAAAAA', currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            createDate: currentDate.format(DATE_TIME_FORMAT),
            lastUpdateDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a MedicalHistoryConfig', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            createDate: currentDate.format(DATE_TIME_FORMAT),
            lastUpdateDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createDate: currentDate,
            lastUpdateDate: currentDate,
          },
          returnedFromService
        );

        service.create(new MedicalHistoryConfig()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a MedicalHistoryConfig', () => {
        const returnedFromService = Object.assign(
          {
            key: 'BBBBBB',
            description: 'BBBBBB',
            createDate: currentDate.format(DATE_TIME_FORMAT),
            lastUpdateDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createDate: currentDate,
            lastUpdateDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of MedicalHistoryConfig', () => {
        const returnedFromService = Object.assign(
          {
            key: 'BBBBBB',
            description: 'BBBBBB',
            createDate: currentDate.format(DATE_TIME_FORMAT),
            lastUpdateDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createDate: currentDate,
            lastUpdateDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a MedicalHistoryConfig', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
