import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { CUSTSTATUS } from 'app/shared/model/custstatus.model';
import { CUSTSTATUSService } from './custstatus.service';
import { CUSTSTATUSComponent } from './custstatus.component';
import { CUSTSTATUSDetailComponent } from './custstatus-detail.component';
import { CUSTSTATUSUpdateComponent } from './custstatus-update.component';
import { CUSTSTATUSDeletePopupComponent } from './custstatus-delete-dialog.component';
import { ICUSTSTATUS } from 'app/shared/model/custstatus.model';

@Injectable({ providedIn: 'root' })
export class CUSTSTATUSResolve implements Resolve<ICUSTSTATUS> {
    constructor(private service: CUSTSTATUSService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((cUSTSTATUS: HttpResponse<CUSTSTATUS>) => cUSTSTATUS.body));
        }
        return of(new CUSTSTATUS());
    }
}

export const cUSTSTATUSRoute: Routes = [
    {
        path: 'custstatus',
        component: CUSTSTATUSComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CUSTSTATUSES'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'custstatus/:id/view',
        component: CUSTSTATUSDetailComponent,
        resolve: {
            cUSTSTATUS: CUSTSTATUSResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CUSTSTATUSES'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'custstatus/new',
        component: CUSTSTATUSUpdateComponent,
        resolve: {
            cUSTSTATUS: CUSTSTATUSResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CUSTSTATUSES'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'custstatus/:id/edit',
        component: CUSTSTATUSUpdateComponent,
        resolve: {
            cUSTSTATUS: CUSTSTATUSResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CUSTSTATUSES'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cUSTSTATUSPopupRoute: Routes = [
    {
        path: 'custstatus/:id/delete',
        component: CUSTSTATUSDeletePopupComponent,
        resolve: {
            cUSTSTATUS: CUSTSTATUSResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CUSTSTATUSES'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
