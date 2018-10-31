import { element, by, ElementFinder } from 'protractor';

export class PedidoComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-pedido div table .btn-danger'));
    title = element.all(by.css('jhi-pedido div h2#page-heading span')).first();

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

export class PedidoUpdatePage {
    pageTitle = element(by.id('jhi-pedido-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    notaInput = element(by.id('field_nota'));
    guiaInput = element(by.id('field_guia'));
    estatusSelect = element(by.id('field_estatus'));
    metaDataInput = element(by.id('field_metaData'));
    proveedorSelect = element(by.id('field_proveedor'));
    productosSelect = element(by.id('field_productos'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setNotaInput(nota) {
        await this.notaInput.sendKeys(nota);
    }

    async getNotaInput() {
        return this.notaInput.getAttribute('value');
    }

    async setGuiaInput(guia) {
        await this.guiaInput.sendKeys(guia);
    }

    async getGuiaInput() {
        return this.guiaInput.getAttribute('value');
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

export class PedidoDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-pedido-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-pedido'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
