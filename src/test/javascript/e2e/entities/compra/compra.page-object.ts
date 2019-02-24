import { element, by, ElementFinder } from 'protractor';

export class CompraComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-compra div table .btn-danger'));
    title = element.all(by.css('jhi-compra div h2#page-heading span')).first();

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

export class CompraUpdatePage {
    pageTitle = element(by.id('jhi-compra-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    fechaInput = element(by.id('field_fecha'));
    guiaRemisionInput = element(by.id('field_guiaRemision'));
    numeroDeDocumentoInput = element(by.id('field_numeroDeDocumento'));
    ubicacionSelect = element(by.id('field_ubicacion'));
    montoTotalInput = element(by.id('field_montoTotal'));
    correlativoInput = element(by.id('field_correlativo'));
    tipoDePagoDeCompraSelect = element(by.id('field_tipoDePagoDeCompra'));
    tipoDeTransaccionSelect = element(by.id('field_tipoDeTransaccion'));
    estatusSelect = element(by.id('field_estatus'));
    metaDataInput = element(by.id('field_metaData'));
    proveedorSelect = element(by.id('field_proveedor'));
    tipoDeDocumentoDeCompraSelect = element(by.id('field_tipoDeDocumentoDeCompra'));
    estatusDeCompraSelect = element(by.id('field_estatusDeCompra'));
    cajaSelect = element(by.id('field_caja'));
    productosSelect = element(by.id('field_productos'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setFechaInput(fecha) {
        await this.fechaInput.sendKeys(fecha);
    }

    async getFechaInput() {
        return this.fechaInput.getAttribute('value');
    }

    async setGuiaRemisionInput(guiaRemision) {
        await this.guiaRemisionInput.sendKeys(guiaRemision);
    }

    async getGuiaRemisionInput() {
        return this.guiaRemisionInput.getAttribute('value');
    }

    async setNumeroDeDocumentoInput(numeroDeDocumento) {
        await this.numeroDeDocumentoInput.sendKeys(numeroDeDocumento);
    }

    async getNumeroDeDocumentoInput() {
        return this.numeroDeDocumentoInput.getAttribute('value');
    }

    async setUbicacionSelect(ubicacion) {
        await this.ubicacionSelect.sendKeys(ubicacion);
    }

    async getUbicacionSelect() {
        return this.ubicacionSelect.element(by.css('option:checked')).getText();
    }

    async ubicacionSelectLastOption() {
        await this.ubicacionSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setMontoTotalInput(montoTotal) {
        await this.montoTotalInput.sendKeys(montoTotal);
    }

    async getMontoTotalInput() {
        return this.montoTotalInput.getAttribute('value');
    }

    async setCorrelativoInput(correlativo) {
        await this.correlativoInput.sendKeys(correlativo);
    }

    async getCorrelativoInput() {
        return this.correlativoInput.getAttribute('value');
    }

    async setTipoDePagoDeCompraSelect(tipoDePagoDeCompra) {
        await this.tipoDePagoDeCompraSelect.sendKeys(tipoDePagoDeCompra);
    }

    async getTipoDePagoDeCompraSelect() {
        return this.tipoDePagoDeCompraSelect.element(by.css('option:checked')).getText();
    }

    async tipoDePagoDeCompraSelectLastOption() {
        await this.tipoDePagoDeCompraSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setTipoDeTransaccionSelect(tipoDeTransaccion) {
        await this.tipoDeTransaccionSelect.sendKeys(tipoDeTransaccion);
    }

    async getTipoDeTransaccionSelect() {
        return this.tipoDeTransaccionSelect.element(by.css('option:checked')).getText();
    }

    async tipoDeTransaccionSelectLastOption() {
        await this.tipoDeTransaccionSelect
            .all(by.tagName('option'))
            .last()
            .click();
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

    async setMetaDataInput(metaData) {
        await this.metaDataInput.sendKeys(metaData);
    }

    async getMetaDataInput() {
        return this.metaDataInput.getAttribute('value');
    }

    async proveedorSelectLastOption() {
        await this.proveedorSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async proveedorSelectOption(option) {
        await this.proveedorSelect.sendKeys(option);
    }

    getProveedorSelect(): ElementFinder {
        return this.proveedorSelect;
    }

    async getProveedorSelectedOption() {
        return this.proveedorSelect.element(by.css('option:checked')).getText();
    }

    async tipoDeDocumentoDeCompraSelectLastOption() {
        await this.tipoDeDocumentoDeCompraSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async tipoDeDocumentoDeCompraSelectOption(option) {
        await this.tipoDeDocumentoDeCompraSelect.sendKeys(option);
    }

    getTipoDeDocumentoDeCompraSelect(): ElementFinder {
        return this.tipoDeDocumentoDeCompraSelect;
    }

    async getTipoDeDocumentoDeCompraSelectedOption() {
        return this.tipoDeDocumentoDeCompraSelect.element(by.css('option:checked')).getText();
    }

    async estatusDeCompraSelectLastOption() {
        await this.estatusDeCompraSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async estatusDeCompraSelectOption(option) {
        await this.estatusDeCompraSelect.sendKeys(option);
    }

    getEstatusDeCompraSelect(): ElementFinder {
        return this.estatusDeCompraSelect;
    }

    async getEstatusDeCompraSelectedOption() {
        return this.estatusDeCompraSelect.element(by.css('option:checked')).getText();
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

export class CompraDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-compra-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-compra'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
