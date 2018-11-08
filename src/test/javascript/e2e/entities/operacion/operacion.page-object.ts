import { element, by, ElementFinder } from 'protractor';

export class OperacionComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-operacion div table .btn-danger'));
    title = element.all(by.css('jhi-operacion div h2#page-heading span')).first();

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

export class OperacionUpdatePage {
    pageTitle = element(by.id('jhi-operacion-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    fechaInput = element(by.id('field_fecha'));
    glosaInput = element(by.id('field_glosa'));
    montoInput = element(by.id('field_monto'));
    tipoDeOperacionSelect = element(by.id('field_tipoDeOperacion'));
    cajaSelect = element(by.id('field_caja'));
    tipoDePagoSelect = element(by.id('field_tipoDePago'));
    tipoDeOperacionDeIngresoSelect = element(by.id('field_tipoDeOperacionDeIngreso'));
    tipoDeOperacionDeGastoSelect = element(by.id('field_tipoDeOperacionDeGasto'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setFechaInput(fecha) {
        await this.fechaInput.sendKeys(fecha);
    }

    async getFechaInput() {
        return this.fechaInput.getAttribute('value');
    }

    async setGlosaInput(glosa) {
        await this.glosaInput.sendKeys(glosa);
    }

    async getGlosaInput() {
        return this.glosaInput.getAttribute('value');
    }

    async setMontoInput(monto) {
        await this.montoInput.sendKeys(monto);
    }

    async getMontoInput() {
        return this.montoInput.getAttribute('value');
    }

    async setTipoDeOperacionSelect(tipoDeOperacion) {
        await this.tipoDeOperacionSelect.sendKeys(tipoDeOperacion);
    }

    async getTipoDeOperacionSelect() {
        return this.tipoDeOperacionSelect.element(by.css('option:checked')).getText();
    }

    async tipoDeOperacionSelectLastOption() {
        await this.tipoDeOperacionSelect
            .all(by.tagName('option'))
            .last()
            .click();
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

    async tipoDeOperacionDeIngresoSelectLastOption() {
        await this.tipoDeOperacionDeIngresoSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async tipoDeOperacionDeIngresoSelectOption(option) {
        await this.tipoDeOperacionDeIngresoSelect.sendKeys(option);
    }

    getTipoDeOperacionDeIngresoSelect(): ElementFinder {
        return this.tipoDeOperacionDeIngresoSelect;
    }

    async getTipoDeOperacionDeIngresoSelectedOption() {
        return this.tipoDeOperacionDeIngresoSelect.element(by.css('option:checked')).getText();
    }

    async tipoDeOperacionDeGastoSelectLastOption() {
        await this.tipoDeOperacionDeGastoSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async tipoDeOperacionDeGastoSelectOption(option) {
        await this.tipoDeOperacionDeGastoSelect.sendKeys(option);
    }

    getTipoDeOperacionDeGastoSelect(): ElementFinder {
        return this.tipoDeOperacionDeGastoSelect;
    }

    async getTipoDeOperacionDeGastoSelectedOption() {
        return this.tipoDeOperacionDeGastoSelect.element(by.css('option:checked')).getText();
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

export class OperacionDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-operacion-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-operacion'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
