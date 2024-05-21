import { Component, OnInit, HostListener } from '@angular/core';
import { ReturnRequestsService } from '../services/return-requests.service';
import {
  ReturnRequests,
  EmptyReturnRequests,
} from '../models/return-requests.model';
import { RMAItems, EmptyRMAItem } from '../models/rma-items.model';
import { HttpErrorResponse } from '@angular/common/http'; // Import HttpErrorResponse
import {BlockScrollkeysService} from '../services/block-scrollkeys.service';



@Component({
  selector: 'app-return-request-overview',
  templateUrl: './return-request-overview.component.html',
  styleUrls: ['./return-request-overview.component.scss'],
})
export class ReturnRequestOverviewComponent implements OnInit {
  apiReturnRequests: ReturnRequests = EmptyReturnRequests();
  loading = false;
  selectedRequest : RMAItems[] = []
  windowScrolledDown: boolean = false;
  windowScrolledUp: boolean = false;

  constructor(
    private returnRequestsService: ReturnRequestsService,
    private blockScrollkeysService: BlockScrollkeysService

    ) {}

  ngOnInit() {
    this.fetchReturnRequestsData();
  }

  fetchReturnRequestsData(): void {
    this.loading = true;
    this.returnRequestsService
      .getReturnRequests()
      .subscribe((apiReturnRequests) => {
        this.apiReturnRequests = apiReturnRequests;
      });
    (error: HttpErrorResponse) => {
      console.error('Error fetching return requests data:', error); // nog to doen: Handle errors
      this.loading = false;
    };
  }
  toggleDetails(request: RMAItems[]): void {
    if (this.selectedRequest === request) {
      this.selectedRequest = [];
    } else {
      this.selectedRequest = request;
    }
  }

  scrollToTop(): void {
    this.setScrollingEnabled(false);
    const smoothscroll = () => {
      const currentScroll = window.scrollY;
      if (currentScroll > 0) {
        window.requestAnimationFrame(smoothscroll);
        window.scrollTo(0, currentScroll - currentScroll / 8);
      } else {
        this.setScrollingEnabled(true);
      }
    };
    smoothscroll();
  }
  setScrollingEnabled(shouldEnable: boolean): void {
    if (shouldEnable) {
      this.blockScrollkeysService.removeListeners();
    } else {
      this.blockScrollkeysService.addListeners();
    }
  }

  @HostListener('window:scroll', [])
  onWindowScroll() {
    this.windowScrolledDown = window.pageYOffset > 100;
  }

}
