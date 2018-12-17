import { NgModule } from '@angular/core';

import { PpeportalSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [PpeportalSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [PpeportalSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class PpeportalSharedCommonModule {}
