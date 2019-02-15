import { element, by, ElementFinder } from 'protractor';

export class TipoDeDocumentoDeVentaComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-tipo-de-documento-de-venta div table .btn-danger'));
    title = element.all(by.css('jhi-tipo-de-documento-de-venta div h2#page-heading span')).first();

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

export class TipoDeDocumentoDeVentaUpdatePage {
    pageTitle = element(by.id('jhi-tipo-de-documento-de-venta-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nombreInput = element(by.id('field_nombre'));
    serieInput = element(by.id('field_serie'));
    formatoInput = element(by.id('field_formato'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setNombreInput(nombre) {
        await this.nombreInput.sendKeys(nombre);
    }

    async getNombreInput() {
        return this.nombreInput.getAttribute('value');
    }

    async setSerieInput(serie) {
        await this.serieInput.sendKeys(serie);
    }

    async getSerieInput() {
        return this.serieInput.getAttribute('value');
    }

    async setFormatoInput(formato) {
        await this.formatoInput.sendKeys(formato);
    }

    async getFormatoInput() {
        return this.formatoInput.getAttribute('value');
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

export class TipoDeDocumentoDeVentaDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-tipoDeDocumentoDeVenta-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-tipoDeDocumentoDeVenta'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
