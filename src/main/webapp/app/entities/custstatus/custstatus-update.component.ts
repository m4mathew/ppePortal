import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ICUSTSTATUS } from 'app/shared/model/custstatus.model';
import { CUSTSTATUSService } from './custstatus.service';
import { CUSTSTATUSESService } from './custstatuses.service';
@Component({
    selector: 'jhi-custstatus-update',
    templateUrl: './custstatus-update.component.html'
})
export class CUSTSTATUSUpdateComponent implements OnInit {
    private _cUSTSTATUS: ICUSTSTATUS;
    isSaving: boolean;
    changeDtDp: any;
    statusArray: Array<string>;
    private cUSTSTATUSES: ICUSTSTATUS[];
    
    constructor(private cUSTSTATUSESService: CUSTSTATUSESService, private cUSTSTATUSService: CUSTSTATUSService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ cUSTSTATUS }) => {
        this.cUSTSTATUS = cUSTSTATUS;
         });
        this.statusArray= ['Final notice served.','Draft notice received from Adv.','HOD Approval taken','Brief forwarded to advocate' ];
        this.cUSTSTATUSES = this.cUSTSTATUSESService.getAll();
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.cUSTSTATUS.id !== undefined) {
            this.subscribeToSaveResponse(this.cUSTSTATUSService.update(this.cUSTSTATUS));
        } else {
            this.subscribeToSaveResponse(this.cUSTSTATUSService.create(this.cUSTSTATUS));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICUSTSTATUS>>) {
        result.subscribe((res: HttpResponse<ICUSTSTATUS>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get cUSTSTATUS() {
        return this._cUSTSTATUS;
    }

    set cUSTSTATUS(cUSTSTATUS: ICUSTSTATUS) {
        this._cUSTSTATUS = cUSTSTATUS;
    }
   
}
