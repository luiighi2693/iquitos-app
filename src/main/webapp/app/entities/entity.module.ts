import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { IquitosAppAmortizationModule } from './amortization/amortization.module';
import { IquitosAppSellModule } from './sell/sell.module';
import { IquitosAppCreditModule } from './credit/credit.module';
import { IquitosAppClientModule } from './client/client.module';
import { IquitosAppEmployeeModule } from './employee/employee.module';
import { IquitosAppProductModule } from './product/product.module';
import { IquitosAppVariantModule } from './variant/variant.module';
import { IquitosAppCategoryModule } from './category/category.module';
import { IquitosAppProviderModule } from './provider/provider.module';
import { IquitosAppProviderAccountModule } from './provider-account/provider-account.module';
import { IquitosAppProviderPaymentModule } from './provider-payment/provider-payment.module';
import { IquitosAppPurchaseModule } from './purchase/purchase.module';
import { IquitosAppBoxModule } from './box/box.module';
import { IquitosAppOperationModule } from './operation/operation.module';
import { IquitosAppDocumentTypeModule } from './document-type/document-type.module';
import { IquitosAppPaymentTypeModule } from './payment-type/payment-type.module';
import { IquitosAppDocumentTypeSellModule } from './document-type-sell/document-type-sell.module';
import { IquitosAppProductsDeliveredStatusModule } from './products-delivered-status/products-delivered-status.module';
import { IquitosAppUnitMeasurementModule } from './unit-measurement/unit-measurement.module';
import { IquitosAppDocumentTypePurchaseModule } from './document-type-purchase/document-type-purchase.module';
import { IquitosAppPurchaseStatusModule } from './purchase-status/purchase-status.module';
import { IquitosAppConceptOperationInputModule } from './concept-operation-input/concept-operation-input.module';
import { IquitosAppConceptOperationOutputModule } from './concept-operation-output/concept-operation-output.module';
import { IquitosAppOrderProductModule } from './order-product/order-product.module';
import { IquitosAppUserLoginModule } from './user-login/user-login.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        IquitosAppAmortizationModule,
        IquitosAppSellModule,
        IquitosAppCreditModule,
        IquitosAppClientModule,
        IquitosAppEmployeeModule,
        IquitosAppProductModule,
        IquitosAppVariantModule,
        IquitosAppCategoryModule,
        IquitosAppProviderModule,
        IquitosAppProviderAccountModule,
        IquitosAppProviderPaymentModule,
        IquitosAppPurchaseModule,
        IquitosAppBoxModule,
        IquitosAppOperationModule,
        IquitosAppDocumentTypeModule,
        IquitosAppPaymentTypeModule,
        IquitosAppDocumentTypeSellModule,
        IquitosAppProductsDeliveredStatusModule,
        IquitosAppUnitMeasurementModule,
        IquitosAppDocumentTypePurchaseModule,
        IquitosAppPurchaseStatusModule,
        IquitosAppConceptOperationInputModule,
        IquitosAppConceptOperationOutputModule,
        IquitosAppOrderProductModule,
        IquitosAppUserLoginModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppEntityModule {}
