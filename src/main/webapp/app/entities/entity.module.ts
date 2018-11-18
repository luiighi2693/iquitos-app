import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { IquitosAppAmortizacionModule } from './amortizacion/amortizacion.module';
import { IquitosAppVentaModule } from './venta/venta.module';
import { IquitosAppCreditoModule } from './credito/credito.module';
import { IquitosAppClienteModule } from './cliente/cliente.module';
import { IquitosAppEmpleadoModule } from './empleado/empleado.module';
import { IquitosAppProductoModule } from './producto/producto.module';
import { IquitosAppVarianteModule } from './variante/variante.module';
import { IquitosAppCategoriaModule } from './categoria/categoria.module';
import { IquitosAppPagoDeProveedorModule } from './pago-de-proveedor/pago-de-proveedor.module';
import { IquitosAppCompraModule } from './compra/compra.module';
import { IquitosAppCajaModule } from './caja/caja.module';
import { IquitosAppOperacionModule } from './operacion/operacion.module';
import { IquitosAppUsuarioExternoModule } from './usuario-externo/usuario-externo.module';
import { IquitosAppPedidoModule } from './pedido/pedido.module';
import { IquitosAppTipoDeDocumentoModule } from './tipo-de-documento/tipo-de-documento.module';
import { IquitosAppTipoDePagoModule } from './tipo-de-pago/tipo-de-pago.module';
import { IquitosAppTipoDeDocumentoDeVentaModule } from './tipo-de-documento-de-venta/tipo-de-documento-de-venta.module';
import { IquitosAppEstatusDeProductoEntregadoModule } from './estatus-de-producto-entregado/estatus-de-producto-entregado.module';
import { IquitosAppUnidadDeMedidaModule } from './unidad-de-medida/unidad-de-medida.module';
import { IquitosAppTipoDeDocumentoDeCompraModule } from './tipo-de-documento-de-compra/tipo-de-documento-de-compra.module';
import { IquitosAppEstatusDeCompraModule } from './estatus-de-compra/estatus-de-compra.module';
import { IquitosAppTipoDeOperacionDeIngresoModule } from './tipo-de-operacion-de-ingreso/tipo-de-operacion-de-ingreso.module';
import { IquitosAppTipoDeOperacionDeGastoModule } from './tipo-de-operacion-de-gasto/tipo-de-operacion-de-gasto.module';
import { IquitosAppContactoProveedorModule } from './contacto-proveedor/contacto-proveedor.module';
import { IquitosAppProveedorModule } from './proveedor/proveedor.module';
import { IquitosAppCuentaProveedorModule } from './cuenta-proveedor/cuenta-proveedor.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        IquitosAppAmortizacionModule,
        IquitosAppVentaModule,
        IquitosAppCreditoModule,
        IquitosAppClienteModule,
        IquitosAppEmpleadoModule,
        IquitosAppProductoModule,
        IquitosAppVarianteModule,
        IquitosAppCategoriaModule,
        IquitosAppPagoDeProveedorModule,
        IquitosAppCompraModule,
        IquitosAppCajaModule,
        IquitosAppOperacionModule,
        IquitosAppUsuarioExternoModule,
        IquitosAppPedidoModule,
        IquitosAppTipoDeDocumentoModule,
        IquitosAppTipoDePagoModule,
        IquitosAppTipoDeDocumentoDeVentaModule,
        IquitosAppEstatusDeProductoEntregadoModule,
        IquitosAppUnidadDeMedidaModule,
        IquitosAppTipoDeDocumentoDeCompraModule,
        IquitosAppEstatusDeCompraModule,
        IquitosAppTipoDeOperacionDeIngresoModule,
        IquitosAppTipoDeOperacionDeGastoModule,
        IquitosAppContactoProveedorModule,
        IquitosAppProveedorModule,
        IquitosAppCuentaProveedorModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppEntityModule {}
