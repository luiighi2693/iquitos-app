import { element, by, ElementFinder } from 'protractor';

export class CreditoComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-credito div table .btn-danger'));
    title = element.all(by.css('jhi-credito div h2#page-heading span')).first();

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

export class CreditoUpdatePage {
    pageTitle = element(by.id('jhi-credito-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    montoInput = element(by.id('field_monto'));
    fechaInput = element(by.id('field_fecha'));
    modoDePagoInput = element(by.id('field_modoDePago'));
    numeroInput = element(by.id('field_numero'));
    montoTotalInput = element(by.id('field_montoTotal'));
    fechaLimiteInput = element(by.id('field_fechaLimite'));
    notaDeCreditoInput = element(by.id('field_notaDeCredito'));
    ventaSelect = element(by.id('field_venta'));
    compraSelect = element(by.id('field_compra'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setMontoInput(monto) {
        await this.montoInput.sendKeys(monto);
    }

    async getMontoInput() {
        return this.montoInput.getAttribute('value');
    }

    async setFechaInput(fecha) {
        await this.fechaInput.sendKeys(fecha);
    }

    async getFechaInput() {
        return this.fechaInput.getAttribute('value');
    }

    async setModoDePagoInput(modoDePago) {
        await this.modoDePagoInput.sendKeys(modoDePago);
    }

    async getModoDePagoInput() {
        return this.modoDePagoInput.getAttribute('value');
    }

    async setNumeroInput(numero) {
        await this.numeroInput.sendKeys(numero);
    }

    async getNumeroInput() {
        return this.numeroInput.getAttribute('value');
    }

    async setMontoTotalInput(montoTotal) {
        await this.montoTotalInput.sendKeys(montoTotal);
    }

    async getMontoTotalInput() {
        return this.montoTotalInput.getAttribute('value');
    }

    async setFechaLimiteInput(fechaLimite) {
        await this.fechaLimiteInput.sendKeys(fechaLimite);
    }

    async getFechaLimiteInput() {
        return this.fechaLimiteInput.getAttribute('value');
    }

    async setNotaDeCreditoInput(notaDeCredito) {
        await this.notaDeCreditoInput.sendKeys(notaDeCredito);
    }

    async getNotaDeCreditoInput() {
        return this.notaDeCreditoInput.getAttribute('value');
    }

    async ventaSelectLastOption() {
        await this.ventaSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async ventaSelectOption(option) {
        await this.ventaSelect.sendKeys(option);
    }

    getVentaSelect(): ElementFinder {
        return this.ventaSelect;
    }

    async getVentaSelectedOption() {
        return this.ventaSelect.element(by.css('option:checked')).getText();
    }

    async compraSelectLastOption() {
        await this.compraSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async compraSelectOption(option) {
        await this.compraSelect.sendKeys(option);
    }

    getCompraSelect(): ElementFinder {
        return this.compraSelect;
    }

    async getCompraSelectedOption() {
        return this.compraSelect.element(by.css('option:checked')).getText();
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

export class CreditoDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-credito-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-credito'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
