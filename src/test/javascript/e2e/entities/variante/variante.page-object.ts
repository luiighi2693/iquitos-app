import { element, by, ElementFinder } from 'protractor';

export class VarianteComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-variante div table .btn-danger'));
    title = element.all(by.css('jhi-variante div h2#page-heading span')).first();

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

export class VarianteUpdatePage {
    pageTitle = element(by.id('jhi-variante-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nombreInput = element(by.id('field_nombre'));
    descripcionInput = element(by.id('field_descripcion'));
    precioVentaInput = element(by.id('field_precioVenta'));
    precioCompraInput = element(by.id('field_precioCompra'));
    cantidadInput = element(by.id('field_cantidad'));
    productosSelect = element(by.id('field_productos'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setNombreInput(nombre) {
        await this.nombreInput.sendKeys(nombre);
    }

    async getNombreInput() {
        return this.nombreInput.getAttribute('value');
    }

    async setDescripcionInput(descripcion) {
        await this.descripcionInput.sendKeys(descripcion);
    }

    async getDescripcionInput() {
        return this.descripcionInput.getAttribute('value');
    }

    async setPrecioVentaInput(precioVenta) {
        await this.precioVentaInput.sendKeys(precioVenta);
    }

    async getPrecioVentaInput() {
        return this.precioVentaInput.getAttribute('value');
    }

    async setPrecioCompraInput(precioCompra) {
        await this.precioCompraInput.sendKeys(precioCompra);
    }

    async getPrecioCompraInput() {
        return this.precioCompraInput.getAttribute('value');
    }

    async setCantidadInput(cantidad) {
        await this.cantidadInput.sendKeys(cantidad);
    }

    async getCantidadInput() {
        return this.cantidadInput.getAttribute('value');
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

export class VarianteDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-variante-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-variante'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
