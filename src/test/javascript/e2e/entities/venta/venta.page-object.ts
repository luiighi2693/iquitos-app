import { element, by, ElementFinder } from 'protractor';

export class VentaComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-venta div table .btn-danger'));
    title = element.all(by.css('jhi-venta div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getText();
    }
}

export class VentaUpdatePage {
    pageTitle = element(by.id('jhi-venta-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    codigoInput = element(by.id('field_codigo'));
    subTotalInput = element(by.id('field_subTotal'));
    impuestoInput = element(by.id('field_impuesto'));
    montoTotalInput = element(by.id('field_montoTotal'));
    fechaInput = element(by.id('field_fecha'));
    estatusSelect = element(by.id('field_estatus'));
    glosaInput = element(by.id('field_glosa'));
    metaDataInput = element(by.id('field_metaData'));
    clienteSelect = element(by.id('field_cliente'));
    empleadoSelect = element(by.id('field_empleado'));
    cajaSelect = element(by.id('field_caja'));
    tipoDeDocumentoDeVentaSelect = element(by.id('field_tipoDeDocumentoDeVenta'));
    tipoDePagoSelect = element(by.id('field_tipoDePago'));
    estatusDeProductoEntregadoSelect = element(by.id('field_estatusDeProductoEntregado'));
    productosSelect = element(by.id('field_productos'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setCodigoInput(codigo) {
        await this.codigoInput.sendKeys(codigo);
    }

    async getCodigoInput() {
        return this.codigoInput.getAttribute('value');
    }

    async setSubTotalInput(subTotal) {
        await this.subTotalInput.sendKeys(subTotal);
    }

    async getSubTotalInput() {
        return this.subTotalInput.getAttribute('value');
    }

    async setImpuestoInput(impuesto) {
        await this.impuestoInput.sendKeys(impuesto);
    }

    async getImpuestoInput() {
        return this.impuestoInput.getAttribute('value');
    }

    async setMontoTotalInput(montoTotal) {
        await this.montoTotalInput.sendKeys(montoTotal);
    }

    async getMontoTotalInput() {
        return this.montoTotalInput.getAttribute('value');
    }

    async setFechaInput(fecha) {
        await this.fechaInput.sendKeys(fecha);
    }

    async getFechaInput() {
        return this.fechaInput.getAttribute('value');
    }

    async setEstatusSelect(estatus) {
        await this.estatusSelect.sendKeys(estatus);
    }

    async getEstatusSelect() {
        return this.estatusSelect.element(by.css('option:checked')).getText();
    }

    async estatusSelectLastOption() {
        await this.estatusSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setGlosaInput(glosa) {
        await this.glosaInput.sendKeys(glosa);
    }

    async getGlosaInput() {
        return this.glosaInput.getAttribute('value');
    }

    async setMetaDataInput(metaData) {
        await this.metaDataInput.sendKeys(metaData);
    }

    async getMetaDataInput() {
        return this.metaDataInput.getAttribute('value');
    }

    async clienteSelectLastOption() {
        await this.clienteSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async clienteSelectOption(option) {
        await this.clienteSelect.sendKeys(option);
    }

    getClienteSelect(): ElementFinder {
        return this.clienteSelect;
    }

    async getClienteSelectedOption() {
        return this.clienteSelect.element(by.css('option:checked')).getText();
    }

    async empleadoSelectLastOption() {
        await this.empleadoSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async empleadoSelectOption(option) {
        await this.empleadoSelect.sendKeys(option);
    }

    getEmpleadoSelect(): ElementFinder {
        return this.empleadoSelect;
    }

    async getEmpleadoSelectedOption() {
        return this.empleadoSelect.element(by.css('option:checked')).getText();
    }

    async cajaSelectLastOption() {
        await this.cajaSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async cajaSelectOption(option) {
        await this.cajaSelect.sendKeys(option);
    }

    getCajaSelect(): ElementFinder {
        return this.cajaSelect;
    }

    async getCajaSelectedOption() {
        return this.cajaSelect.element(by.css('option:checked')).getText();
    }

    async tipoDeDocumentoDeVentaSelectLastOption() {
        await this.tipoDeDocumentoDeVentaSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async tipoDeDocumentoDeVentaSelectOption(option) {
        await this.tipoDeDocumentoDeVentaSelect.sendKeys(option);
    }

    getTipoDeDocumentoDeVentaSelect(): ElementFinder {
        return this.tipoDeDocumentoDeVentaSelect;
    }

    async getTipoDeDocumentoDeVentaSelectedOption() {
        return this.tipoDeDocumentoDeVentaSelect.element(by.css('option:checked')).getText();
    }

    async tipoDePagoSelectLastOption() {
        await this.tipoDePagoSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async tipoDePagoSelectOption(option) {
        await this.tipoDePagoSelect.sendKeys(option);
    }

    getTipoDePagoSelect(): ElementFinder {
        return this.tipoDePagoSelect;
    }

    async getTipoDePagoSelectedOption() {
        return this.tipoDePagoSelect.element(by.css('option:checked')).getText();
    }

    async estatusDeProductoEntregadoSelectLastOption() {
        await this.estatusDeProductoEntregadoSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async estatusDeProductoEntregadoSelectOption(option) {
        await this.estatusDeProductoEntregadoSelect.sendKeys(option);
    }

    getEstatusDeProductoEntregadoSelect(): ElementFinder {
        return this.estatusDeProductoEntregadoSelect;
    }

    async getEstatusDeProductoEntregadoSelectedOption() {
        return this.estatusDeProductoEntregadoSelect.element(by.css('option:checked')).getText();
    }

    async productosSelectLastOption() {
        await this.productosSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async productosSelectOption(option) {
        await this.productosSelect.sendKeys(option);
    }

    getProductosSelect(): ElementFinder {
        return this.productosSelect;
    }

    async getProductosSelectedOption() {
        return this.productosSelect.element(by.css('option:checked')).getText();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class VentaDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-venta-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-venta'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
