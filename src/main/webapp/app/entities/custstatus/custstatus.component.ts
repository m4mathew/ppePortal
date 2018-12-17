import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ICUSTSTATUS } from 'app/shared/model/custstatus.model';
import { Principal } from 'app/core';
import { CUSTSTATUSService } from './custstatus.service';
import { Angular2Csv } from 'angular2-csv/Angular2-csv';
import { CustUnitPipe } from './custunit.pipe';
import { ISUMMARY } from 'app/shared/model/summary.model';
@Component({
    selector: 'jhi-custstatus',
    templateUrl: './custstatus.component.html',
    providers:[CustUnitPipe]
})
export class CUSTSTATUSComponent implements OnInit, OnDestroy {
    cUSTSTATUSES: ICUSTSTATUS[];
    subList: Array<ICUSTSTATUS> = [];
    subListLatest: Array<ICUSTSTATUS> = [];
    currentAccount: any;
    eventSubscriber: Subscription;
 	cUSTSTATUSESLAST: ICUSTSTATUS[];
 	sUMMARY: ISUMMARY[];
    constructor(
        private cUSTSTATUSService: CUSTSTATUSService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
        private pipe: CustUnitPipe
    ) {}

    loadAll() {
        this.cUSTSTATUSService.query().subscribe(
            (res: HttpResponse<ICUSTSTATUS[]>) => {
                this.cUSTSTATUSES = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        
    }

    ngOnInit() {
    	
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCUSTSTATUSES();
        this.getSummary();
        
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICUSTSTATUS) {
        return item.custId;
    }

    registerChangeInCUSTSTATUSES() {
        this.eventSubscriber = this.eventManager.subscribe('cUSTSTATUSListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
    
	downloadDataSelected() {
	var options = { 
	fieldSeparator: ',',
	quoteStrings: '"',
	decimalseparator: '.',
	showLabels: true, 
	showTitle: true,
	headers: ['RowId','CustomerID','Status','ChangeDate','UserId','No of Days'] 
	};
	    new Angular2Csv(this.subList,'My ReportUnit',options);
		 
	}
	
	downloadDataAll() {
	var options = { 
	fieldSeparator: ',',
	quoteStrings: '"',
	decimalseparator: '.',
	showLabels: true, 
	showTitle: true,
	headers: ['RowId','CustomerID','Status','ChangeDate','UserId','No of Days'] 
	};
	    new Angular2Csv(this.cUSTSTATUSES,'My ReportAll',options);
		 
	}
	
	downloadAllLatest() {
	var options = { 
	fieldSeparator: ',',
	quoteStrings: '"',
	decimalseparator: '.',
	showLabels: true, 
	showTitle: true,
	headers: ['RowId','CustomerID','Status','ChangeDate','UserId','No of Days'] 
	};
	    new Angular2Csv(this.cUSTSTATUSESLAST,'My ReportAll_OnlyLatest',options);
		 
	}
	downloadSelectedLatest() {
	var options = { 
	fieldSeparator: ',',
	quoteStrings: '"',
	decimalseparator: '.',
	showLabels: true, 
	showTitle: true,
	headers: ['RowId','CustomerID','Status','ChangeDate','UserId','No of Days'] 
	};
	    new Angular2Csv(this.subListLatest,'My ReportUnit_OnlyLatest',options);
		 
	}
    addCustStatus(cUSTSTATUS:ICUSTSTATUS) {
       this.subList.push(cUSTSTATUS);
    }
    addCustStatusLastest(cUSTSTATUS:ICUSTSTATUS) {
       this.subListLatest.push(cUSTSTATUS);
    }
    unitOnChange(){
      this.subList=[];
       this.subListLatest=[];
    }
    
  
    getOnlyLatestPpe() {
    this.subListLatest=[];
        this.cUSTSTATUSService.findOnlyLatest().subscribe(
            (resp: HttpResponse<ICUSTSTATUS[]>) => {this.cUSTSTATUSESLAST = resp.body;},
            (resp: HttpErrorResponse) => this.onError(resp.message));
    }
    getSummary() {
       this.cUSTSTATUSService.findSummary().subscribe(
            (resp: HttpResponse<ISUMMARY[]>) => {this.sUMMARY = resp.body;},
            (resp: HttpErrorResponse) => this.onError(resp.message));
    }
    
}
