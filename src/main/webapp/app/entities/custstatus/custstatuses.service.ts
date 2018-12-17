import { Injectable } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';
import { CUSTSTATUSService } from './custstatus.service';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICUSTSTATUS } from 'app/shared/model/custstatus.model';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

@Injectable({ providedIn: 'root' })
export class CUSTSTATUSESService {
  cUSTSTATUSES: ICUSTSTATUS[];  
    constructor(private cUSTSTATUSService: CUSTSTATUSService,private jhiAlertService: JhiAlertService){}
    
	getAll(): ICUSTSTATUS[] {
        this.cUSTSTATUSService.query().subscribe(
            (res: HttpResponse<ICUSTSTATUS[]>) => {
                this.cUSTSTATUSES = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        return this.cUSTSTATUSES;
      }  
        private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    	}
}
