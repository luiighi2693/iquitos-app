import { element, by, ElementFinder } from 'protractor';

export class TipoDeOperacionDeGastoComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-tipo-de-operacion-de-gasto div table .btn-danger'));
    title = element.all(by.css('jhi-tipo-de-operacion-de-gasto div h2#page-heading span')).first();

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

export class TipoDeOperacionDeGastoUpdatePage {
    pageTitle = element(by.id('jhi-tipo-de-operacion-de-gasto-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nombreInput = element(by.id('field_nombre'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setNombreInput(nombre) {
        await this.nombreInput.sendKeys(nombre);
    }

    async getNombreInput() {
        return this.nombreInput.getAttribute('value');
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

export class TipoDeOperacionDeGastoDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-tipoDeOperacionDeGasto-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-tipoDeOperacionDeGasto'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
