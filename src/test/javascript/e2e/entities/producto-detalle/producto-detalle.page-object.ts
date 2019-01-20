import { element, by, ElementFinder } from 'protractor';

export class ProductoDetalleComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-producto-detalle div table .btn-danger'));
    title = element.all(by.css('jhi-producto-detalle div h2#page-heading span')).first();

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

export class ProductoDetalleUpdatePage {
    pageTitle = element(by.id('jhi-producto-detalle-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    cantidadInput = element(by.id('field_cantidad'));
    productoLabelInput = element(by.id('field_productoLabel'));
    precioVentaInput = element(by.id('field_precioVenta'));
    variantesSelect = element(by.id('field_variantes'));
    productosSelect = element(by.id('field_productos'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setCantidadInput(cantidad) {
        await this.cantidadInput.sendKeys(cantidad);
    }

    async getCantidadInput() {
        return this.cantidadInput.getAttribute('value');
    }

    async setProductoLabelInput(productoLabel) {
        await this.productoLabelInput.sendKeys(productoLabel);
    }

    async getProductoLabelInput() {
        return this.productoLabelInput.getAttribute('value');
    }

    async setPrecioVentaInput(precioVenta) {
        await this.precioVentaInput.sendKeys(precioVenta);
    }

    async getPrecioVentaInput() {
        return this.precioVentaInput.getAttribute('value');
    }

    async variantesSelectLastOption() {
        await this.variantesSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async variantesSelectOption(option) {
        await this.variantesSelect.sendKeys(option);
    }

    getVariantesSelect(): ElementFinder {
        return this.variantesSelect;
    }

    async getVariantesSelectedOption() {
        return this.variantesSelect.element(by.css('option:checked')).getText();
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

export class ProductoDetalleDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-productoDetalle-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-productoDetalle'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
