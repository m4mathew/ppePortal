import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICUSTSTATUS } from 'app/shared/model/custstatus.model';

@Component({
    selector: 'jhi-custstatus-detail',
    templateUrl: './custstatus-detail.component.html'
})
export class CUSTSTATUSDetailComponent implements OnInit {
    cUSTSTATUS: ICUSTSTATUS;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ cUSTSTATUS }) => {
            this.cUSTSTATUS = cUSTSTATUS;
        });
    }

    previousState() {
        window.history.back();
    }
}
