import { ReturnRequest } from '../models/return-request.model';

export interface ReturnRequests {
  apiReturnRequests: ReturnRequest[];
}

export const EmptyReturnRequests = (): ReturnRequests => ({
  apiReturnRequests: [],
});
