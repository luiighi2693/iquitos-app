import { NgModule } from '@angular/core';

import { IquitosAppSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [IquitosAppSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [IquitosAppSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class IquitosAppSharedCommonModule {}
