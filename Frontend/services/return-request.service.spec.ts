import { TestBed, inject } from '@angular/core/testing';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { HttpClient } from '@angular/common/http';
import { ReturnRequestsService } from './return-requests.service';
import { MY_SERVICE_URL } from '../injection-tokens';
import { ReturnRequest } from '../models/return-request.model';
import { ReturnRequests } from '../models/return-requests.model';

describe('ReturnRequestsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        ReturnRequestsService,
        { provide: MY_SERVICE_URL, useValue: 'mockServiceUrl' },
      ],
    });
  });

  it('should be created', inject(
    [ReturnRequestsService],
    (service: ReturnRequestsService) => {
      expect(service).toBeTruthy();
    }
  ));

  it('should call getReturnRequests and return data', inject(
    [ReturnRequestsService, HttpClient, HttpTestingController],
    (
      service: ReturnRequestsService,
      http: HttpClient,
      httpMock: HttpTestingController
    ) => {
      const mockReturnRequestData: ReturnRequest[] = [
        {
          rmaNum: 'RMA12345',
          status: 'initialized',
          orderNumber: 'ORD98765',
          emailAddress: 'testEmailAddress@indi.nl',
          comment: 'damaged',
          returnRequestDate: '2024-04-09 14:00:18.247',
          rmaItems: [
            {
              position: 1,
              sku: 'SKU001',
              quantity: 10,
              articleName: 'Article 1',
              imageURL: 'https://assets.indi.nl/images/2XS/721005gp.jpg'
            },
          ],
        },
      ];

      const returnRequestsData: ReturnRequests = {
        apiReturnRequests: mockReturnRequestData,
      };
      service.getReturnRequests().subscribe((apiReturnRequests: ReturnRequests) => {
        expect(apiReturnRequests).toEqual(returnRequestsData);
      });

      const req = httpMock.expectOne('mockServiceUrl/returns');
      expect(req.request.method).toEqual('GET');
      req.flush(returnRequestsData);

      httpMock.verify();
    }
  ));
});
