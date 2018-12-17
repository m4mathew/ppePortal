import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PpeportalSharedModule } from 'app/shared';
import {
    CUSTSTATUSComponent,
    CUSTSTATUSDetailComponent,
    CUSTSTATUSUpdateComponent,
    CUSTSTATUSDeletePopupComponent,
    CUSTSTATUSDeleteDialogComponent,
    cUSTSTATUSRoute,
    cUSTSTATUSPopupRoute
} from './';

const ENTITY_STATES = [...cUSTSTATUSRoute, ...cUSTSTATUSPopupRoute];

@NgModule({
    imports: [PpeportalSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CUSTSTATUSComponent,
        CUSTSTATUSDetailComponent,
        CUSTSTATUSUpdateComponent,
        CUSTSTATUSDeleteDialogComponent,
        CUSTSTATUSDeletePopupComponent
    ],
    entryComponents: [CUSTSTATUSComponent, CUSTSTATUSUpdateComponent, CUSTSTATUSDeleteDialogComponent, CUSTSTATUSDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PpeportalCUSTSTATUSModule {}
