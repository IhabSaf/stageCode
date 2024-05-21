import { Injectable, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ReturnRequests } from '../models/return-requests.model';
import { MY_SERVICE_URL } from '../injection-tokens';

@Injectable()
export class ReturnRequestsService {
  constructor(
    @Inject(MY_SERVICE_URL) readonly serviceUrl: string,
    readonly http: HttpClient
  ) {}

  getReturnRequests(): Observable<ReturnRequests> {
    return this.http.get<ReturnRequests>(this.serviceUrl + '/returns');
  }
}
