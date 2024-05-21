import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { ReturnRequestOverviewComponent } from './return-request-overview.component';
import { ReturnRequestsService } from '../services/return-requests.service';
import { ReturnRequests } from '../models/return-requests.model';
import { ReturnRequest } from '../models/return-request.model';
import { RMAItems } from '../models/rma-items.model';

describe('ReturnRequestOverviewComponent', () => {
  let component: ReturnRequestOverviewComponent;
  let fixture: ComponentFixture<ReturnRequestOverviewComponent>;
  let returnRequestsService: ReturnRequestsService;

  const mockRMAItems: RMAItems[] = [
    {
      position: 12,
      sku: 'SK34U001',
      quantity: 103,
      articleName: 'Articl44e 1',
      imageURL: 'https://assets.indi.nl/images/2XS/721005gp.jpg'
    },
  ];

  const mockReturnRequestData: ReturnRequest[] = [
    {
      rmaNum: 'RMA1234225',
      status: 'initialized',
      orderNumber: 'ORD98765',
      emailAddress: 'testEmailAddress@indi.nl',
      comment: 'damaged',
      returnRequestDate: '2024-04-09 14:00:18.247',
      rmaItems: mockRMAItems,
    },
    {
      rmaNum: 'RMA1232342345',
      status: 'initialized',
      orderNumber: 'ORD987645',
      emailAddress: 'testEmailAddress@indi.nl',
      comment: 'damaged',
      returnRequestDate: '2024-04-09 14:00:18.247',
      rmaItems: [
        {
          position: 1,
          sku: 'SKU02301',
          quantity: 130,
          articleName: 'Article 21',
          imageURL: 'https://assets.indi.nl/images/2XS/721005gp.jpg'
        },
      ],
    },
    {
      rmaNum: 'RMA1234445',
      status: 'initialized',
      orderNumber: 'ORD98764265',
      emailAddress: 'testEmailAddress@indi.nl',
      comment: 'damaged',
      returnRequestDate: '2024-04-09 14:00:18.247',
      rmaItems: [
        {
          position: 1,
          sku: 'SKU07701',
          quantity: 1370,
          articleName: 'Ar4ticle3 31',
          imageURL: 'https://assets.indi.nl/images/2XS/721005gp.jpg'
        },
      ],
    },
    {
      rmaNum: 'RMA1239845',
      status: 'initialized',
      orderNumber: 'ORD9878765',
      emailAddress: 'testEmailAddress@indi.nl',
      comment: 'damaged',
      returnRequestDate: '2024-04-09 14:00:18.247',
      rmaItems: [
        {
          position: 1,
          sku: 'SKU006581',
          quantity: 180,
          articleName: 'Attyrticle 18',
          imageURL: 'https://assets.indi.nl/images/2XS/721005gp.jpg'
        },
        {
          position: 15,
          sku: 'SKU001',
          quantity: 10,
          articleName: 'Article 1',
          imageURL: 'https://assets.indi.nl/images/2XS/721005gp.jpg'
        },
        {
          position: 71,
          sku: 'SKU0015',
          quantity: 510,
          articleName: 'Article 14',
          imageURL: 'https://assets.indi.nl/images/2XS/721005gp.jpg'
        },
        {
          position: 145,
          sku: 'SKU004571',
          quantity: 1450,
          articleName: 'Article 551',
          imageURL: 'https://assets.indi.nl/images/2XS/721005gp.jpg'
        },
      ],
    },
  ];

  const returnRequestsData: ReturnRequests = {
    apiReturnRequests: mockReturnRequestData,
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ReturnRequestOverviewComponent],
      imports: [HttpClientTestingModule],
      providers: [ReturnRequestsService],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReturnRequestOverviewComponent);
    component = fixture.componentInstance;
    returnRequestsService = TestBed.inject(ReturnRequestsService);
    spyOn(returnRequestsService, 'getReturnRequests').and.returnValue(
      of(returnRequestsData)
    );
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch return requests data', () => {
    component.fetchReturnRequestsData();

    expect(component.apiReturnRequests.apiReturnRequests).toEqual(
      jasmine.arrayContaining(returnRequestsData.apiReturnRequests)
    );
  });
});
