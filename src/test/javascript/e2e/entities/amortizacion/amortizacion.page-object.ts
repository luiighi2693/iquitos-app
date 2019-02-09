import { element, by, ElementFinder } from 'protractor';

export class AmortizacionComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-amortizacion div table .btn-danger'));
    title = element.all(by.css('jhi-amortizacion div h2#page-heading span')).first();

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

export class AmortizacionUpdatePage {
    pageTitle = element(by.id('jhi-amortizacion-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    codigoInput = element(by.id('field_codigo'));
    montoInput = element(by.id('field_monto'));
    montoPagadoInput = element(by.id('field_montoPagado'));
    fechaInput = element(by.id('field_fecha'));
    codigoDocumentoInput = element(by.id('field_codigoDocumento'));
    glosaInput = element(by.id('field_glosa'));
    comprobanteInput = element(by.id('field_comprobante'));
    fotoComprobanteInput = element(by.id('file_fotoComprobante'));
    tipoDeDocumentoDeVentaSelect = element(by.id('field_tipoDeDocumentoDeVenta'));
    tipoDePagoSelect = element(by.id('field_tipoDePago'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setCodigoInput(codigo) {
        await this.codigoInput.sendKeys(codigo);
    }

    async getCodigoInput() {
        return this.codigoInput.getAttribute('value');
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

    async setCodigoDocumentoInput(codigoDocumento) {
        await this.codigoDocumentoInput.sendKeys(codigoDocumento);
    }

    async getCodigoDocumentoInput() {
        return this.codigoDocumentoInput.getAttribute('value');
    }

    async setGlosaInput(glosa) {
        await this.glosaInput.sendKeys(glosa);
    }

    async getGlosaInput() {
        return this.glosaInput.getAttribute('value');
    }

    async setComprobanteInput(comprobante) {
        await this.comprobanteInput.sendKeys(comprobante);
    }

    async getComprobanteInput() {
        return this.comprobanteInput.getAttribute('value');
    }

    async setFotoComprobanteInput(fotoComprobante) {
        await this.fotoComprobanteInput.sendKeys(fotoComprobante);
    }

    async getFotoComprobanteInput() {
        return this.fotoComprobanteInput.getAttribute('value');
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

export class AmortizacionDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-amortizacion-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-amortizacion'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
