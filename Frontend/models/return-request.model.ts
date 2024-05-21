import { RMAItems } from '../models/rma-items.model';
export interface ReturnRequest {
  rmaNum: String;
  status: String;
  orderNumber: String;
  emailAddress: string;
  comment: string;
  returnRequestDate: string;
  rmaItems: RMAItems[];
}
