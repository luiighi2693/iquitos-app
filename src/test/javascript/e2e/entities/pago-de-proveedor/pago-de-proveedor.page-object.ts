import { element, by, ElementFinder } from 'protractor';

export class PagoDeProveedorComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-pago-de-proveedor div table .btn-danger'));
    title = element.all(by.css('jhi-pago-de-proveedor div h2#page-heading span')).first();

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

export class PagoDeProveedorUpdatePage {
    pageTitle = element(by.id('jhi-pago-de-proveedor-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    montoInput = element(by.id('field_monto'));
    montoPagadoInput = element(by.id('field_montoPagado'));
    fechaInput = element(by.id('field_fecha'));
    codigoDeDocumentoInput = element(by.id('field_codigoDeDocumento'));
    glosaInput = element(by.id('field_glosa'));
    imagenInput = element(by.id('file_imagen'));
    tipoDeDocumentoDeCompraSelect = element(by.id('field_tipoDeDocumentoDeCompra'));
    tipoDePagoSelect = element(by.id('field_tipoDePago'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setMontoInput(monto) {
        await this.montoInput.sendKeys(monto);
    }

    async getMontoInput() {
        return this.montoInput.getAttribute('value');
    }

    async setMontoPagadoInput(montoPagado) {
        await this.montoPagadoInput.sendKeys(montoPagado);
    }

    async getMontoPagadoInput() {
        return this.montoPagadoInput.getAttribute('value');
    }

    async setFechaInput(fecha) {
        await this.fechaInput.sendKeys(fecha);
    }

    async getFechaInput() {
        return this.fechaInput.getAttribute('value');
    }

    async setCodigoDeDocumentoInput(codigoDeDocumento) {
        await this.codigoDeDocumentoInput.sendKeys(codigoDeDocumento);
    }

    async getCodigoDeDocumentoInput() {
        return this.codigoDeDocumentoInput.getAttribute('value');
    }

    async setGlosaInput(glosa) {
        await this.glosaInput.sendKeys(glosa);
    }

    async getGlosaInput() {
        return this.glosaInput.getAttribute('value');
    }

    async setImagenInput(imagen) {
        await this.imagenInput.sendKeys(imagen);
    }

    async getImagenInput() {
        return this.imagenInput.getAttribute('value');
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

export class PagoDeProveedorDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-pagoDeProveedor-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-pagoDeProveedor'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
