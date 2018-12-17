import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICUSTSTATUS } from 'app/shared/model/custstatus.model';
import { CUSTSTATUSService } from './custstatus.service';

@Component({
    selector: 'jhi-custstatus-delete-dialog',
    templateUrl: './custstatus-delete-dialog.component.html'
})
export class CUSTSTATUSDeleteDialogComponent {
    cUSTSTATUS: ICUSTSTATUS;

    constructor(private cUSTSTATUSService: CUSTSTATUSService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.cUSTSTATUSService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'cUSTSTATUSListModification',
                content: 'Deleted an cUSTSTATUS'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-custstatus-delete-popup',
    template: ''
})
export class CUSTSTATUSDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ cUSTSTATUS }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CUSTSTATUSDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.cUSTSTATUS = cUSTSTATUS;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
