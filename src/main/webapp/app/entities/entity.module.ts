import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { PpeportalCUSTSTATUSModule } from './custstatus/custstatus.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        PpeportalCUSTSTATUSModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PpeportalEntityModule {}
