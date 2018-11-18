import { element, by, ElementFinder } from 'protractor';

export class CajaComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-caja div table .btn-danger'));
    title = element.all(by.css('jhi-caja div h2#page-heading span')).first();

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

export class CajaUpdatePage {
    pageTitle = element(by.id('jhi-caja-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    montoInput = element(by.id('field_monto'));
    montoActualInput = element(by.id('field_montoActual'));
    fechaInicialInput = element(by.id('field_fechaInicial'));
    fechaFinalInput = element(by.id('field_fechaFinal'));
    cajaSelect = element(by.id('field_caja'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setMontoInput(monto) {
        await this.montoInput.sendKeys(monto);
    }

    async getMontoInput() {
        return this.montoInput.getAttribute('value');
    }

    async setMontoActualInput(montoActual) {
        await this.montoActualInput.sendKeys(montoActual);
    }

    async getMontoActualInput() {
        return this.montoActualInput.getAttribute('value');
    }

    async setFechaInicialInput(fechaInicial) {
        await this.fechaInicialInput.sendKeys(fechaInicial);
    }

    async getFechaInicialInput() {
        return this.fechaInicialInput.getAttribute('value');
    }

    async setFechaFinalInput(fechaFinal) {
        await this.fechaFinalInput.sendKeys(fechaFinal);
    }

    async getFechaFinalInput() {
        return this.fechaFinalInput.getAttribute('value');
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

export class CajaDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-caja-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-caja'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
